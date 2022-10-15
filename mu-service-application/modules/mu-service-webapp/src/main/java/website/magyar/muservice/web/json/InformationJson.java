package website.magyar.muservice.web.json;

import website.magyar.muservice.helper.JsonField;

/**
 * Json structure that is used to provide information for a person.
 */
public class InformationJson {
    @JsonField
    public String error; // filled only in case of error
    @JsonField
    public String name; //name of the person
    @JsonField
    public String status;   //status of the person
    @JsonField
    public String id;   //id of the person
}
