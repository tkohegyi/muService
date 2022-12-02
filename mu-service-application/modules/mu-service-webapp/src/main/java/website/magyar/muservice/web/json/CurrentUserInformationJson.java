package website.magyar.muservice.web.json;

import website.magyar.muservice.database.tables.Person;
import website.magyar.muservice.database.tables.Social;
import website.magyar.muservice.helper.JsonField;

/**
 * Json structure to hold information about the actual user.
 */
public class CurrentUserInformationJson {

    @JsonField
    public boolean isLoggedIn;
    @JsonField
    public boolean isAuthorized;
    @JsonField
    public Integer personRole;
    @JsonField
    public Long personId;
    @JsonField
    public Long socialId;
    @JsonField
    public String socialEmail;
    @JsonField
    public String loggedInUserName;
    @JsonField
    public String userName;
    @JsonField
    public String socialServiceUsed;
    @JsonField
    public String applicationVersion;

    /**
     * Constructor - fills the json structure with default values.
     */
    public CurrentUserInformationJson() {
        reset();
    }

    /**
     * Fill the json structure with basic and default (user not logged in) information.
     */
    public void reset() {
        personId = null;
        socialId = null;
        socialEmail = "";
        isLoggedIn = false;
        isAuthorized = false;
        personRole = 0;
        loggedInUserName = "Anonymous";
        userName = loggedInUserName;
        socialServiceUsed = "Undetermined";
        applicationVersion = "MuService - unknown version.";
    }

    /**
     * Fill json fields from a given Person.
     *
     * @param person      is the person
     */
    public void fillIdentifiedPersonFields(Person person) {
        isAuthorized = true; //not just logged in, but since the person is known, authorized too
        personId = person.getId();
        userName = person.getName();
        personRole = person.getRole();
    }

    /**
     * Fills json fields from the Social data.
     *
     * @param social is the Social data
     */
    public void fillIdentifiedSocialFields(Social social) {
        socialId = social.getId();
        String email = social.getGoogleEmail();
        socialEmail = email;
    }

    /**
     * Fill Application version.
     */
    public void fillApplicationVersion(final String adorationApplicationVersion) {
        this.applicationVersion = adorationApplicationVersion;
    }

}