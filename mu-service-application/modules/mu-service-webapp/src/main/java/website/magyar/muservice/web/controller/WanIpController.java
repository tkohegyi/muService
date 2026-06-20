package website.magyar.muservice.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import website.magyar.muservice.web.controller.helper.ControllerBase;
import website.magyar.muservice.web.json.CurrentUserInformationJson;
import website.magyar.muservice.web.provider.CurrentUserProvider;
import website.magyar.muservice.web.provider.WanIpProvider;

import javax.servlet.http.HttpSession;

/**
 * Controller for handling requests to show WAN IP timeline.
 *
 * @author Tamas Kohegyi
 */
@Controller
public class WanIpController extends ControllerBase {
    private final Logger logger = LoggerFactory.getLogger(WanIpController.class);

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Autowired
    private WanIpProvider wanIpProvider;

    /**
     * Serves the WAN IP timeline page.
     *
     * @return the name of the jsp to display as result
     */
    @GetMapping(value = "/appSecure/getWanIp/{id}")
    public String wanIpPage(HttpSession httpSession, @PathVariable("id") long id, ModelMap modelMap) {
        CurrentUserInformationJson currentUserInformationJson = currentUserProvider.getUserInformation(httpSession);
        if (currentUserInformationJson.isAuthorized) {
            modelMap.addAttribute("headId", id);
            return "wanip";
        }
        return REDIRECT_TO_HOME;
    }

    /**
     * Serves WAN IP timeline data as JSON (defaults to 30 days).
     *
     * @return with proper content
     */
    @GetMapping(value = "/appSecure/getWanIpData/{id}")
    public ResponseEntity<String> wanIpData(HttpSession httpSession, @PathVariable("id") long id) {
        return wanIpDataForHours(httpSession, id, 720);
    }

    /**
     * Serves WAN IP timeline data as JSON for the given number of hours.
     *
     * @return with proper content
     */
    @GetMapping(value = "/appSecure/getWanIpData/{id}/{hours}")
    public ResponseEntity<String> wanIpDataForHours(HttpSession httpSession, @PathVariable("id") long id,
            @PathVariable("hours") int hours) {
        CurrentUserInformationJson currentUserInformationJson = currentUserProvider.getUserInformation(httpSession);
        ResponseEntity<String> result = buildUnauthorizedActionBodyResult();
        if (currentUserInformationJson.isAuthorized) {
            Object jsonObject = wanIpProvider.getData(currentUserInformationJson, id, hours);
            result = buildResponseBodyResult(JSON_RESPONSE_STATUS, jsonObject, HttpStatus.OK);
        }
        return result;
    }

}
