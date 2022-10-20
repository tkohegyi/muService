package website.magyar.muservice.database.business;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import website.magyar.muservice.database.SessionFactoryHelper;
import website.magyar.muservice.database.business.helper.BusinessBase;
import website.magyar.muservice.database.tables.TestHead;
import website.magyar.muservice.database.tables.TestHeadData;

import java.util.List;

/**
 * Business class to handle TestHead table, that is to list the existing test heads.
 */
@Component
public class BusinessWithTestHead extends BusinessBase {
    private final Logger logger = LoggerFactory.getLogger(BusinessWithTestHead.class);

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
            logger.info("TestHead record created successfully: {}", id);
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.warn("TestHead record creation failure", e);
        }
        session.close();
        return id;
    }

    /**
     * Get (search for) a specific test head by specifying its head id.
     *
     * @param headId is the id of the head
     * @return with the TestHead info, if found, or null, if not found
     */
    public TestHead getTestHeadByHeadId(@NotNull final String headId) {
        Session session = SessionFactoryHelper.getOpenedSession();
        session.beginTransaction();
        String hql = "from TestHead as T where T.headId = :expectedName";
        Query<TestHead> query = session.createQuery(hql, TestHead.class);
        query.setParameter("expectedName", headId);
        List<TestHead> result = query.list();
        session.getTransaction().commit();
        session.close();
        return (TestHead) returnWithFirstItem(result);
    }

}
