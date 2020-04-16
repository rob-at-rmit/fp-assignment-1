package model.bet;

import model.Player;
import model.card.Hand;

/**
 * ScoreBet interface implementation class as per specification and Javadoc, 
 * utilising an AbstractBet base class for shared logic between all Bet
 * implementations.
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public class ScoreBetImpl extends AbstractBet implements ScoreBet 
{
    
    /**
     * Bet multiplier value for a score bet.
     */
    private static final int SCORE_BET_MULTIPLIER = 2;

    /**
     * Constructor which delegates to the abstract superclass AbstractBet for
     * common validation logic and common fields.
     * 
     * @param player The player associated with this bet.
     * @param amount The amount of this bet.
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
	public ScoreBetImpl(final Player player, final int amount) 
	    throws NullPointerException, IllegalArgumentException
	{
	    super(player, amount);
	}

	/**
	 * Returns the multiplier value for this bet.
	 */
	@Override
	public int getMultiplier()
	{
		return SCORE_BET_MULTIPLIER;
	}
    
    /**
     * Finalises this bet by using the specified house hand to calculate the
     * bet result for a score bet, store it and then return it.
     */
	@Override
	public BetResult finaliseBet(final Hand houseHand)
	{
	    final int playerScore = getPlayer().getHand().getScore();
	    final int houseScore = houseHand.getScore();

	    if (playerScore < houseScore)
	    {
	        betResult = BetResult.PLAYER_LOSS;
	    }
	    else if (playerScore == houseScore)
	    {
	        betResult = BetResult.DRAW;
	    }
	    else {
	        betResult = BetResult.PLAYER_WIN; 
	    }

		return betResult;
	}

	/**
	 * Returns the string representation of this bet as per the specification.
	 */
	@Override
	public String toString() 
	{
		return String.format("Score Bet for %s", getAmount());
	}

}