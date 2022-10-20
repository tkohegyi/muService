package website.magyar.muservice.database.business;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import website.magyar.muservice.database.SessionFactoryHelper;
import website.magyar.muservice.database.business.helper.BusinessBase;
import website.magyar.muservice.database.tables.TestHeadData;

/**
 * Business class to handle TestHeadData table, that is to store received measurements/data.
 */
@Component
public class BusinessWithTestHeadData extends BusinessBase {
    private final Logger logger = LoggerFactory.getLogger(BusinessWithTestHeadData.class);

    /**
     * Create new TestHeadData object.
     *
     * @param testHeadData  is the object to be saved
     * @return with the Id of the new object
     */
    public Long newTestHeadData(@NotNull TestHeadData testHeadData) {
        Long id = null;
        Session session = SessionFactoryHelper.getOpenedSession();
        try {
            session.beginTransaction();
            session.save(testHeadData); //insert into table !
            session.getTransaction().commit();
            id = testHeadData.getId();
            logger.info("TestHeadData record created successfully: {}", id);
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.warn("TestHeadData record creation failure", e);
        }
        session.close();
        return id;
    }

}
