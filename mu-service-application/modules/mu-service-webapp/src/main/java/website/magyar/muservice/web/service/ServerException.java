package website.magyar.muservice.web.service;

import website.magyar.muservice.exception.SystemException;

/**
 * It is thrown if an internal exception occurs in the jetty server.
 */
public class ServerException extends SystemException {

    /**
     * Contstructs a new exception with a given <tt>message</tt>.
     *
     * @param message cause of the error
     * @param e       causing error
     */
    public ServerException(final String message, final Throwable e) {
        super(message, e);
    }

}
