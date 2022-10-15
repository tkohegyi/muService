package website.magyar.muservice.web.provider;

import website.magyar.muservice.database.business.BusinessWithPerson;
import website.magyar.muservice.database.business.BusinessWithSocial;
import website.magyar.muservice.web.json.CurrentUserInformationJson;
import website.magyar.muservice.web.json.InformationJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class to provide Information about users.
 */
@Component
public class InformationProvider {

    private final Logger logger = LoggerFactory.getLogger(InformationProvider.class);

    @Autowired
    private BusinessWithPerson businessWithPerson;
    @Autowired
    private BusinessWithSocial businessWithSocial;

    /**
     * Get overall information about a registered adorator.
     *
     * @return with the info in json object form
     */
    public Object getInformation(CurrentUserInformationJson currentUserInformationJson) {
        var informationJson = new InformationJson();
        //get name and status
        var personId = currentUserInformationJson.personId;
        var person = businessWithPerson.getPersonById(personId);
        if (person == null) {
            //wow, we should not be here
            logger.warn("User got access to prohibited area: {}", currentUserInformationJson.loggedInUserName);
            informationJson.error = "access denied";
        } else {
            //we have person info
            informationJson.name = person.getName();
            informationJson.status = "NA";
            informationJson.id = person.getId().toString();
        }
        return informationJson;
    }

}
