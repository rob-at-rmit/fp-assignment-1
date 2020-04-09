package model.bet;

import model.Player;
import util.ExceptionUtil;

/**
 * TODO: 
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public abstract class AbstractBet implements Bet
{

    private final Player player;
    private final int amount;

    protected AbstractBet(final Player player, final int amount)
        throws NullPointerException, IllegalArgumentException
    {
        ExceptionUtil.assertNotNull(player, "Player cannot be null");
        ExceptionUtil.assertLegalState(amount >= 0, "Amount must be positive");
        ExceptionUtil.assertLegalState(
            player.getPoints() >= amount, "Player does not have enough points to place bet"
        );
        this.player = player;
        this.amount = amount;
    }

    @Override
    public Player getPlayer() 
    {
        return player;
    }

    @Override
    public int getAmount() 
    {
        return amount;
    }

    @Override
    public int getOutcome() 
    {
        return getOutcome(getResult());
    }

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

    @Override
    public int compareTo(final Bet bet)
    {
        final int thisValue = getOutcome();
        final int otherValue = bet.getOutcome();
        return thisValue == otherValue ? 0 : (thisValue < otherValue) ? -1 : 1;
    }
    
}