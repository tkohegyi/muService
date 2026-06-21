package website.magyar.muservice.web.json;

import website.magyar.muservice.helper.JsonField;

import java.util.ArrayList;

/**
 * Json structure for WAN IP timeline data.
 */
public class WanIpJson {
    @JsonField
    public String description;
    @JsonField
    public String type;
    @JsonField
    public ArrayList<WanIpPointJson> points;
}
