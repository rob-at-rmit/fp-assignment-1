package model.card;

import java.util.Collections;
import java.util.Stack;

import util.ExceptionUtil;

public class DeckImpl implements Deck
{

	public static Deck createShuffledDeck() 
	{
        final Deck shuffled = new DeckImpl(createStackOfAllCards());
        shuffled.shuffleDeck();
        return shuffled;
	}
	
	public static Deck createSortedDeck() 
	{
	    final Stack<Card> sorted = createStackOfAllCards();
	    Collections.sort(sorted);
	    return new DeckImpl(sorted);
	}
	
	private static Stack<Card> createStackOfAllCards() 
	{
	    final Stack<Card> all = new Stack<>();
	    for (final Suit suit : Suit.values()) 
	    {
	        for (final Rank rank : Rank.values()) 
	        {
	            all.add(new CardImpl(suit, rank));
	        }
	    }
	    return all;
	}
	
	private final Stack<Card> cards;

	private DeckImpl(final Stack<Card> cards) 
	{
	    this.cards = cards;
	}

	@Override
	public Card removeNextCard() throws IllegalStateException 
	{
	    ExceptionUtil.assertLegalState(!cards.isEmpty(), "No cards in deck");
	    return cards.pop();
	}

	@Override
	public int cardsInDeck() 
	{
		return cards.size();
	}

	@Override
	public void shuffleDeck()
	{
	    Collections.shuffle(cards);
	}

}