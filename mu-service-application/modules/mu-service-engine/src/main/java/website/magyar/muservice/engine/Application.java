package website.magyar.muservice.engine;

import website.magyar.muservice.bootstrap.Bootstrap;

/**
 * Starts the application.
 */
public final class Application {

    public static String[] arguments; //NOSONAR

    private Application() {
    }

    /**
     * The app main entry point.
     *
     * @param args The program needs the path of conf.properties to run.
     */
    public static void main(final String[] args) {
        arguments = args; //NOSONAR
        new Bootstrap().bootstrap(args);
    }
}
