package website.magyar.muservice.web.controller;

import website.magyar.muservice.web.controller.helper.ControllerBase;
import website.magyar.muservice.web.json.CurrentUserInformationJson;
import website.magyar.muservice.web.json.TableDataInformationJson;
import website.magyar.muservice.web.provider.CurrentUserProvider;
import website.magyar.muservice.web.provider.InformationProvider;
import website.magyar.muservice.web.provider.PeopleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller for handling requests for the application pages about Information.
 *
 * @author Tamas Kohegyi
 */
@Controller
public class InformationController extends ControllerBase {
    private final Logger logger = LoggerFactory.getLogger(InformationController.class);
    @Autowired
    private CurrentUserProvider currentUserProvider;
    @Autowired
    private InformationProvider informationProvider;
    @Autowired
    private PeopleProvider peopleProvider;

    /**
     * Serves user requests for general Information.
     *
     * @return the name of the jsp to display as result
     */
    @GetMapping(value = "/adorationSecure/information")
    public String informationPage(HttpSession httpSession) {
        CurrentUserInformationJson currentUserInformationJson = currentUserProvider.getUserInformation(httpSession);
        if (currentUserInformationJson.isAuthorized) {
            return "information";
        }
        return REDIRECT_TO_HOME; //not even logged in -> go back to basic home page
    }

    /**
     * Serves general information data for the logged-in and known user.
     *
     * @param httpSession         identifies the user
     * @param httpServletResponse is used to build the response
     * @return with proper content
     */
    @ResponseBody
    @GetMapping(value = "/adorationSecure/getInformation")
    public TableDataInformationJson getInformation(HttpSession httpSession, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        TableDataInformationJson content = null;
        if (isAuthorized(currentUserProvider, httpSession)) {
            //has right to collect and see information
            var information = informationProvider.getInformation(currentUserProvider.getUserInformation(httpSession));
            content = new TableDataInformationJson(information);
        }
        return content;
    }

}
