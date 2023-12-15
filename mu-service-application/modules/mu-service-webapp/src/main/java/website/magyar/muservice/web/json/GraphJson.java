package website.magyar.muservice.web.json;

import website.magyar.muservice.helper.JsonField;

import java.util.ArrayList;

/**
 * Json structure that is used to provide information for a person.
 */
public class GraphJson {
    @JsonField
    public ArrayList<SeriesJson> seriesA;
    @JsonField
    public ArrayList<SeriesJson> seriesB;
    @JsonField
    public String headInformation;
    @JsonField
    public String dateMin;
    @JsonField
    public String dateMax;
    @JsonField
    public String dataSize;
    @JsonField
    public String dataStartingPosition;
    @JsonField
    public String dataVisibleSize;
}
