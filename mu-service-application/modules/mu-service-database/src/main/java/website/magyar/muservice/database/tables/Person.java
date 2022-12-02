package website.magyar.muservice.database.tables;

import website.magyar.muservice.database.business.helper.enums.PersonRoleTypes;
import website.magyar.muservice.database.exception.DatabaseHandlingException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Descriptor class for Database table: Person.
 * Technically: People.
 */
@Entity
@Table(name = "public.person")
public class Person {

    private Long id;
    private String name;
    private String mobile;
    private String email; //single e-mail
    private String comment;
    private Integer role;
    private Boolean dhcSigned;
    private String dhcSignedDate;


    /**
     * General constructor, used by Hibernate.
     * Shall be used only when a new record is created - then fields need to be filled of course before saving it to the database.
     */
    public Person() {
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

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    /**
     * Sets the name filed of the Person record.
     *
     * @param name is the name of the Person.
     * @throws DatabaseHandlingException in case the name is an empty string or null.
     */
    public void setName(String name) {
        if ((name == null) || (name.length() == 0)) {
            throw new DatabaseHandlingException("Person name tried to set to null/zero length.");
        }
        this.name = name;
    }

    /**
     * Gets mobile field of a Person record, ensures that it never will have null value.
     *
     * @return with the mobile field value or with an empty string.
     */
    @Column(name = "mobile", nullable = true)
    public String getMobile() {
        if (mobile != null) {
            return mobile;
        } else {
            return "";
        }
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets email field of a Person record, ensures that it never will have null value.
     *
     * @return with the email field value or with an empty string.
     */
    @Column(name = "email", nullable = true)
    public String getEmail() {
        if (email != null) {
            return email;
        } else {
            return "";
        }
    }

    /**
     * Sets email field, ensures that regardless of the input, only lowercase version stored.
     *
     * @param email is the value of the field
     */
    public void setEmail(String email) {
        if (email != null) {
            this.email = email.toLowerCase();
        } else {
            this.email = null;
        }
    }

    /**
     * Gets adminComment field of a Person record, ensures that it never will have null value.
     *
     * @return with the adminComment field value or with an empty string.
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

    @Column(name = "role", nullable = false)
    public Integer getRole() {
        return role;
    }

    /**
     * Sets the role status by using the integer version of the enum.
     *
     * @param role is the integer identifier of the enum
     * @throws DatabaseHandlingException in case the id is not valid
     */
    public void setRole(Integer role) {
        PersonRoleTypes.getTypeFromId(role); //this validates the value
        this.role = role;
    }

    @Column(name = "dhcsigned", nullable = false)
    public Boolean getDhcSigned() {
        return dhcSigned;
    }

    public void setDhcSigned(Boolean dhcSigned) {
        this.dhcSigned = dhcSigned;
    }

    /**
     * Gets dhcSignedDate field of a Person record, ensures that it never will have null value.
     *
     * @return with the dhcSignedDate field value or with an empty string.
     */
    @Column(name = "dhcsigneddate", nullable = true)
    //this also might be null
    public String getDhcSignedDate() {
        if (dhcSignedDate != null) {
            return dhcSignedDate;
        } else {
            return "";
        }
    }

    /**
     * Sets the data handling consent date according to the given string, that must be in "YYYY-MM-DD" format.
     *
     * @param dhcSignedDate is the string format of the date
     * @throws DatabaseHandlingException if the string format/content is invalid
     */
    public void setDhcSignedDate(String dhcSignedDate) {
        if ((dhcSignedDate != null) && (dhcSignedDate.length() > 0)) {
            try {
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                df1.parse(dhcSignedDate);
            } catch (ParseException e) {
                throw new DatabaseHandlingException("DhcSignedDate date format is unacceptable, it must be YYYY-MM-DD");
            }
        }
        this.dhcSignedDate = dhcSignedDate;
    }

}
