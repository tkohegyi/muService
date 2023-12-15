package website.magyar.muservice.database.business;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import website.magyar.muservice.database.SessionFactoryHelper;
import website.magyar.muservice.database.business.helper.BusinessBase;
import website.magyar.muservice.database.tables.TestHeadData;

import java.util.List;

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

    public TestHeadData getLastData(String headId) {
        TestHeadData thd;
        List<TestHeadData> result = getList(headId, 0, 1, true);
        if (result != null && !result.isEmpty()) {
            thd = result.get(0);
        } else {
            thd = new TestHeadData();
            thd.setTimestamp("N/A");
            thd.setInformation("N/A");
        }
        return thd;
    }

    /**
     * Get full list of a TestHead data, by specifying its head id.
     *
     * @return with the list
     */
    public List<TestHeadData> getList(String headId, Integer startPos, Integer maxResults, boolean fromTheEnd) {
        String ordering = fromTheEnd ? "desc" : "asc";
        Session session = SessionFactoryHelper.getOpenedSession();
        session.beginTransaction();
        String hql = "from TestHeadData as T where T.head like :expectedName order by T.id " + ordering;
        Query<TestHeadData> query = session.createQuery(hql, TestHeadData.class);
        query.setParameter("expectedName", headId);
        query.setFirstResult(startPos);
        query.setMaxResults(maxResults);
        List<TestHeadData> result = query.list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Get full list of a TestHead data, by specifying its head id.
     *
     * @return with the list
     */
    public int detectRows(String headId) {
        Session session = SessionFactoryHelper.getOpenedSession();
        session.beginTransaction();
        String hql = "from TestHeadData as T where T.head like :expectedName";
        Query<TestHeadData> query = session.createQuery(hql, TestHeadData.class);
        query.setParameter("expectedName", headId);
        List<TestHeadData> result = query.list();
        session.getTransaction().commit();
        session.close();
        return result.size();
    }


}
