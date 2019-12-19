package com.epam.wilma.common.helper;

/**
 * Interface for representing a service which can be stopped and started.
 */
public interface muService {

    /**
     * Starts the service on the same thread.
     */
    void start();

    /**
     * Stops the service (asynchronously).
     */
    void stop();
}
