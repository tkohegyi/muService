package website.magyar.muservice.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import website.magyar.muservice.database.business.BusinessWithTestHead;
import website.magyar.muservice.database.business.BusinessWithTestHeadData;
import website.magyar.muservice.database.tables.TestHead;
import website.magyar.muservice.database.tables.TestHeadData;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Placeholder for doing regular tasks.
 *
 * @author Tamas_Kohegyi
 */
@Component
public class RegularTask implements Runnable {
    private static final Semaphore semaphore = new Semaphore(1);

    private static final String ERROR_MESSAGE = "Error occurred while doing regular tasks.";

    private final Logger logger = LoggerFactory.getLogger(RegularTask.class);

    @Autowired
    BusinessWithTestHead businessWithTestHead;
    @Autowired
    BusinessWithTestHeadData businessWithTestHeadData;

    @Override
    public void run() {
        boolean acquired = semaphore.tryAcquire();
        if (!acquired) {
            logger.warn("Semaphore prevents running the Task.");
            return;
        }
        try {
            logger.info("Regular task is running...");
            cleanUpErrorsInDatabase();
            //...
            logger.info("Regular task finished successfully.");
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE, e);
        } finally {
            semaphore.release();
        }
    }

    private void cleanUpErrorsInDatabase() {
        List<TestHead> testHeadList = businessWithTestHead.getList();
        for (TestHead testHead:testHeadList) {
            if ("ds18b20".equals(testHead.getType())) {
                removeIrregularValues(testHead.getHeadId());
            }
        }
    }

    private void removeIrregularValues(String headId) {
        int rows = businessWithTestHeadData.detectRows(headId);
        List<TestHeadData> testHeadDataList = businessWithTestHeadData.getList(headId, 0, rows, false);
        for (TestHeadData testHeadData: testHeadDataList) {
            String information = testHeadData.getInformation();
            try {
                double d = Double.parseDouble(information);
                if (d > 60.0 || d < -60.0) {
                    logger.info("Row {} is candidate for deletion because of unacceptable information: {}.", testHeadData.getId(), d);
                }
            } catch (NumberFormatException e) {
                logger.info("Row {} is candidate for deletion because of incorrect information data.", testHeadData.getId());
            }
        }
    }

}

