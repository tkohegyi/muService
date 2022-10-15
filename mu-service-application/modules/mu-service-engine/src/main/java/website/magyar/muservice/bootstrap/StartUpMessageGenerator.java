package website.magyar.muservice.bootstrap;

import website.magyar.muservice.configuration.VersionTitleProvider;
import website.magyar.muservice.initialize.EngineConfigurationAccess;
import website.magyar.muservice.initialize.PropertyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Logs the startup message.
 */
@Component
public class StartUpMessageGenerator {

    private final Logger logger = LoggerFactory.getLogger(StartUpMessageGenerator.class);

    @Value("#{startMessage}")
    private String startMessage;
    @Autowired
    private EngineConfigurationAccess configurationAccess;
    @Autowired
    private VersionTitleProvider versionTitleProvider;

    /**
     * Logs the startup message.
     */
    public void logStartUpMessage() {
        PropertyDto properties = configurationAccess.getProperties();
        Integer port = properties.getPort();
        String startUpMessage = String.format(startMessage, versionTitleProvider.getVersionTitle(), port);
        logger.info(startUpMessage);

    }

}
