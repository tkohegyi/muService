package com.epam.wilma.engine;

import com.epam.wilma.engine.bootstrap.WilmaBootstrap;
import com.epam.wilma.engine.bootstrap.helper.ApplicationContextFactory;

/**
 * Starts the Wilma application.
 * @author Tamas Kohegyi
 *
 */
public final class MuServiceApplication {

    public static String[] arguments; //NOSONAR

    private MuServiceApplication() {
    }

    /**
     * The proxy can be started by running this method.
     * @param args The program needs the path of wilma.conf.properties to run.
     */
    public static void main(final String[] args) {
        arguments = args; //NOSONAR
        new WilmaBootstrap(ApplicationContextFactory.getInstance()).bootstrap();
    }

}
