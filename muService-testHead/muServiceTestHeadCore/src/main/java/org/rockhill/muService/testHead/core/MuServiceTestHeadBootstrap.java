package org.rockhill.muService.testHead.core;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads the properties and handles SystemExceptions.
 * @author Tamas_Kohegyi
 *
 */
public class MuServiceTestHeadBootstrap {

    private Logger logger = LoggerFactory.getLogger(MuServiceTestHeadBootstrap.class);

    /**
     * Bootstraps the application by loading the properties and starting the server.
     * @param args the program arguments
     * @param properties where the properties should be loaded
     * @param jettyServer that should be configured and started
     */
    public void bootstrap(final String[] args, final Properties properties, final JettyServer jettyServer) {
        String versionTitle = getClass().getPackage().getImplementationTitle();
        if (versionTitle == null) {
            versionTitle = "UNKNOWN";
        }

        logger.info(versionTitle + "\nCopyright since 2019 - GNU GPL-V3.0 License");



        if (args.length == 1) {
            try {
                String fileName = args[0];
                if (args[0].endsWith(".properties")) {
                    InputStream inputStream = new FileInputStream(fileName);
                    properties.load(inputStream);
                    Integer httpPort = Integer.parseInt(properties.getProperty("server.port.http"));
                    Integer httpsPort = Integer.parseInt(properties.getProperty("server.port.https"));
                    logger.info("Http Test Head is running on port: " + httpPort);
                    logger.info("HttpS Test Head is running on port: " + httpsPort);
                    jettyServer.configureAndStart(new Server(), httpPort, httpsPort);
                } else {
                    logger.error("Specified property file's extension is not \"properties\"!");
                }
            } catch (FileNotFoundException e) {
                logger.error("Specified property file not found!", e);
            } catch (NumberFormatException e) {
                logger.error("One of server.port.http and server.port.https properties is not a valid port number.", e);
            } catch (IOException e) {
                logger.error("Property file cannot be read.");
            } catch (SystemException e) {
                e.logStackTrace(logger);
            }
        } else {
            //no property file, go with defaults
            Integer httpPort = Integer.parseInt("9998");
            Integer httpsPort = Integer.parseInt("9997");
            logger.info("Http Test Head is running on port: " + httpPort);
            logger.info("HttpS Test Head is running on port: " + httpsPort);
            jettyServer.configureAndStart(new Server(), httpPort, httpsPort);
        }
    }

}
