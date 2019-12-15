package com.epam.wilma.engine.bootstrap.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.wilma.common.helper.LogFilePathProvider;
import com.epam.wilma.common.helper.VersionTitleProvider;
import com.epam.wilma.engine.configuration.EngineConfigurationAccess;
import com.epam.wilma.engine.configuration.domain.PropertyDTO;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.Service.State;

/**
 * Listener for the muService, logs out the different state changes.
 * @author Tamas Kohegyi
 *
 */
@Component
public class WilmaServiceListener extends Service.Listener {

    private static final String FAILED_MESSAGE = "Error occurred in muService";
    private static final String TERMINATED_MESSAGE = "muService stopped.";
    private final Logger logger = LoggerFactory.getLogger(WilmaServiceListener.class);
    @Qualifier("startMessage")
    @Autowired
    private String startMessage;
    @Autowired
    private LogFilePathProvider logFilePath;
    @Autowired
    private VersionTitleProvider versionTitleProvider;
    @Autowired
    private EngineConfigurationAccess configurationAccess;

    @Override
    public void running() {
        logger.info(generateStartMessage());
    }

    @Override
    public void terminated(final State from) {
        logger.info(TERMINATED_MESSAGE);
    }

    @Override
    public void failed(final State from, final Throwable failure) {
        logger.error(FAILED_MESSAGE, failure);
    }

    private String generateStartMessage() {
        int wilmaPort = getWilmaPort();
        String appLogPath = logFilePath.getAppLogFilePath().toAbsolutePath().toString();
        return String.format(startMessage, versionTitleProvider.getVersionTitle(), wilmaPort, appLogPath);
    }

    private int getProxyPort() {
        PropertyDTO properties = configurationAccess.getProperties();
        return properties.getProxyPort();
    }

    private int getWilmaPort() {
        PropertyDTO properties = configurationAccess.getProperties();
        return properties.getWilmaPort();
    }

}
