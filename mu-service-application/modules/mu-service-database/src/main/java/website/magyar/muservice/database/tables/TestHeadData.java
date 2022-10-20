package website.magyar.muservice.database.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Descriptor class for Database table: TestHeadData.
 * Technically: data.
 */
@Entity
@Table(name = "public.data")
public class TestHeadData {

    private Long id;
    private String head;
    private String timestamp;
    private String status;
    private String information;

    /**
     * General constructor, used by Hibernate.
     * Shall be used only when a new record is created - then fields need to be filled of course before saving it to the database.
     */
    public TestHeadData() {
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

    @Column(name = "head", nullable = false)
    public String getHead() {
        return head;
    }

    /**
     * Sets the head field.
     *
     * @param head is the agreed head id.
     */
    public void setHead(String head) {
        this.head = head;
    }

    @Column(name = "timestamp", nullable = false)
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp field.
     *
     * @param timestamp is the data creation timestamp in YYYY-MM-DD HH:NN:SSS format.
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status field.
     *
     * @param status is the test head status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "information", nullable = false)
    public String getInformation() {
        return information;
    }

    /**
     * Sets the information field.
     *
     * @param information is the test head specific data information.
     */
    public void setInformation(String information) {
        this.information = information;
    }

}
