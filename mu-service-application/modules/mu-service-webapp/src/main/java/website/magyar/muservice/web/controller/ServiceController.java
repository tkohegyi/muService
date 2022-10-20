package website.magyar.muservice.web.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import website.magyar.muservice.web.controller.helper.ControllerBase;
import website.magyar.muservice.web.json.TestHeadUploadDataJson;
import website.magyar.muservice.web.provider.TestHeadServiceProvider;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for handling requests for the application home page.
 *
 * @author Tamas Kohegyi
 */
@Controller
public class ServiceController extends ControllerBase {
    private final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private TestHeadServiceProvider testHeadServiceProvider;

    /**
     * Serves incoming data posts.
     *
     * @param httpServletRequest to know from where it is coming
     * @return with proper content
     */
    @ResponseBody
    @PostMapping(value = "/appService/uploadData")
    public ResponseEntity<String> uploadData(@RequestBody final String body, HttpServletRequest httpServletRequest) {
        ResponseEntity<String> result;
        try {
            var g = new Gson();
            var testHeadUploadDataJson = g.fromJson(body, TestHeadUploadDataJson.class);
            var isTestHeadValid = testHeadServiceProvider.isTestHeadValid(testHeadUploadDataJson);
            if (isTestHeadValid) {
                String resultString = handleValidTestHeadUploadData(testHeadUploadDataJson);
                result = buildResponseBodyResult(JSON_RESPONSE_STATUS, resultString, HttpStatus.OK);
            } else {
                //it is either a trial to break in or just misconfiguration - anyway, refuse it
                logger.warn("Request from an invalid test head, pls contact to maintainers: {} from {}", testHeadUploadDataJson.id, httpServletRequest.getRemoteHost());
                result = buildResponseBodyResult(UNAUTHORIZED_ACTION, UNAUTHORIZED_ACTION, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception e) {
            logger.warn("Error happened at TestHead Upload data, pls contact to maintainers", e);
            result = buildResponseBodyResult(UNAUTHORIZED_ACTION, UNAUTHORIZED_ACTION, HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    private String handleValidTestHeadUploadData(TestHeadUploadDataJson testHeadUploadDataJson) {
        Long id = testHeadServiceProvider.uploadData(testHeadUploadDataJson);
        String result = id == null ? "null" : id.toString();
        return result;
    }

}
