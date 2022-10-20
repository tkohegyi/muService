package website.magyar.muservice.web.json;

import website.magyar.muservice.helper.JsonField;

/**
 * Json structure that holds information about an uploadable test head data.
 */
public class TestHeadUploadDataJson {
    @JsonField
    public String id;
    @JsonField
    public String head;
    @JsonField
    public String status;
    @JsonField
    public String information;
}
