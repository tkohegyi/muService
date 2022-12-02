package website.magyar.muservice.web.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import website.magyar.muservice.database.business.BusinessWithTestHead;
import website.magyar.muservice.database.business.BusinessWithTestHeadData;
import website.magyar.muservice.database.business.helper.enums.PersonRoleTypes;
import website.magyar.muservice.database.tables.TestHead;
import website.magyar.muservice.database.tables.TestHeadData;
import website.magyar.muservice.web.json.CurrentUserInformationJson;
import website.magyar.muservice.web.json.ListJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to provide List of test heads for the users.
 */
@Component
public class ListProvider {

    private final Logger logger = LoggerFactory.getLogger(ListProvider.class);

    @Autowired
    BusinessWithTestHead businessWithTestHead;

    @Autowired
    BusinessWithTestHeadData businessWithTestHeadData;

    /**
     * Get overall information about a registered adorator.
     *
     * @return with the info in json object form
     */
    public Object getList(CurrentUserInformationJson currentUserInformationJson) {
        var result = new ArrayList<ListJson>();
        PersonRoleTypes role = PersonRoleTypes.getTypeFromId(currentUserInformationJson.personRole);
        if (role.equals(PersonRoleTypes.ADMINISTRATOR)) {
            List<TestHead> testHeadList = businessWithTestHead.getList();
            for (TestHead th: testHeadList) {
                var listJson = new ListJson();
                listJson.id = th.getId().toString();
                listJson.description = th.getDescription();
                listJson.type = th.getType();
                TestHeadData testHeadData = businessWithTestHeadData.getLastData(th.getHeadId());
                listJson.lastInformation = testHeadData.getInformation();
                listJson.lastInformationDate = testHeadData.getTimestamp();
                result.add(listJson);
            }
        }
        return result;
    }

}
