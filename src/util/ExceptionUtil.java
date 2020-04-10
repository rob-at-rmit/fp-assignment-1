package util;

/**
 * Utilitiy class to help with assertions and method parameter validation
 * consistently.
 * 
 * @author Robert Beardow, Student ID 3461721
 */
public final class ExceptionUtil
{
    
    /**
     * Throws a {@link java.lang.NullPointerException} if the specified object is null, and
     * presents the specified message.
     * @param object object to test.
     * @param message message to use in exception.
     */
    public static void assertNotNull(final Object object, final String message)
    {
        if (object == null)
        {
            throw new NullPointerException(message);
        }
    }
    
    /**
     * Throws a {@link java.lang.IllegalArgumentException} if the specified 
     * predicate is resolved to false.
     * @param object predicate to test.
     * @param message message to use in exception.
     */
    public static void assertLegalArgument(final boolean predicate, final String message)
    {
        if (!predicate) 
        {
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * Throws a {@link java.lang.IllegalStateException} if the specified 
     * predicate is resolved to false.
     * @param object predicate to test.
     * @param message message to use in exception.
     */
    public static void assertLegalState(final boolean predicate, final String message)
    {
        if (!predicate)
        {
            throw new IllegalStateException(message);
        }
    }

}