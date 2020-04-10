package model.card;

/**
 * This enum represent the rank for each card
 * 
 * <p>
 * The natural order of rank should be Ace, 2, 3, ... 9, 10, Jack, Queen, King
 * 
 * <p>
 * <b>Note: </b>You must provide the method {@link Rank#getRankValue()} in the
 * enum and/or for each of it's values.
 * 
 * <p>
 * <b>Hint: </b>You may find it useful to override methods in the enum and/or
 * on each of the value.
 * 
 * <p>
 * <b>Hint: </b>Be sure to follow naming conventions for your enum values
 * 
 * <p>
 * <b>Note: </b> The {@link Rank#valueOf(String)} and {@link Rank#values()}
 * methods are provided by the API - you do not need to write or override them
 * yourself.
 * 
 * @author Ross Nye
 * 
 * @see model.card.Card
 * @see model.card.Suit
 *
 */
public enum Rank
{
	ACE   ("Ace", 1),
	TWO   ("2", 2),
	THREE ("3", 3),
	FOUR  ("4", 4),
	FIVE  ("5", 5),
	SIX   ("6", 6),
	SEVEN ("7", 7),
	EIGHT ("8", 8),
	NINE  ("9", 9),
	TEN   ("10", 10),
	JACK  ("Jack", 10),
	QUEEN ("Queen", 10),
	KING  ("King", 10);

    /**
     * Human readable name of this rank.
     */
    private final String name;
    
    /**
     * Actual int value of this rank for game logic.
     */
	private final int rankValue;
	
	/**
	 * Private constructor for each enum value.
	 * @param name the human readable name of this item.
	 * @param rankValue the game rank value of this item.
	 */
	private Rank(final String name, final int rankValue) 
	{
	    this.name = name;
		this.rankValue = rankValue;
	}
	
	/**
	 * Returns the rank value of this enum item.
	 */
	public int getRankValue() 
	{
		return rankValue;
	}

	/**
	 * String representation as per the specification.
	 */
	@Override
	public String toString() 
	{
	    return name;
	}

}