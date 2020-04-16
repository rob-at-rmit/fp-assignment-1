package model.bet;

import model.Player;
import model.card.Hand;
import model.card.Suit;

/**
 * SuitBet interface implementation class as per specification and Javadoc, 
 * utilising an AbstractBet base class for shared logic between all Bet
 * implementations.
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public class SuitBetImpl extends AbstractBet implements SuitBet 
{
    /**
     * Bet multiplier value for a suit bet.
     */
    private static final int SUIT_BET_MULTIPLIER = 4;

    /**
     * The suit associated with this suit bet.
     */
	private final Suit suit;

    /**
     * Constructor which delegates to the abstract superclass AbstractBet for
     * common validation logic and common fields.
     * 
     * @param player The player associated with this bet.
     * @param amount The amount of this bet.
     * @param suit The suit this suit bet is for.
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
	public SuitBetImpl(final Player player, final int amount, final Suit suit) 
	    throws NullPointerException, IllegalArgumentException
	{
	    super(player, amount);
		this.suit = suit;
	}

	/**
	 * Returns the multiplier for this bet implementation.
	 */
	@Override
	public int getMultiplier() 
	{
		return SUIT_BET_MULTIPLIER;
	}

    /**
     * Finalises this bet by using the specified house hand to calculate the
     * bet result for a suit bet, store it and then return it.
     */
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

	/**
	 * Returns the suit associated with this suit bet.
	 */
	@Override
	public Suit getSuit() 
	{
		return suit;
	}
	
    /**
     * Returns the string representation of this bet as per the specification.
     */
	@Override
	public String toString() 
	{
		return String.format("Suit Bet for %s on %s", getAmount(), getSuit());
	}

}