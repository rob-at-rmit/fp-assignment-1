package model.card;

import java.util.Collections;
import java.util.Stack;

import util.ExceptionUtil;

/**
 * Deck interface implementation class as per specification and Javadoc. 
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public class DeckImpl implements Deck
{

    /**
     * Creates and returns a new Deck instance that is shuffled and ready
     * to deal.
     * @return a new shuffled deck instance.
     */
	public static Deck createShuffledDeck() 
	{
        final Deck shuffled = new DeckImpl(createStackOfAllCards());
        shuffled.shuffleDeck();
        return shuffled;
	}
	
	/**
	 * Creates and returns a new Deck instance that is sorted according to 
	 * card ordering as per specification.
	 * @return a new sorted deck instance.
	 */
	public static Deck createSortedDeck() 
	{
	    final Stack<Card> sorted = createStackOfAllCards();
	    Collections.sort(sorted);
	    return new DeckImpl(sorted);
	}
	
	/**
	 * Creates a Stack of the full 52 card standard deck.
	 * @return
	 */
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
	
	/**
	 * The stack collection which backs this instance of DeckImpl.
	 */
	private final Stack<Card> cards;

	/**
	 * Private constructor.
	 * @param cards stack of cards to use to create this deck.
	 */
	private DeckImpl(final Stack<Card> cards) 
	{
	    this.cards = cards;
	}

	/**
	 * Pops the next card off the stack and returns it.
	 */
	@Override
	public Card removeNextCard() throws IllegalStateException 
	{
	    ExceptionUtil.assertLegalState(!cards.isEmpty(), "No cards in deck");
	    return cards.pop();
	}

	/**
	 * Returns the number of cards remaining in the deck.
	 */
	@Override
	public int cardsInDeck() 
	{
		return cards.size();
	}

	/**
	 * Shuffles the deck in place.
	 */
	@Override
	public void shuffleDeck()
	{
	    Collections.shuffle(cards);
	}

}