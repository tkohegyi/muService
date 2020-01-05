package org.rockhill.muService.testHead.core;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.security.Password;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.rockhill.muService.testHead.core.handlers.GetInformationHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Create a new server that listens on the port given in the properties file, and adds the handler to the server.
 * Test run: java -Djavax.net.debug=ssl:handshake -jar muService-TestHead-Core-0.0.DEV.jar
 *
 * @author Tamas_Kohegyi
 *
 */
public class JettyServer {

    /**
     * Configures and starts a new Jetty server instance.
     * This server is configured to answer incoming request.
     * @param server the server that should be started
     * @param httpPort the http server port
     * @param httpsPort the https server port
     */
    public void configureAndStart(final Server server, final Integer httpPort, final Integer httpsPort) {
        try {
            ServerConnector httpConnector = createHttpConnector(server, httpPort);
            ServerConnector sslConnector = createSslConnector(server, httpsPort);
            server.setConnectors(new Connector[]{httpConnector, sslConnector});
            server.setHandler(new CompositeHandler(new GetInformationHandler()));
            startServer(server);
            server.join();
        } catch (Exception e) {
            throw new SystemException("server cannot be started", e);
        }
    }

    void startServer(final Server server) throws Exception {
        server.start();
    }

    private ServerConnector createHttpConnector(final Server server, final Integer port) {
        ServerConnector httpConnector = new ServerConnector(server);
        httpConnector.setPort(port);
        return httpConnector;
    }

    private ServerConnector createSslConnector(final Server server, final Integer port) {
        // HTTPS Configuration
        HttpConfiguration httpsConfig = new HttpConfiguration();
        httpsConfig.setSecureScheme("https");
        httpsConfig.setSecurePort(port);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        // SSL Context Factory for HTTPS and HTTP/2
        SslContextFactory sslContextFactory = new SslContextFactory();

        // this does NOT work sslContextFactory.setKeyStoreResource(newClassPathResource("certificate/muService.jks")); - so we need to load keyStore explicitly
        KeyStore keyStore = getKeyStore("certificate/muService.jks", "OBF:1uo71vv51qax1mik1y7z1mma1qc51vu51unr");
        sslContextFactory.setKeyStore(keyStore);

        sslContextFactory.setKeyStorePassword("OBF:1uo71vv51qax1mik1y7z1mma1qc51vu51unr");
        sslContextFactory.setKeyManagerPassword("OBF:1uo71vv51qax1mik1y7z1mma1qc51vu51unr");
        sslContextFactory.setUseCipherSuitesOrder(true);
        sslContextFactory.setRenegotiationAllowed(true);

        // SSL Connection Factory
        SslConnectionFactory ssl = new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString());

        // HTTP/2 Connector
        ServerConnector http2Connector = new ServerConnector(server, ssl, new HttpConnectionFactory(httpsConfig));
        http2Connector.setPort(port);
        return http2Connector;
    }

    /**
     * KeyStore
     *
     */
    private KeyStore getKeyStore(String keyStorePath, String obfPassword) {
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("PKCS12");
            FileInputStream is = new FileInputStream(keyStorePath);
            ks.load(is, Password.deobfuscate(obfPassword).toCharArray());
            is.close();
        } catch (KeyStoreException|CertificateException|NoSuchAlgorithmException|IOException e) {
            throw new SystemException("SSL Keystore is not available.", e);
        }
        return ks;
    }

}
