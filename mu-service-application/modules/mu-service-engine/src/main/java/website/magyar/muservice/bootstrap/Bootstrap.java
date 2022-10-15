package website.magyar.muservice.bootstrap;

import website.magyar.muservice.bootstrap.helper.SystemExceptionSelector;
import website.magyar.muservice.database.SessionFactoryHelper;
import website.magyar.muservice.exception.InvalidPropertyException;
import website.magyar.muservice.exception.SystemException;
import website.magyar.muservice.properties.PropertyLoader;
import website.magyar.muservice.properties.helper.PropertiesNotAvailableException;
import website.magyar.muservice.web.WebAppServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;

import java.util.Properties;

/**
 * Bootstrap class that starts the application engine.
 */
public class Bootstrap {

    private final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    private final SystemExceptionSelector systemExceptionSelector = new SystemExceptionSelector();
    private final PropertyLoader propertyLoader = new PropertyLoader();

    WebAppServer createWebAppServer() {
        return new WebAppServer();
    }

    SessionFactoryHelper createSessionFactoryHelper() {
        return new SessionFactoryHelper();
    }

    /**
     * Starts the application.
     *
     * @param args command line arguments
     */
    public void bootstrap(final String[] args) {
        WebAppServer webAppServer = createWebAppServer();
        SessionFactoryHelper sessionFactoryHelper = createSessionFactoryHelper();
        try {
            //prepare hibernate
            String hibernateUsername = getStringInfo(args, "hibernate.connection.username");
            String hibernatePassword = getStringInfo(args, "hibernate.connection.password");
            String hibernateUrl = getStringInfo(args, "hibernate.connection.url");
            sessionFactoryHelper.initiateHibernateSessionFactory(hibernateUrl, hibernateUsername, hibernatePassword);
            //prepare web server
            String serverKeyStoreFile = getStringInfo(args, "keyStoreFile");
            String serverKeyStorePassword = getStringInfo(args, "keyStorePassword");
            Integer port = getPort(args);
            Boolean isHttpsInUse = getIsHttpsInUse(args);
            webAppServer.createServer(port, isHttpsInUse, serverKeyStoreFile, serverKeyStorePassword);
            webAppServer.start();
        } catch (BeanCreationException e) {
            handleException(webAppServer, e);
        } catch (SystemException e) {
            logError(e);
            webAppServer.stop();
            SessionFactoryHelper.shutdownHibernateSessionFactory();
        }
    }

    private String getStringInfo(String[] args, String propertyName) {
        String info;
        checkPropertyFileArgument(args);
        Properties properties = propertyLoader.loadProperties(args[0]);
        info = properties.getProperty(propertyName);
        return info;
    }

    private Integer getPort(final String[] args) {
        int port;
        checkPropertyFileArgument(args);
        Properties properties = propertyLoader.loadProperties(args[0]);
        try {
            port = Integer.parseInt(properties.getProperty("webapp.port"));
        } catch (NumberFormatException e) {
            throw new InvalidPropertyException("Invalid port value!");
        }
        return port;
    }

    private Boolean getIsHttpsInUse(final String[] args) {
        checkPropertyFileArgument(args);
        Properties properties = propertyLoader.loadProperties(args[0]);
        boolean isHttpsInUse;
        isHttpsInUse = Boolean.parseBoolean(properties.getProperty("isHttpsInUse"));
        return isHttpsInUse;
    }

    private void checkPropertyFileArgument(final String[] args) {
        if (args.length == 0) {
            throw new PropertiesNotAvailableException("Configuration file was not specified as input argument!");
        } else if (!args[0].endsWith(".properties")) {
            throw new PropertiesNotAvailableException("Configuration file must be a properties file!");
        }
    }

    private void handleException(final WebAppServer webAppServer, final BeanCreationException e) {
        SystemException ex = systemExceptionSelector.getSystemException(e);
        if (ex != null) {
            logError(ex);
            webAppServer.stop();
            SessionFactoryHelper.shutdownHibernateSessionFactory();
        } else {
            throw e;
        }
    }

    private void logError(final Exception e) {
        String message = "Application cannot be started: " + e.getLocalizedMessage();
        logger.error(message);
    }
}
