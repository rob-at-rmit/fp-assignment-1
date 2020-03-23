package view;

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
	

}