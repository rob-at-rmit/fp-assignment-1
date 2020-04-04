package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

public class GameEngineImpl implements GameEngine 
{

	private Collection<GameCallback> callbacks;
	private Map<String,Player> players;
	private Hand houseHand;
	private Deck deck;

	public GameEngineImpl() 
	{
	    this.callbacks = new ArrayList<>();
	    this.players = new HashMap<>();
	    this.houseHand = new HandImpl();
	}

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

	@Override
	public void addPlayer(Player player) throws NullPointerException, IllegalArgumentException 
	{
		players.put(player.getId(), player);
		for (final GameCallback cb : callbacks)
		{
			cb.addPlayer(player);
		}
	}

	@Override
	public void removePlayer(String playerId) throws NullPointerException, IllegalArgumentException 
	{
		final Player removed = players.remove(playerId);
		for (final GameCallback cb : callbacks)
		{
			cb.removePlayer(removed);
		}
	}

	@Override
	public Collection<Player> getAllPlayers() 
	{
		return players.values();
	}

	/**
	 * Score bet.
	 */
	@Override
	public void placeBet(final String playerId, final int amount) throws NullPointerException, IllegalArgumentException 
	{
		final Player player = players.get(playerId);
		final ScoreBet newBet = new ScoreBetImpl(player, amount);
		player.assignBet(newBet);
		fireBetUpdatedCallbacks(player);
	}

	/**
	 * Suit bet.
	 */
	@Override
	public void placeBet(String playerId, int amount, Suit suit) throws NullPointerException, IllegalArgumentException 
	{
		final Player player = players.get(playerId);
		final SuitBet newBet = new SuitBetImpl(player, amount, suit);
		player.assignBet(newBet);
		fireBetUpdatedCallbacks(player);
	}

	@Override
	public void dealPlayer(final String playerId, final int delay)
	    throws NullPointerException, IllegalArgumentException, IllegalStateException 
	{
	    ExceptionUtil.assertNotNull(playerId, "Player ID cannot be null");
	    ExceptionUtil.assertLegalArgument(players.containsKey(playerId), "Player ID does not exist");
	    ExceptionUtil.assertLegalArgument(delay >= 0, "Deplay cannot be negative");
	    
	    ensureDeckInitialised();
	    
	    final Player player = players.get(playerId);
	    dealUntilBust(player, delay);
	}

	@Override
    public void dealHouse(int delay) throws IllegalArgumentException 
    {
	    dealUntilBust(null, delay);
    }
	
	/**
     * This private implementation attempts to share the deal logic between
     * both the Player type (with a hand) and the main house hand.
     * 
     * @param player If specified, the player to deal the hand to. If not 
     * specified (null) this assumes a deal to the house hand.
     * @param delayMilliseconds The delay in ms between deals.
     */
    private void dealUntilBust(final Player player, final int delayMilliseconds)
    {
        /*
         * Determine whether this is a player or house deal. Assume 
         * house detail if no player has been specified.
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
                    applyAllBets();
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
    
    private void applyAllBets()
    {
        for (final Player player : getAllPlayers()) 
        {
            player.applyBetResult(houseHand);
        }
    }

    private void firePlayerCardCallbacks(final Player player, final Card card)
    {
        for (final GameCallback cb : this.callbacks)
        {
            cb.playerCard(player, card);
        }
    }
    
    private void firePlayerBustCallbacks(final Player player, final Card card)
    {
        for (final GameCallback cb : this.callbacks)
        {
            cb.playerBust(player, card);
        }
    }
    
    private void fireHouseCardCallbacks(final Card card)
    {
        for (final GameCallback cb : this.callbacks)
        {
            cb.houseCard(houseHand, card);
        }
    }
    
    private void fireHouseBustCallbacks(final Card card)
    {
        for (final GameCallback cb : this.callbacks)
        {
            cb.houseBust(houseHand, card);
        }
    }
    
    private void fireBetUpdatedCallbacks(final Player player) {
        for (final GameCallback cb : this.callbacks) 
        {
            cb.betUpdated(player);
        }
    }    

	private void ensureDeckInitialised() {
	    if (deck == null)
	    {
	        deck = DeckImpl.createShuffledDeck();
	        for (final GameCallback cb : callbacks) 
	        {
	            cb.newDeck(deck);
	        }
	    }
	}
	
	private void wait(final int delayMilliseconds) {
        try 
        {
            Thread.sleep(delayMilliseconds);
        }
        catch (final InterruptedException e)
        {
            
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

}