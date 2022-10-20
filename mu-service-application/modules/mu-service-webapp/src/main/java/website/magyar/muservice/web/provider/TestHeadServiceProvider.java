package website.magyar.muservice.web.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import website.magyar.muservice.database.business.BusinessWithNextGeneralKey;
import website.magyar.muservice.database.business.BusinessWithTestHead;
import website.magyar.muservice.database.business.BusinessWithTestHeadData;
import website.magyar.muservice.database.business.helper.DateTimeConverter;
import website.magyar.muservice.database.tables.TestHead;
import website.magyar.muservice.database.tables.TestHeadData;
import website.magyar.muservice.web.json.TestHeadUploadDataJson;

/**
 * Class to provide service for Test Heads.
 */
@Component
public class TestHeadServiceProvider {

    @Autowired
    BusinessWithTestHead businessWithTestHead;

    @Autowired
    BusinessWithTestHeadData businessWithTestHeadData;

    @Autowired
    BusinessWithNextGeneralKey businessWithNextGeneralKey;

    public boolean isTestHeadValid(TestHeadUploadDataJson testHeadUploadDataJson) {
        TestHead testHead = businessWithTestHead.getTestHeadByHeadId(testHeadUploadDataJson.id);
        return testHead != null;
    }

    /**
     *
     * @param testHeadUploadDataJson
     * @return with the ID of the just created record.
     */
    public Long uploadData(TestHeadUploadDataJson testHeadUploadDataJson) {
        Long id = null;
        TestHead testHead = businessWithTestHead.getTestHeadByHeadId(testHeadUploadDataJson.id);
        if (testHead != null && testHead.getActive()) {
            TestHeadData testHeadData = new TestHeadData();
            testHeadData.setId(businessWithNextGeneralKey.getNextGeneralId());
            testHeadData.setHead(testHead.getHeadId());
            testHeadData.setStatus(testHeadUploadDataJson.status);
            testHeadData.setInformation(testHeadUploadDataJson.information);
            DateTimeConverter dateTimeConverter = new DateTimeConverter();
            testHeadData.setTimestamp(dateTimeConverter.getCurrentDateTimeAsString());
            id = businessWithTestHeadData.newTestHeadData(testHeadData);
        }
        return id;
    }
}
