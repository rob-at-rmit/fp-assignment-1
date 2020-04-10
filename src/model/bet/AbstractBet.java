package model.bet;

import model.Player;
import util.ExceptionUtil;

/**
 * Abstract base class to share common implementation between both Bet 
 * implementations.
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public abstract class AbstractBet implements Bet
{
    
    /**
     * The player this bet is associated with.
     */
    private final Player player;
    
    /**
     * The amount of this bet.
     */
    private final int amount;

    /**
     * Base constructor that implements common constructor validation logic
     * based on the specification for any subclasses (e.g. ScoreBetImpl and
     * SuitBetImpl).
     * 
     * @param player The player this bet is associated with
     * @param amount The amount of this bet
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    protected AbstractBet(final Player player, final int amount)
        throws NullPointerException, IllegalArgumentException
    {
        ExceptionUtil.assertNotNull(player, "Player cannot be null");
        ExceptionUtil.assertLegalState(amount > 0, "Amount must be positive");
        ExceptionUtil.assertLegalState(
            player.getPoints() >= amount, 
            "Player does not have enough points to place bet"
        );
        this.player = player;
        this.amount = amount;
    }

    /**
     * Returns the player this bet is associated with.
     */
    @Override
    public Player getPlayer() 
    {
        return player;
    }

    /**
     * Returns the amount of this bet.
     */
    @Override
    public int getAmount() 
    {
        return amount;
    }

    /**
     * Returns the outcome of this bet based on the current bet result.
     */
    @Override
    public int getOutcome() 
    {
        return getOutcome(getResult());
    }

    /**
     * Calculates the outcome of the bet based on the result that is specified
     * as an argument. 
     * For a player win, the amount multiplied by the multiplier.
     * For a player loss, the negative of the amount that was bet.
     * For a draw, the outcome is zero (no change).
     */
    @Override
    public int getOutcome(final BetResult result) 
    {
        if (result.equals(BetResult.PLAYER_WIN))
        {
            return amount * getMultiplier();
        }
        else if (result.equals(BetResult.PLAYER_LOSS))
        {
            return -(amount);
        }
        return 0;
    }

    /**
     * Common implementation of compareTo which compares two Bet instances
     * based on the value of the bet outcome.
     */
    @Override
    public int compareTo(final Bet bet)
    {
        final int thisValue = getOutcome();
        final int otherValue = bet.getOutcome();
        return thisValue == otherValue ? 0 : (thisValue < otherValue) ? -1 : 1;
    }
    
}