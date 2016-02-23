package com.samvbeckmann.ai.project1a;

/**
 * Exception for Datasets that are not formatted correctly.
 *
 * @author Sam Beckmann
 */
public class InvalidFileFormatException extends Exception
{
    public InvalidFileFormatException()
    {
        super();
    }

    public InvalidFileFormatException(String message)
    {
        super(message);
    }

    public InvalidFileFormatException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidFileFormatException(Throwable cause)
    {
        super(cause);
    }
}
