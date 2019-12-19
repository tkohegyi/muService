package org.rockhill.muService.serverService;

import java.util.UUID;

/**
 * The main class of the Server Service.
 *
 * @author tamas_kohegyi
 */
public class ServerServiceCore {

    private UUID randomUUID;
    /**
     * The main method of this service - generates the Server Service answer.
     *
     * @param input is the input string
     * @return with the result of the iteration
     */
    protected String workWithCommand(final String input) {
        randomUUID = UUID.randomUUID();
        String response = "{ \"result\": \"" + randomUUID.toString() + "\" }";
        return response;
    }

}
