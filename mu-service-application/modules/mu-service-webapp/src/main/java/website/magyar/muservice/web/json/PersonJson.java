package website.magyar.muservice.web.json;

import website.magyar.muservice.database.tables.Person;
import website.magyar.muservice.helper.JsonField;

/**
 * Json structure to be used about a person when the information is provided to a non-privileged person.
 */
public class PersonJson {

    @JsonField
    public Long id;
    @JsonField
    public String name;
    @JsonField
    public String email;
    @JsonField
    public String mobile;

    /**
     * Create a data class.
     * If requestor is privileged user, then returns with all fields.
     * If requestor is not privileged, then:
     * - Only ID is available in case DCH is not enabled.
     * - Name is "Anonymous" in case person would like to remain anonymous, and only id and mobile fields are filled.
     * - email/mobile is visible based on visibility flag.
     * - coordinator comment requires privileged user
     * - visible comment is always available
     *
     * @param person
     */
    public PersonJson(Person person, Boolean isPrivilegedUser) {
        this.id = person.getId();
        this.name = person.getName();
        this.email = person.getEmail();
        this.mobile = person.getMobile();
    }
}


