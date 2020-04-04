package model.bet;

import model.Player;
import model.card.Hand;

public class ScoreBetImpl extends AbstractBet implements ScoreBet 
{

    private static final int SCORE_BET_MULTIPLIER = 2;
    
    private BetResult betResult;

	public ScoreBetImpl(final Player player, final int amount) 
	    throws NullPointerException, IllegalArgumentException
	{
	    super(player, amount);
	    this.betResult = BetResult.UNDETERMINED;
	}

	@Override
	public int getMultiplier()
	{
		return SCORE_BET_MULTIPLIER;
	}

    /**
     * Returns the latest result.
     */
    @Override
    public BetResult getResult() 
    {
        return betResult;
    }

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

	@Override
	public String toString() 
	{
		return String.format("Score Bet for %s", getAmount());
	}

}