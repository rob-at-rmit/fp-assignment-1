package model;

import model.bet.Bet;
import model.card.Hand;

public class PlayerImpl implements Player 
{
	
	public PlayerImpl(final String id, final String name, final int points) throws NullPointerException, IllegalArgumentException
	{
		
	}

	@Override
	public String getId() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPoints() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalPoints() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void assignBet(final Bet bet) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Bet getBet()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hand getHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyBetResult(final Hand houseHand) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetBet() 
	{
		// TODO Auto-generated method stub
		
	}

}
