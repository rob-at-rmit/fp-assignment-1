package util;

/**
 * TODO: 
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public final class ExceptionUtil
{
    
    public static void assertNotNull(final Object object, final String message)
    {
        if (object == null)
        {
            throw new NullPointerException(message);
        }
    }
    
    public static void assertLegalArgument(final boolean predicate, final String message)
    {
        if (!predicate) 
        {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void assertLegalState(final boolean predicate, final String message)
    {
        if (!predicate)
        {
            throw new IllegalStateException(message);
        }
    }
    

}