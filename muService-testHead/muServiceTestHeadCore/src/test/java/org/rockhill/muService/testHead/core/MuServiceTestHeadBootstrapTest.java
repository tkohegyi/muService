package org.rockhill.muService.testHead.core;
import org.eclipse.jetty.server.Server;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Properties;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link MuServiceTestHeadBootstrap}.
 * @author Tamas_Kohegyi
 *
 */
public class MuServiceTestHeadBootstrapTest {

    @InjectMocks
    private MuServiceTestHeadBootstrap underTest;

    @Mock
    private Properties properties;
    
    @Mock
    private JettyServer jettyServer;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public final void testBootstrapShouldConfigureAndStartJettyServer() {
        //GIVEN
        String[] args = {"muService.TestHead.properties"};
        given(properties.getProperty("server.port.http")).willReturn("9090");
        given(properties.getProperty("server.port.https")).willReturn("8443");
        //WHEN
        underTest.bootstrap(args, properties, jettyServer);
        //THEN
        verify(jettyServer).configureAndStart(any(Server.class), eq(9090), eq(8443));
    }

}
