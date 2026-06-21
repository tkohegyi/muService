package website.magyar.muservice.web.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import website.magyar.muservice.database.business.BusinessWithTestHead;
import website.magyar.muservice.database.business.BusinessWithTestHeadData;
import website.magyar.muservice.database.business.helper.DateTimeConverter;
import website.magyar.muservice.database.business.helper.enums.PersonRoleTypes;
import website.magyar.muservice.database.tables.TestHead;
import website.magyar.muservice.database.tables.TestHeadData;
import website.magyar.muservice.web.json.CurrentUserInformationJson;
import website.magyar.muservice.web.json.WanIpJson;
import website.magyar.muservice.web.json.WanIpPointJson;
import website.magyar.muservice.web.provider.helper.ProviderBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to provide WAN IP timeline data.
 */
@Component
public class WanIpProvider extends ProviderBase {

    private static final int DEFAULT_HOURS_TO_SHOW = 720;
    private static final int MAX_HOURS_TO_SHOW = 8760;
    private final Logger logger = LoggerFactory.getLogger(WanIpProvider.class);

    @Autowired
    BusinessWithTestHead businessWithTestHead;

    @Autowired
    BusinessWithTestHeadData businessWithTestHeadData;

    /**
     * Get WAN IP data for the given head, number of hours, and window end time.
     *
     * @return with the info in json object form
     */
    public Object getData(CurrentUserInformationJson currentUserInformationJson, long id, int hours, long toMs) {
        WanIpJson wanIpJson = new WanIpJson();
        wanIpJson.points = new ArrayList<>();

        PersonRoleTypes role = PersonRoleTypes.getTypeFromId(currentUserInformationJson.personRole);
        TestHead testHead = businessWithTestHead.getTestHeadById(String.valueOf(id));
        if (!role.equals(PersonRoleTypes.ADMINISTRATOR) || testHead == null || !"wanip".equals(testHead.getType())) {
            return wanIpJson;
        }

        wanIpJson.description = testHead.getDescription();
        wanIpJson.type = testHead.getType();

        int hoursToShow = (hours < 1 || hours > MAX_HOURS_TO_SHOW) ? DEFAULT_HOURS_TO_SHOW : hours;
        DateTimeConverter dateTimeConverter = new DateTimeConverter();
        Date toDate = new Date(toMs);
        Date fromDate = new Date(toMs - (long) hoursToShow * DateTimeConverter.HOUR_IN_MS);
        String fromTimestamp = dateTimeConverter.getDateTimeAsString(fromDate);
        String toTimestamp = dateTimeConverter.getDateTimeAsString(toDate);
        List<TestHeadData> dataList = businessWithTestHeadData.getListFromTimestampRange(testHead.getHeadId(), fromTimestamp, toTimestamp);

        for (TestHeadData data : dataList) {
            try {
                WanIpPointJson point = new WanIpPointJson(data.getTimestamp(), data.getStatus());
                wanIpJson.points.add(point);
            } catch (ParseException e) {
                logger.warn("Skipping WAN IP data point with unparseable timestamp: {}", data.getTimestamp());
            }
        }

        return wanIpJson;
    }

}
