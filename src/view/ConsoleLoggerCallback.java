package view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.GameEngine;
import model.Player;
import model.bet.Bet;
import model.card.Card;
import model.card.Deck;
import model.card.Hand;

/**
 * An implementation of GameCallback which uses a Logger to log game events to the console.
 * 
 * <p><b>Important!</b> DO NOT EDIT THE STATIC BLOCK THAT SETS UP THE LOGGER OR IT'S DECLARATION!
 * 
 * <p><b>Note:</b> Logging message format should be consistent with the output trace.
 * 
 * @author Ross Nye
 * 
 * @see view.GameCallback
 * @see view.GameCallbackCollection
 *
 */
public class ConsoleLoggerCallback implements GameCallback
{
	/**
	 * A static {@link java.util.logging.Logger} object used for logging information
	 * (in this case to the console)
	 * 
	 * DO NOT EDIT!
	 */
	public static final Logger LOGGER;
	
	static
	{
		// DO NOT EDIT THIS STATIC BLOCK!!
		
		// Creating consoleHandler, add it and set the log levels.
		LOGGER = Logger.getLogger(ConsoleLoggerCallback.class.getName());
		LOGGER.setLevel(Level.FINER);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINER);
		LOGGER.addHandler(handler);
		LOGGER.setUseParentHandlers(false);
	}
	
	/**
	 * Local reference to game engine for handling any global
	 * logging callbacks.
	 */
	private final GameEngine engine;

	/**
	 * Public constructor as per the specification. Stores a reference
	 * to the game engine instance this callback is associated with.
	 * 
	 * @param engine the engine this callback is registered with.
	 */
	public ConsoleLoggerCallback(final GameEngine engine)
	{
		super();
		this.engine = engine;
	}

	/**
	 * Callback for adding a player.
	 */
	@Override
	public void addPlayer(final Player player) 
	{
	    LOGGER.log(Level.INFO, "Added {0}", player);
	}

    /**
     * Callback for removing a player.
     */
	@Override
	public void removePlayer(final Player player) 
	{
		LOGGER.log(Level.INFO, "Removed {0}", player); 
	}

    /**
     * Callback for a bet being updated for a player.
     */
	@Override
	public void betUpdated(final Player player)
	{
		LOGGER.log(
		    Level.INFO,
			"Bet updated for {0} to {1}",
			new Object[] { player.getId(), player.getBet() }
		);
	}

    /**
     * Callback for a new deck being initialised.
     */
	@Override
	public void newDeck(final Deck deck) 
	{
	    LOGGER.log(
	        Level.INFO, 
	        "A new deck of cards was created with 52 cards"
	    );
	}

    /**
     * Callback for player being dealt a card and not busting.
     */
	@Override
	public void playerCard(final Player player, final Card card) 
	{
	    LOGGER.log(
	        Level.FINE,
	        "Player {0} dealt {1}",
	        new Object[] {player.getId(), card}
	    );
	}

    /**
     * Callback for player being dealt a card busting.
     */
	@Override
	public void playerBust(final Player player, final Card card) 
	{
	    LOGGER.log(
	        Level.FINE, 
	        "Player {0} bust on {1}", 
	        new Object[] {player.getId(), card}
	    );
	}

	/**
	 * Callback for the house being dealt a card.
	 */
	@Override
	public void houseCard(final Hand houseHand, final Card card) 
	{
	    LOGGER.log(Level.FINE, String.format("House dealt %s", card));
	}

	/**
	 * Callback for the house busing.
	 */
	@Override
	public void houseBust(final Hand houseHand, final Card card) 
	{
	    LOGGER.log(Level.FINE, "House bust on {0}", card);
	    LOGGER.log(Level.FINE, "House Hand: {0}", houseHand);
	    LOGGER.log(Level.FINE, "Final Results:{0}", getFinalResults());
	}
	
	/**
	 * Formats the final results for multi-line log output.
	 * @return
	 */
	private String getFinalResults() 
	{
	    final List<String> results = new ArrayList<>();
	    for (final Player player : engine.getAllPlayers())
	    {
	        results.add(getFinalResults(player));
	    }
	    return String.join("", results);
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	private String getFinalResults(final Player player) 
	{
	    return String.format(
	        "%n%s%nPlayer: %-11s%-20s%s%s",
	        player,
	        player.getId(),
	        player.getName(),
	        player.getBet().equals(Bet.NO_BET) ? player.getBet() : player.getBet().getResult(),
	        formatBetOutcome(player)
	    );
	}
	
	private String formatBetOutcome(final Player player) 
	{
	    return (
	        player.getBet().equals(Bet.NO_BET) ? "" : String.format("%8d", player.getBet().getOutcome())
	    );
	}

}