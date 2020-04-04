package view;

import java.util.ArrayList;
import java.util.Collections;
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
	
	
	private final GameEngine engine;
	
	public ConsoleLoggerCallback(final GameEngine engine) 
	{
		super();
		// Unused
		this.engine = engine;
	}

	@Override
	public void addPlayer(final Player player) 
	{
		LOGGER.info(String.format("Added %s", player));
	}

	@Override
	public void removePlayer(final Player player) 
	{
		LOGGER.info(String.format("Removed %s", player));
	}

	@Override
	public void betUpdated(final Player player)
	{
		LOGGER.info(
			String.format("Bet updated for %s to %s", player.getId(), player.getBet())
		);
	}

	@Override
	public void newDeck(final Deck deck) 
	{
	    LOGGER.info("A new deck of cards was created with 52 cards");
	}

	@Override
	public void playerCard(final Player player, final Card card) 
	{
	    LOGGER.fine(String.format("Player %s dealt %s", player.getId(), card));
	}

	@Override
	public void playerBust(final Player player, final Card card) 
	{
	    LOGGER.fine(String.format("Player %s bust on %s", player.getId(), card));
	}

	@Override
	public void houseCard(final Hand houseHand, final Card card) 
	{
	    LOGGER.fine(String.format("House dealt %s", card));
	}

	@Override
	public void houseBust(final Hand houseHand, final Card card) 
	{
	    LOGGER.fine(String.format("House bust on %s", card));
	    LOGGER.info(String.format("House Hand: %s", houseHand));
	    LOGGER.info(String.format("Final Results:%s", getFinalResults()));
	}
	
	private String getFinalResults() 
	{
	    final List<String> results = new ArrayList<>();
	    for (final Player player : engine.getAllPlayers())
	    {
	        results.add(getFinalResults(player));
	    }
	    return String.join("", results);
	}
	
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