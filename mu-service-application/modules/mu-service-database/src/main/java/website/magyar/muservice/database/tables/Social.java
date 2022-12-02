package website.magyar.muservice.database.tables;

import website.magyar.muservice.database.business.helper.enums.SocialStatusTypes;
import website.magyar.muservice.database.exception.DatabaseHandlingException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Descriptor class for Database table: Social.
 * Technically: Record of social (oauth2) login possibilities.
 */
@Entity
@Table(name = "public.social")
public class Social {

    private Long id;
    private Long personId;
    private Integer socialStatus;
    private String googleEmail;
    private String googleUserName;
    private String googleUserId;
    private String googleUserPicture;
    private String comment;

    /**
     * General constructor, used by Hibernate.
     * Shall be used only when a new record is created - then fields need to be filled of course before saving it to the database.
     */
    public Social() {
        // this form used by Hibernate
    }

    @Column(name = "id", nullable = false)
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "personid", nullable = true)
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Column(name = "socialstatus", nullable = false)
    public Integer getSocialStatus() {
        return socialStatus;
    }

    /**
     * Sets the id of the social status of the social user.
     *
     * @param socialStatus is the status id
     * @throws DatabaseHandlingException if the id is not valid
     */
    public void setSocialStatus(Integer socialStatus) {
        SocialStatusTypes.getTypeFromId(socialStatus); //validation
        this.socialStatus = socialStatus;
    }

    /**
     * Gets googleEmail field of a Social record, ensures that it never will have null value.
     *
     * @return with the googleEmail field value or with an empty string.
     */
    @Column(name = "googleemail", nullable = true)
    public String getGoogleEmail() {
        if (googleEmail != null) {
            return googleEmail;
        } else {
            return "";
        }
    }

    public void setGoogleEmail(String googleEmail) {
        this.googleEmail = googleEmail;
    }

    /**
     * Gets googleUserName field of a Social record, ensures that it never will have null value.
     *
     * @return with the googleUserName field value or with an empty string.
     */
    @Column(name = "googleusername", nullable = true)
    public String getGoogleUserName() {
        if (googleUserName != null) {
            return googleUserName;
        } else {
            return "";
        }
    }

    public void setGoogleUserName(String googleUserName) {
        this.googleUserName = googleUserName;
    }

    /**
     * Gets googleUserId field of a Social record, ensures that it never will have null value.
     *
     * @return with the googleUserId field value or with an empty string.
     */
    @Column(name = "googleuserid", nullable = true)
    public String getGoogleUserId() {
        if (googleUserId != null) {
            return googleUserId;
        } else {
            return "";
        }
    }

    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    /**
     * Gets googleUserPicture field of a Social record, ensures that it never will have null value.
     *
     * @return with the googleUserPicture field value or with an empty string.
     */
    @Column(name = "googleuserpicture", nullable = true)
    public String getGoogleUserPicture() {
        if (googleUserPicture != null) {
            return googleUserPicture;
        } else {
            return "";
        }
    }

    public void setGoogleUserPicture(String googleUserPicture) {
        this.googleUserPicture = googleUserPicture;
    }

    /**
     * Gets comment field of a Social record, ensures that it never will have null value.
     *
     * @return with the comment field value or with an empty string.
     */
    @Column(name = "comment", nullable = true)
    public String getComment() {
        if (comment != null) {
            return comment;
        } else {
            return "";
        }
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
