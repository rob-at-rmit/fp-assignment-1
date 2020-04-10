package model.card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Hand interface implementation class as per specification and Javadoc.
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public class HandImpl implements Hand 
{
    
    /**
     * The cards currently in this hand. 
     */
    private final Collection<Card> cards;

    /**
     * Default zero-argument constructor as per the specification.
     */
    public HandImpl() 
    {
        this.cards = new ArrayList<>();
    }

    /**
     * Deals the specified card into this hand.
     * Compares the value of current hand plus the new card to determine whether
     * this hand has busted or not. The card is only added to the hand if it
     * doesn't bust. 
     * 
     * @return true if the card was successfully added to the hand, bust otherwise.
     */
	@Override
	public boolean dealCard(final Card card) 
	{
	    final int scoreAfterDeal = getScore() + card.getValue();
	    if (scoreAfterDeal <= BUST_SCORE)
	    {
	        cards.add(card);
	        return true;
	    }
	    return false;
	}

	/**
	 * Returns whether the hand is empty (has no cards)
	 */
	@Override
	public boolean isEmpty()
	{
		return cards.isEmpty();
	}

	/**
	 * Returns the number of cards in the hand.
	 */
	@Override
	public int getNumberOfCards() 
	{
		return cards.size();
	}

	/**
	 * Returns the current score of all cards in the hand.
	 */
	@Override
	public int getScore() 
	{
	    int result = 0;
	    for (final Card card : cards) 
	    {
	        result += card.getRank().getRankValue();
	    }
	    return result;
	}

	/**
	 * Returns the number of cards of the specified suit within the hand.
	 */
	@Override
	public int getSuitCount(final Suit suit) 
	{
	    int result = 0;
	    for (final Card card : cards)
	    {
	        if (card.getSuit().equals(suit)) {
	            result++;
	        }
	    }
	    return result;
	}

	/**
	 * Returns an unmodifiable collection of all the cards in the hand.
	 */
	@Override
	public Collection<Card> getCards() 
	{
	    return Collections.unmodifiableCollection(cards);
	}

	/**
	 * Clears the hand of all cards.
	 */
	@Override
	public void reset() 
	{
		cards.clear();
	}
	
	/**
	 * String representation of this hand as per the specification.
	 */
	@Override
	public String toString()
	{
	    if (isEmpty()) {
	        return "Empty Hand";
	    }
	    return String.format(
	        "Hand of %d cards %s Score: %d", getNumberOfCards(), cards, getScore()
	    );
	}

}