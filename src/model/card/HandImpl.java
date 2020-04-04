package model.card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HandImpl implements Hand 
{
    
    private final Collection<Card> cards;
    
    public HandImpl() 
    {
        this.cards = new ArrayList<>();
    }

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

	@Override
	public boolean isEmpty()
	{
		return cards.isEmpty();
	}

	@Override
	public int getNumberOfCards() 
	{
		return cards.size();
	}

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

	@Override
	public Collection<Card> getCards() 
	{
	    return Collections.unmodifiableCollection(cards);
	}

	@Override
	public void reset() 
	{
		cards.clear();
	}
	
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