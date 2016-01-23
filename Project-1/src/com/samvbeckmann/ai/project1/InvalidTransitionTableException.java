package com.samvbeckmann.ai.project1;

/**
 * Exception thrown when a transition table does not
 * have probabilities that add up to one.
 * Indicates error with transition table.
 *
 * @author Sam Beckmann
 */
public class InvalidTransitionTableException extends Exception
{
    public InvalidTransitionTableException()
    {
        super();
    }

    public InvalidTransitionTableException(String message)
    {
        super(message);
    }

    public InvalidTransitionTableException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidTransitionTableException(Throwable cause)
    {
        super(cause);
    }
}
