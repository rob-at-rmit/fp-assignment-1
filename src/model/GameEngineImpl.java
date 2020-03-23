package model;

import java.util.Collection;

import model.card.Suit;
import view.GameCallback;

public class GameEngineImpl implements GameEngine 
{

	@Override
	public void registerCallback(GameCallback callback) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCallback(GameCallback callback) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPlayer(Player player) throws NullPointerException, IllegalArgumentException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePlayer(String playerId) throws NullPointerException, IllegalArgumentException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Player> getAllPlayers() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void placeBet(String playerId, int amount) throws NullPointerException, IllegalArgumentException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeBet(String playerId, int amount, Suit suit) throws NullPointerException, IllegalArgumentException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealPlayer(String playerId, int delay)
			throws NullPointerException, IllegalArgumentException, IllegalStateException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealHouse(int delay) throws IllegalArgumentException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetAllBetsAndHands() 
	{
		// TODO Auto-generated method stub
		
	}

}