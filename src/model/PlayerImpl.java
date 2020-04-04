package model;

import model.bet.Bet;
import model.bet.BetResult;
import model.card.Hand;
import model.card.HandImpl;

public class PlayerImpl implements Player 
{
	
	private final String id;
	private final String name;
	private int points;
	private Bet currentBet;
	private Hand currentHand;
	
	public PlayerImpl(final String id, final String name, final int points) 
		throws NullPointerException, IllegalArgumentException
	{
		this.id = id;
		this.name = name;
		this.points = points;
		this.currentBet = Bet.NO_BET;
		this.currentHand = new HandImpl();
	}

	@Override
	public String getId() 
	{
		return id;
	}

	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public int getPoints() 
	{
		return points;
	}

	@Override
	public int getTotalPoints() 
	{
		return points + currentBet.getAmount();
	}

	/**
	 * Assigns a new bet to this player.
	 * 
     * If we are replacing an existing non-zero bet, ensure any points in that 
     * bet are returned before subtracting the new bet amount.
     */
	@Override
	public void assignBet(final Bet bet)
	{
	    points = (points + currentBet.getAmount()) - bet.getAmount();
		currentBet = bet;
	}

	@Override
	public Bet getBet()
	{
		return currentBet;
	}

	@Override
	public Hand getHand() {
		return currentHand;
	}

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

	@Override
	public void resetBet() 
	{
	    assignBet(Bet.NO_BET);
	}
	
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