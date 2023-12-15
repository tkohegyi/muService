package website.magyar.muservice.web.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import website.magyar.muservice.web.controller.helper.ControllerBase;
import website.magyar.muservice.web.json.CurrentUserInformationJson;
import website.magyar.muservice.web.json.TableDataInformationJson;
import website.magyar.muservice.web.provider.CurrentUserProvider;
import website.magyar.muservice.web.provider.GraphProvider;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller for handling requests to show graphs.
 *
 * @author Tamas Kohegyi
 */
@Controller
public class GraphController extends ControllerBase {
    private final Logger logger = LoggerFactory.getLogger(GraphController.class);
    @Autowired
    private CurrentUserProvider currentUserProvider;
    @Autowired
    private GraphProvider graphProvider;

    /**
     * Serves user requests for general Information.
     *
     * @return the name of the jsp to display as result
     */
    @GetMapping(value = "/appSecure/getGraph/{id}")
    public String informationPage(HttpSession httpSession, @PathVariable("id") long id, ModelMap modelMap) {
        CurrentUserInformationJson currentUserInformationJson = currentUserProvider.getUserInformation(httpSession);
        if (currentUserInformationJson.isAuthorized) {
            modelMap.addAttribute("headId", id);
            return "graph";
        }
        return REDIRECT_TO_HOME; //not even logged in -> go back to basic home page
    }

    /**
     * Serves user requests for general Information.
     *
     * @return the name of the jsp to display as result
     */
    @GetMapping(value = "/appSecure/getGraphSub/{id}")
    public ResponseEntity<String> informationSubPage(HttpSession httpSession, @PathVariable("id") long id) {
        CurrentUserInformationJson currentUserInformationJson = currentUserProvider.getUserInformation(httpSession);
        ResponseEntity<String> result = buildUnauthorizedActionBodyResult();
        if (currentUserInformationJson.isAuthorized) {
            Object jsonObject = graphProvider.getGraph(currentUserInformationJson, id, false);
            result = buildResponseBodyResult(JSON_RESPONSE_STATUS, jsonObject, HttpStatus.OK);
        }
        return result;
    }

}
