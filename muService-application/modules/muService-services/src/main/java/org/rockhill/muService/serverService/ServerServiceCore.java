package org.rockhill.muService.serverService;
/**
 * The main class of the Server Service.
 *
 * @author tamas_kohegyi
 */
public class ServerServiceCore {

    /**
     * The main method of this service - generates the Server Service answer.
     *
     * @param input is the input string
     * @return with the result of the iteration
     */
    protected String workWithCommand(final String input) {
        String response = "{ \"result\": \"I am alive\" }";
        return response;
    }

}
