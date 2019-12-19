package org.rockhill.muService.serverService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The main class of the Server Service.
 *
 * @author tamas_kohegyi
 */
public class ServerServiceCore {
    private final String ERROR_MESSAGE = "{ \"result\": \"E404\" }";
    private final long TOKEN_VALIDITY_DURATION = 5000;  //in millisec

    private final Map<String, TestHeadDTO> testHeadMap = new HashMap<>();
    private final Map<String, TestHeadDTO> centerMap = new HashMap<>();

    private UUID randomUUID;
    /**
     * The main method of this service - generates the Server Service answer.
     *
     * @param o is the incoming JSON object
     * @return with the result of the iteration
     */
    protected String workWithCommand(final JSONObject o) {
        String response = ERROR_MESSAGE;
        String command = o.getString("command");
        String id = o.getString("id");
        if (command == null || id == null) {
            return response;
        }
        //we have command, now we just need to work with it
        if ("requestSSToken".equalsIgnoreCase(command)) {
            TestHeadDTO testHeadDTO = testHeadMap.get(id);
            if (testHeadDTO == null) { return response; }  //if id is wrong, ignore the request
            String randomUUID = UUID.randomUUID().toString();
            testHeadDTO.setActualToken(randomUUID);
            testHeadDTO.setValidity(System.currentTimeMillis() + TOKEN_VALIDITY_DURATION);
            testHeadMap.replace(id, testHeadDTO);
            response = "{ \"result\": \"" + randomUUID.toString() + "\" }";
        }
        if ("addTestHead".equalsIgnoreCase(command)) {
            TestHeadDTO testHeadDTO = new TestHeadDTO();
            testHeadMap.put(id, testHeadDTO);
            response = "{ \"result\": \"added\" }";
        }
        return response;
    }

}
