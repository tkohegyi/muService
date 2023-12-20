package website.magyar.muservice.scheduler.helper;

import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * Factory class for {@link CronTrigger}.
 * @author Tamas_Kohegyi
 */
@Component
public class CronTriggerFactory {

    /**
     * Creates a new {@link CronTrigger} instance.
     * @param expression a space-separated list of time fields, following cron expression conventions
     * @return a new {@link CronTrigger} instance
     */
    public CronTrigger create(final String expression) {
        return new CronTrigger(expression);
    }
}
