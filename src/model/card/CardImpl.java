package model.card;

/**
 * TODO: 
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public class CardImpl implements Card 
{
    
    private final Suit suit;
    private final Rank rank;
    
	public CardImpl(final Suit suit, final Rank rank)
	{
		this.suit = suit;
		this.rank = rank;
	}
	
	@Override
	public Suit getSuit() 
	{
		return suit;
	}

	@Override
	public Rank getRank() 
	{
		return rank;
	}

	@Override
	public int getValue() 
	{
		return rank.getRankValue();
	}
	
	@Override
	public int hashCode()
	{
		System.out.println("Someone is calling CardImpl.hashCode()");
		return 0;
	}
	
	@Override
	public int compareTo(final Card card)
	{
	    int suitComparisonResult = card.getSuit().compareTo(suit);
	    if (suitComparisonResult == 0) {
	        int thisValue = getValue();
	        int otherValue = card.getValue();
	        return thisValue == otherValue ? 0 : (thisValue < otherValue) ? -1 : 1;
	    }
	    return suitComparisonResult;
	}
	
	/**
	 * Override of equals which uses a value comparison to determine whether
	 * the specified object (if a Card instance) is the same card.
	 * This re-uses the compareTo method, which will return zero if the cards
	 * have the same suit and rank value, which satisfies the specified rules.
	 */
	@Override
	public boolean equals(final Object other)
	{
	    return (other instanceof Card) ? compareTo((Card) other) == 0 : false;
	}
	
	/**
	 * Override of toString which shows a human readable name using the rank
	 * and suit of the card.
	 */
	@Override
    public String toString() 
	{
	    return String.format("%s of %s", rank, suit);
	}

}