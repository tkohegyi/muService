package com.epam.wilma.webapp.configuration.domain;

/**
 * Holds the properties required for the web server.
 * @author Tamas Kohegyi
 *
 */
public class ServerProperties {

    private final int serverPort;

    private final int requestBufferSize;
    private final int responseBufferSize;

    /**
     * Constructor for {@link ServerProperties}.
     * @param serverPort the internal port of the web application
     * @param requestBufferSize the buffer size (in bytes) for receiving requests
     * @param responseBufferSize the buffer size (in bytes) for sending responses
     */
    public ServerProperties(final int serverPort, final int requestBufferSize, final int responseBufferSize) {
        this.serverPort = serverPort;
        this.requestBufferSize = requestBufferSize;
        this.responseBufferSize = responseBufferSize;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getRequestBufferSize() {
        return requestBufferSize;
    }

    public int getResponseBufferSize() {
        return responseBufferSize;
    }

}
