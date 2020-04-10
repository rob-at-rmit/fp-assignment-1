package model.card;

import util.ExceptionUtil;

/**
 * Card interface implementation class as per specification and Javadoc.
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public class CardImpl implements Card 
{
    
    /**
     * The suit of this card.
     */
    private final Suit suit;
    
    /**
     * The rank of this card.
     */
    private final Rank rank;
    
    /**
     * Public constructor which takes a suit and rank as per the specification.
     * 
     * @param suit the suit associated with this card.
     * @param rank the rank of this card.
     */
	public CardImpl(final Suit suit, final Rank rank)
	{
	    ExceptionUtil.assertNotNull(suit, "Suit cannot be null");
	    ExceptionUtil.assertNotNull(suit, "Rank cannot be null");

		this.suit = suit;
		this.rank = rank;
	}
	
	/**
	 * Returns the suit of this card.
	 */
	@Override
	public Suit getSuit() 
	{
		return suit;
	}

	/**
	 * Returns the rank of this card.
	 */
	@Override
	public Rank getRank() 
	{
		return rank;
	}

	/**
	 * Returns the value of the rank of this card.
	 */
	@Override
	public int getValue() 
	{
		return rank.getRankValue();
	}
	
	/**
	 * This hashCode implementation uses a simple prime combined with the
	 * hashCode values of the two principal fields to calculate a stable 
	 * hashCode for each CardImpl which is consistent with .equals().
	 */
	@Override
	public int hashCode()
	{
		int hashCodeResult = 1;
		hashCodeResult = 97 * hashCodeResult + suit.hashCode();
		hashCodeResult = 97 * hashCodeResult + rank.hashCode();
		return hashCodeResult;
	}

    /**
     * Compares card instances as per the specifications.
     * Suits are compared first, if they do not match then the suit order
     * is used to compare cards. The specification states to use alphabetical
     * order for Suit comparison which matches the natural order of the enum
     * (Clubs, Diamonds, Hearts, Spades) so an enum comparison is fine.
     * 
     * If the suits match, then the ranks of the cards are compared using their
     * natural order (as per specification).
     */
	@Override
	public int compareTo(final Card card)
	{
	    final int suitComparisonResult = card.getSuit().compareTo(suit);
	    if (suitComparisonResult == 0)
	    {
	        return getRank().compareTo(card.getRank());
	    }
	    return suitComparisonResult;
	}
	
	/**
	 * Override of equals which compares suits and ranks as per the specifications.
	 * For any equals comparison on a Card instance, if they have a suit and
	 * rank which is the same (and not null) they are considered the same.
	 */
	@Override
	public boolean equals(final Object other)
	{
        if (this == other) {
            return true;
        }
	    else if (other instanceof Card)
	    {
	        final Card otherCard = (Card) other;
	        return (
	            suit.equals(otherCard.getSuit()) &&
	            rank.equals(otherCard.getRank())
	        );
	    }
	    return false;
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