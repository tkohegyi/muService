package website.magyar.muservice.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import website.magyar.muservice.configuration.CoreConfigurationAccess;
import website.magyar.muservice.scheduler.helper.CronTriggerFactory;

import java.util.Date;

/**
 * Schedules and runs the background tasks.
 *
 * @author Tamas Kohegyi
 */
@Component
public class RegularTaskScheduler {

    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private CronTriggerFactory cronTriggerFactory;

    @Autowired
    private CoreConfigurationAccess configurationAccess;

    @Autowired
    private RegularTask regularTask;

    public RegularTaskScheduler() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(3);
        taskScheduler.initialize();
    }

    /**
     * Schedules the task.
     */
    public void startTaskScheduling() {
        String taskCronExpression = configurationAccess.getProperties().getCronTask();
        CronTrigger taskCronTrigger = cronTriggerFactory.create(taskCronExpression);
        taskScheduler.schedule(regularTask, taskCronTrigger);
    }

    /**
     * Schedules the task to run now.
     */
    public void runTaskOnDemand() {
        taskScheduler.schedule(regularTask, new Date());
    }

}
