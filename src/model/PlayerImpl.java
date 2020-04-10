package model;

import model.bet.Bet;
import model.bet.BetResult;
import model.card.Hand;
import model.card.HandImpl;
import util.ExceptionUtil;

/**
 * Player interface implementation class as per specification and Javadoc.
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public class PlayerImpl implements Player 
{
	
    /** 
     * Unique identifier of the player.
     */
	private final String id;
	
	/**
	 * Name of the player.
	 */
	private final String name;
	
	/**
	 * Current points for this player.
	 */
	private int points;
	
	/**
	 * Current bet in play for this player.
	 */
	private Bet currentBet;
	
	/**
	 * Current hand for this player.
	 */
	private Hand currentHand;
	
	/**
	 * Constructor which constructs a new single player based on the given 
	 * arguments and validates based on the specification. 
	 * 
	 * @param id Unique ID of the player, cannot be null or empty.
	 * @param name Name of the player, cannot be null or empty
	 * @param points Number of points the player has, must be positive.
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public PlayerImpl(final String id, final String name, final int points) 
		throws NullPointerException, IllegalArgumentException
	{
	    
	    ExceptionUtil.assertNotNull(id, "Player ID cannot be null");
	    ExceptionUtil.assertNotNull(name, "Player name cannot be null");
        ExceptionUtil.assertLegalArgument(
            id.trim().length() > 0, "Player ID cannot be empty"
        );	    
	    ExceptionUtil.assertLegalArgument(
	        name.trim().length() > 0, "Player name cannot be empty"
	    );
        ExceptionUtil.assertLegalArgument(
            points > 0,  "Player must be positive"
        );
	    
		this.id = id;
		this.name = name;
		this.points = points;
		this.currentBet = Bet.NO_BET;
		this.currentHand = new HandImpl();
	}

	/**
	 * Returns the ID of the player.
	 */
	@Override
	public String getId() 
	{
		return id;
	}

    /**
     * Returns the name of the player.
     */
	@Override
	public String getName() 
	{
		return name;
	}

    /**
     * Returns the current points of the player. If a bet is in play, those bet
     * points have been quarantined so will not be reflected in the current 
     * points available to the player.
     * 
     */
	@Override
	public int getPoints() 
	{
		return points;
	}

    /**
     * Returns the current total points of the player, which includes any bet
     * which is currently in play.
     */
	@Override
	public int getTotalPoints() 
	{
		return points + currentBet.getAmount();
	}

	/**
	 * Assigns a new bet to this player.
	 * If we are replacing an existing non-zero bet, ensure any points in that 
     * bet are returned before subtracting the new bet amount.
     */
	@Override
	public void assignBet(final Bet bet)
	{
	    points = (points + currentBet.getAmount()) - bet.getAmount();
		currentBet = bet;
	}

    /**
     * Returns the current bet for this player.
     */
	@Override
	public Bet getBet()
	{
		return currentBet;
	}

    /**
     * Returns the current hand for this player.
     */
	@Override
	public Hand getHand() {
		return currentHand;
	}

	/**
	 * Using the specified house hand, finalises the current bet for the player
	 * and applies the results to the player's points.
	 */
	@Override
	public void applyBetResult(final Hand houseHand)
	{
	    if (houseHand != null) 
	    {
	        final BetResult result = currentBet.finaliseBet(houseHand);
	        /*
	         * Player wins, add outcome to points which includes the original
	         * quarantined bet amount.
	         */
	        if (result.equals(BetResult.PLAYER_WIN))
	        {
	            final int outcome = currentBet.getOutcome();
	            points = points + outcome;
	        }
	        /*
	         * Player draws, return original quarantined amount.
	         */
	        else if (result.equals(BetResult.DRAW))
	        {
	            points = points + currentBet.getAmount();
	        }
	        /*
	         * Player loses - the lost amount has already been removed.
	         */
	    }
	}

	/**
	 * Resets the current bet for this player by assigning BET.NO_BET, ensuring
	 * point quarantine and return logic is fired.
	 */
	@Override
	public void resetBet() 
	{
	    assignBet(Bet.NO_BET);
	}
	
	/**
	 * Returns the string representation of this player as per the specification.
	 */
	@Override
	public String toString() {
		return String.format(
			"Player id=%s, name=%s, points=%d, %s, %s",
			getId(),
			getName(),
			getPoints(),
			getBet(),
			getHand()
		);
	}

}