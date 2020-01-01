package org.rockhill.muService.testHead.core;

import org.slf4j.Logger;

/**
 * This exception acts as a base class for all types of system exceptions, that can occur when something not related to the business logic fails.
 * @author Tamas_Kohegyi
 *
 */
public class SystemException extends RuntimeException {

    /**
     * Creates a new SystemException.
     * @param message Message of the exception.
     * @param cause Cause of the exception.
     */
    public SystemException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Logs the stack trace of the exception.
     * @param logger Logger that is used to log the exception
     */
    public void logStackTrace(final Logger logger) {
        logger.error(getMessage());
        logger.error("Caused by: " + getCause().getClass() + " " + getCause().getMessage());
        String stackTrace = "";
        for (StackTraceElement element : getStackTrace()) {
            stackTrace += "\n\t" + element.toString();
        }
        logger.debug(stackTrace);
    }
}
