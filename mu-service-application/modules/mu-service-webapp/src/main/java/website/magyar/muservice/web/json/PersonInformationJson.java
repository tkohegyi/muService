package website.magyar.muservice.web.json;

import website.magyar.muservice.helper.JsonField;

/**
 * Json structure that hold information about a specific person. Used for coordinators and other privileged users only.
 * For non-privileged users, the PersonJson class is used.
 */
public class PersonInformationJson {
    @JsonField
    public String id;
    @JsonField
    public String name;
    @JsonField
    public String mobile;
    @JsonField
    public String email;
    @JsonField
    public String comment;
}
