package com.samvbeckmann.ai.project1;

/**
 * Created by sam on 1/22/16.
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
