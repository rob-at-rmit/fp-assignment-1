package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.bet.Bet;
import model.bet.ScoreBet;
import model.bet.ScoreBetImpl;
import model.bet.SuitBet;
import model.bet.SuitBetImpl;
import model.card.Card;
import model.card.Deck;
import model.card.DeckImpl;
import model.card.Hand;
import model.card.HandImpl;
import model.card.Suit;
import util.ExceptionUtil;
import view.GameCallback;

/**
 * Main Game engine implementation class as per specification and Javadoc.
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public class GameEngineImpl implements GameEngine 
{
    
    /**
     * Collection to hold all registered callbacks, as multiple callbacks may be
     * registered.
     */
	private final Collection<GameCallback> callbacks;
	
	/**
	 * Map of all players in this instance of the game, using the player ID as 
	 * the map key and a reference to the player as the value.
	 */
	private final Map<String,Player> players;
	
	/**
	 * The current hand the house is holding in this game.
	 */
	private final Hand houseHand;
	
	/**
	 * The current deck in use by this game.
	 */
	private Deck deck;

	/**
	 * Default constructor.
	 * 
	 * <p>Initialises the callbacks collection, players, and house hand.</p>
	 * 
	 * <p>The deck is not initialised until the first deal to align with 
	 * newDeck callback timing (which does not fire until first deal).</p>
	 */
	public GameEngineImpl() 
	{
	    this.callbacks = new ArrayList<>();
	    this.players = new HashMap<>();
	    this.houseHand = new HandImpl();
	}

	/**
	 * Registers the specified callback in the local callback collection.
	 */
	@Override
	public void registerCallback(final GameCallback callback) 
	{
		callbacks.add(callback);
	}

	
	@Override
	public void removeCallback(final GameCallback callback) 
	{
		callbacks.remove(callback);
	}

	/**
	 * Adds the specified player to the current player map and fires the
	 * add player callbacks if successfully added.
	 */
	@Override
	public void addPlayer(final Player player) 
	    throws NullPointerException, IllegalArgumentException 
	{
	    assertPlayerDoesNotExist(player);
	    
		players.put(player.getId(), player);
		fireAddPlayerCallbacks(player);
	}

	/**
	 * Removes the player with the specified player ID from the player map
	 * and fires the remove player callbacks if successfully removed.
	 */
	@Override
	public void removePlayer(final String playerId) 
	    throws NullPointerException, IllegalArgumentException 
	{
	    assertPlayerExists(playerId);
	    
		final Player removed = players.remove(playerId);
		fireRemovePlayerCallbacks(removed);
	}

	/**
	 * Wraps the player values collection from the map with an unmodifiable
	 * collection and returns it.
	 */
	@Override
	public Collection<Player> getAllPlayers()
	{
		return Collections.unmodifiableCollection(players.values());
	}

	/**
	 * Places score bet with the player with the specified player ID and fires 
	 * all validation and callbacks.
	 */
	@Override
	public void placeBet(final String playerId, final int amount) 
	    throws NullPointerException, IllegalArgumentException 
	{
	    assertPlayerExists(playerId);

		final Player player = players.get(playerId);
		assertNewBetHigher(player, amount);
		
		final ScoreBet newBet = new ScoreBetImpl(player, amount);
		player.assignBet(newBet);
		fireBetUpdatedCallbacks(player);
	}

	/**
	 * Places suit bet with the player with the specified player ID and fires 
	 * all validation and callbacks.
	 */
	@Override
	public void placeBet(final String playerId, final int amount, final Suit suit) 
	    throws NullPointerException, IllegalArgumentException 
	{
	    assertPlayerExists(playerId);
	    
		final Player player = players.get(playerId);
		assertNewBetHigher(player, amount);
		
		final SuitBet newBet = new SuitBetImpl(player, amount, suit);

		player.assignBet(newBet);
		fireBetUpdatedCallbacks(player);
	}

	/**
	 * Deals a card to the player with the specified player ID, ensuring they
	 * have not already been dealt a hand yet and that they have made a valid 
	 * bet.
	 */
	@Override
	public void dealPlayer(final String playerId, final int delay)
	    throws NullPointerException, IllegalArgumentException, IllegalStateException 
	{
	    assertPlayerExists(playerId);
	    assertDelayNotNegative(delay);
	    
	    final Player player = players.get(playerId);
	    ExceptionUtil.assertLegalState(
	        !player.getBet().equals(Bet.NO_BET),
	        String.format("Player with ID %s has not placed a bet", playerId) 
	    );
	    ExceptionUtil.assertLegalState(
	        player.getHand().isEmpty(),
	        String.format("Player with ID %s has already been dealt to", playerId) 
	    );

	    ensureDeckReadyToDeal();
	    dealUntilBust(player, delay);
	}

    /**
     * Deals a card to the house hand utilising the same game logic as a player.
     */
	@Override
    public void dealHouse(int delay) throws IllegalArgumentException 
    {
	    assertDelayNotNegative(delay);
	    dealUntilBust(null, delay);
    }
	
	/**
     * This private implementation attempts to share the deal logic between
     * both the Player type (with a hand) and the main house hand.
     * 
     * It's not super pretty to pass a 'null' player here to indicate a house
     * hand, and I considered for a while using a separate implementation of 
     * PlayerImpl to represent the house. I decided against this because there 
     * are so many methods on Player which are not relevant to the house. 
     * 
     * For now, I think this implementation is reasonable and with extensive
     * comments hopefully clear.
     * 
     * @param player If specified, the player to deal the hand to. If not 
     * specified (null) this assumes a deal to the house hand.
     * @param delayMilliseconds The delay in ms between deals.
     */
    private void dealUntilBust(final Player player, final int delayMilliseconds)
    {
        /*
         * Determine whether this is a player or house deal. Assume this is a 
         * house deal if no player has been specified.
         */
        final boolean houseDeal = (player == null);
        final Hand handToDeal = houseDeal ? houseHand : player.getHand();
        
        boolean dealBust = false;
        while (!dealBust) 
        {
            final Card card = deck.removeNextCard();
            dealBust = !handToDeal.dealCard(card);
            wait(delayMilliseconds);
            if (dealBust)
            {
                if (houseDeal) 
                {
                    /*
                     * House bust, game is over.
                     */
                    finishGame();
                    fireHouseBustCallbacks(card);
                }
                else
                {
                    firePlayerBustCallbacks(player, card);
                }
            }
            else
            {
                if (houseDeal) {
                    fireHouseCardCallbacks(card);
                }
                else
                {
                    firePlayerCardCallbacks(player, card);
                }
            }
        }
    }
    

    @Override
    public void resetAllBetsAndHands() 
    {
        /*
         * Ensure the deck is reset to null so it is initialised 
         * again once the first player deal occurs.
         */
        deck = null;
        
        /*
         * Reset the house hand.
         */
        houseHand.reset();
        
        /*
         * Reset all bets and hands for all players.
         */
        for (final Player player : getAllPlayers())
        {
            player.getHand().reset();
            player.resetBet();
            fireBetUpdatedCallbacks(player);
        }
    }
    
    /**
     * Finishes the game by applying all bet results to all players based on the 
     * current and final house hand.
     */
    private void finishGame()
    {
        for (final Player player : getAllPlayers()) 
        {
            player.applyBetResult(houseHand);
        }
    }
    
    /**
     * Simple private implementation to fire all remove player callbacks.
     */
    private void fireRemovePlayerCallbacks(final Player player)
    {
        for (final GameCallback cb : callbacks)
        {
            cb.removePlayer(player);
        }
    }
    
    /**
     * Simple private implementation to fire all add player callbacks.
     */
    private void fireAddPlayerCallbacks(final Player player) 
    {
        for (final GameCallback cb : callbacks)
        {
            cb.addPlayer(player);
        }
    }

    /**
     * Simple private implementation to fire all player card callbacks.
     */
    private void firePlayerCardCallbacks(final Player player, final Card card)
    {
        for (final GameCallback cb : this.callbacks)
        {
            cb.playerCard(player, card);
        }
    }
    
    /**
     * Simple private implementation to fire all player bust callbacks.
     */
    private void firePlayerBustCallbacks(final Player player, final Card card)
    {
        for (final GameCallback cb : this.callbacks)
        {
            cb.playerBust(player, card);
        }
    }
    
    /**
     * Simple private implementation to fire all house card callbacks.
     */
    private void fireHouseCardCallbacks(final Card card)
    {
        for (final GameCallback cb : this.callbacks)
        {
            cb.houseCard(houseHand, card);
        }
    }
    
    /**
     * Simple private implementation to fire all house bust callbacks.
     */
    private void fireHouseBustCallbacks(final Card card)
    {
        for (final GameCallback cb : this.callbacks)
        {
            cb.houseBust(houseHand, card);
        }
    }
    
    /**
     * Simple private implementation to fire all bet updated callbacks.
     */
    private void fireBetUpdatedCallbacks(final Player player) {
        for (final GameCallback cb : this.callbacks) 
        {
            cb.betUpdated(player);
        }
    }
    
    /**
     * Simple private implementation to fire all new deck callbacks.
     */
    private void fireNewDeckCallbacks()
    {
        for (final GameCallback cb : callbacks) 
        {
            cb.newDeck(deck);
        }
    }

    /**
     * Ensures the deck in use is ready to deal, either by initialising it
     * in the first instance if it hasn't been created yet, or if there are no
     * more cards left to deal.
     */
	private void ensureDeckReadyToDeal() {
	    if (deck == null || deck.cardsInDeck() == 0)
	    {
	        deck = DeckImpl.createShuffledDeck();
	        fireNewDeckCallbacks();
	    }
	}
	
	/**
	 * Causes the current thread to wait for the specified number of ms and
	 * takes no action on interrupt.
	 */
	private void wait(final int delayMilliseconds) {
        try 
        {
            Thread.sleep(delayMilliseconds);
        }
        catch (final InterruptedException e)
        {
            // No action on interrupt
        }
	}
	
	/*
	 * Ensures a new bet being assigned is higher than the existing player bet.
	 * The javadoc specified 'if, when replacing an existing bet, the bet amount 
	 * is not greater the existing bet' but makes no mention of a bet of zero.
	 * 
	 * The assumption here is that a bet of zero is invalid, because the default
	 * state of 'no bet' has an amount of zero, and so a new bet is not
	 * greater than the existing bet (of zero).
	 */
	private void assertNewBetHigher(final Player player, final int amount)
	{
	    ExceptionUtil.assertLegalArgument(
	        amount > player.getBet().getAmount(),
	        String.format(
	            "Bet for player with ID %s of %d must be higher than existing bet %d",
	            player.getId(), player.getBet().getAmount(), amount
	        )
	    );
	}
	
	/**
	 * Ensures the specified delay is not negative otherwise throws an 
	 * IllegalArgumentException.
	 * 
	 * @param delay delay value to test.
	 */
	private void assertDelayNotNegative(final int delay)
	{
        ExceptionUtil.assertLegalArgument(
            delay >= 0, "Deplay cannot be negative"
        );
	}    

    /**
     * Ensures the specified player is not null and does not already exist in the game.
     * 
     * @param player player reference to test.
     */
	private void assertPlayerDoesNotExist(final Player player)
	{
	    ExceptionUtil.assertNotNull(player, "Player cannot be null");
        ExceptionUtil.assertLegalArgument(
            !players.containsKey(player.getId()), 
            String.format("Player with ID %s exists in game", player.getId())
        );
	}

    /**
     * Ensures the specified player is not null and exists in the game.
     * 
     * @param player player reference to test.
     */
    private void assertPlayerExists(final String playerId)
    {
        ExceptionUtil.assertNotNull(playerId, "Player ID cannot be null");
        ExceptionUtil.assertLegalArgument(
            players.containsKey(playerId),
            String.format("Player with ID %s does not exist in game", playerId)
        );
    }

}