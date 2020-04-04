package model.bet;

import model.Player;
import model.card.Hand;
import model.card.Suit;

public class SuitBetImpl extends AbstractBet implements SuitBet 
{
    private static final int SUIT_BET_MULTIPLIER = 4;

	private final Suit suit;
	private BetResult betResult;

	public SuitBetImpl(final Player player, final int amount, final Suit suit) 
	    throws NullPointerException, IllegalArgumentException
	{
	    super(player, amount);
		this.suit = suit;
		this.betResult = BetResult.UNDETERMINED;
	}

	@Override
	public int getMultiplier() 
	{
		return SUIT_BET_MULTIPLIER;
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

	    final int playerSuitCount = getPlayer().getHand().getSuitCount(suit);
	    final int houseSuitCount = houseHand.getSuitCount(suit);
        
        if (playerSuitCount <= houseSuitCount) 
        {
            betResult = BetResult.PLAYER_LOSS;
        }
        else {
            betResult = BetResult.PLAYER_WIN; 
        }

        return betResult;
	}

	@Override
	public Suit getSuit() 
	{
		return suit;
	}
	
	@Override
	public String toString() 
	{
		return String.format("Suit Bet for %s on %s", getAmount(), getSuit());
	}

}