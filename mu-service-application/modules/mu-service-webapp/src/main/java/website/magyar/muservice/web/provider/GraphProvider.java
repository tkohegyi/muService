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
import website.magyar.muservice.web.json.SeriesJson;
import website.magyar.muservice.web.provider.helper.ProviderBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to provide Graphs for drawable test heads.
 */
@Component
public class GraphProvider extends ProviderBase {

    private final Logger logger = LoggerFactory.getLogger(GraphProvider.class);

    @Autowired
    BusinessWithTestHead businessWithTestHead;

    @Autowired
    BusinessWithTestHeadData businessWithTestHeadData;

    /**
     * Get overall information about a registered adorator.
     *
     * @return with the info in json object form
     */
    public Object getGraph(CurrentUserInformationJson currentUserInformationJson, long headId, boolean secondSeries) {
        DateTimeConverter dateTimeConverter = new DateTimeConverter();
        ArrayList<SeriesJson> seriesA = new ArrayList<>();
        ArrayList<SeriesJson> seriesB = new ArrayList<>();
        Boolean hasValuesB = Boolean.FALSE;

        PersonRoleTypes role = PersonRoleTypes.getTypeFromId(currentUserInformationJson.personRole);
        TestHead testHead = businessWithTestHead.getTestHeadById(String.valueOf(headId));
        Boolean hasSeriesB = testHead.getType().equalsIgnoreCase("dht11");
        if (role.equals(PersonRoleTypes.ADMINISTRATOR) && testHead != null && drawables.contains(testHead.getType())) {
            //lehet rajzolni, drawable és van access is
            try {
                int rows = businessWithTestHeadData.detectRows(testHead.getHeadId());
                int startPos = Math.max(rows - 1440, 0);
                List<TestHeadData> testHeadData = businessWithTestHeadData.getList(testHead.getHeadId(), startPos, 1440, false);
                //get max min temp
                for (TestHeadData data : testHeadData) {
                    String information = data.getInformation();
                    if (information != null && !information.isEmpty()) {
                        String[] values = information.split(";");
                        String timeStamp = data.getTimestamp();
                        SeriesJson seriesJson = new SeriesJson(timeStamp, Double.parseDouble(values[0]));
                        seriesA.add(seriesJson);
                        if (hasSeriesB) {
                            seriesJson = new SeriesJson(timeStamp, Double.parseDouble(values[1].replace("%","")));
                            seriesB.add(seriesJson);
                        }
                    }
                }
            } catch (NumberFormatException | ParseException ie) {
                ie.printStackTrace();
            }
        }
        if (secondSeries && hasSeriesB) {
            return seriesB;
        }
        return seriesA;
    }

}
