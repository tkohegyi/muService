package website.magyar.muservice.web.json;

import website.magyar.muservice.helper.JsonField;

/**
 * Json structure that is used to provide list.
 */
public class ListJson {
    @JsonField
    public String description;
    @JsonField
    public String type;
    @JsonField
    public String id;   //id
    @JsonField
    public String lastInformation;
    @JsonField
    public String lastInformationDate;
}
