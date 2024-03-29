package website.magyar.muservice.database.exception;

import website.magyar.muservice.exception.SystemException;

/**
 * Thrown when a database handling related exception occurs.
 */
public class DatabaseHandlingException extends SystemException {

    /**
     * Constructor that takes the exception message as input.
     *
     * @param message of the exception
     */
    public DatabaseHandlingException(final String message) {
        super(message);
    }

    /**
     * Constructor that takes a message and wrapped or parent exception as input.
     *
     * @param message   of the exception
     * @param throwable is the parent or wrapped exception.
     */
    public DatabaseHandlingException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
