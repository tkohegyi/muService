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
import website.magyar.muservice.web.json.GraphJson;
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
    private static final int DEFAULT_VISIBLE_ROWS = 1440;
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
    public Object getGraph(CurrentUserInformationJson currentUserInformationJson, long headId, int startingPos, int visibleRows) {
        GraphJson graphJson = new GraphJson();
        ArrayList<SeriesJson> seriesA = new ArrayList<>();
        ArrayList<SeriesJson> seriesB = new ArrayList<>();

        PersonRoleTypes role = PersonRoleTypes.getTypeFromId(currentUserInformationJson.personRole);
        TestHead testHead = businessWithTestHead.getTestHeadById(String.valueOf(headId));
        boolean hasSeriesB = testHead.getType().equalsIgnoreCase("dht11");
        if (role.equals(PersonRoleTypes.ADMINISTRATOR) && drawables.contains(testHead.getType())) {
            //lehet rajzolni, drawable és van access is
            try {
                int rows = businessWithTestHeadData.detectRows(testHead.getHeadId());
                graphJson.dataSize = String.valueOf(rows);
                graphJson.headInformation = testHead.getDescription();

                //set visible rows
                if (visibleRows < 1) {
                    visibleRows = DEFAULT_VISIBLE_ROWS;
                }
                if (visibleRows > rows) {
                    visibleRows = rows;
                } //we have visibleRows set, and it is >= 1
                //set start pos: if startingPos is not defined or too close to its end, then use default
                if (startingPos < 0) {
                    startingPos = rows - DEFAULT_VISIBLE_ROWS;
                }
                if (startingPos + visibleRows > rows) {
                    startingPos = rows - visibleRows;
                }
                if (startingPos < 0) {
                    startingPos = 0;
                }

                graphJson.dataStartingPosition = String.valueOf(startingPos);
                List<TestHeadData> testHeadData = businessWithTestHeadData.getList(testHead.getHeadId(), startingPos, visibleRows, false);
                graphJson.dataVisibleSize = String.valueOf(testHeadData.size());
                //get max min temp
                for (TestHeadData data : testHeadData) {
                    String information = data.getInformation();
                    if (information != null && !information.isEmpty()) {
                        String[] values = information.split(";");
                        String timeStamp = data.getTimestamp();
                        if (graphJson.dateMin == null) {
                            graphJson.dateMin = timeStamp;
                        }
                        graphJson.dateMax = timeStamp;
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
        graphJson.seriesA = seriesA;
        graphJson.seriesB = seriesB;
        return graphJson;
    }

}
