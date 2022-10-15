package website.magyar.muservice.engine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import website.magyar.muservice.bootstrap.Bootstrap;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Test class of {@link Application} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Application.class)
public class ApplicationTest {

    private final String[] arguments = {"blah", "blah"};

    @Mock
    private Bootstrap bootstrap;

    @InjectMocks
    private Application underTest;

    @Before
    public void setUp() throws Exception {
        underTest = PowerMockito.mock(Application.class);
        whenNew(Application.class).withNoArguments().thenReturn(underTest);
        bootstrap = spy(new Bootstrap());
        whenNew(Bootstrap.class).withNoArguments().thenReturn(bootstrap);
        doNothing().when(bootstrap).bootstrap(arguments);
    }

    @Test
    public void testMain() {
        Application.main(arguments);
        assertArrayEquals(arguments, Whitebox.getInternalState(Application.class, "arguments"));
        verify(bootstrap).bootstrap(arguments);
    }
}