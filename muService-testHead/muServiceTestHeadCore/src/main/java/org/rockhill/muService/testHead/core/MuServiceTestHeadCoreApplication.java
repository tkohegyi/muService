package org.rockhill.muService.testHead.core;

import java.util.Properties;

/**
 * Starts the core server. The server can listen on a configurable port and answer to certain requests.
 * @author Tamas Kohegyi
 *
 */
public final class MuServiceTestHeadCoreApplication {

    private MuServiceTestHeadCoreApplication() {
    }

    /**
     * Starts the core server.
     * @param args the program arguments. The properties file location is required.
     */
    public static void main(final String[] args) {
        new MuServiceTestHeadBootstrap().bootstrap(args, new Properties(), new JettyServer());
    }
}
