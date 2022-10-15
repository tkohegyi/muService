package website.magyar.muservice.database.tables;

import website.magyar.muservice.database.exception.DatabaseHandlingException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Descriptor class for Database table: Person.
 * Technically: People.
 */
@Entity
@Table(name = "dbo.person")
public class Person {

    private Long id;
    private String name;
    private String mobile;
    private String email; //single e-mail
    private String comment;

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

}
