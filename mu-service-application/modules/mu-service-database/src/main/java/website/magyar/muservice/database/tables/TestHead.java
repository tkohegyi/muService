package website.magyar.muservice.database.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Descriptor class for Database table: TestHead.
 * Technically: head.
 */
@Entity
@Table(name = "public.head")
public class TestHead {

    private Long id;
    private String headId;
    private String type;
    private String description;
    private Boolean active;

    /**
     * General constructor, used by Hibernate.
     * Shall be used only when a new record is created - then fields need to be filled of course before saving it to the database.
     */
    public TestHead() {
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

    @Column(name = "headid", nullable = false)
    public String getHeadId() {
        return headId;
    }

    /**
     * Sets the head id field.
     *
     * @param headId is the agreed head id.
     */
    public void setHeadId(String headId) {
        this.headId = headId;
    }

    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    /**
     * Sets the type field.
     *
     * @param type is type of the head.
     */
    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description field.
     *
     * @param description is the test head description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "active", nullable = false)
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the active field.
     *
     * @param active is the test head specific information.
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

}
