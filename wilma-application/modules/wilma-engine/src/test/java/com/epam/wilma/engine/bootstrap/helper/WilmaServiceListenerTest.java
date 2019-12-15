package com.epam.wilma.engine.bootstrap.helper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.wilma.common.helper.LogFilePathProvider;
import com.epam.wilma.common.helper.VersionTitleProvider;
import com.epam.wilma.domain.exception.SystemException;
import com.epam.wilma.engine.configuration.EngineConfigurationAccess;
import com.epam.wilma.engine.configuration.domain.PropertyDTO;
import com.google.common.util.concurrent.Service.State;

/**
 * Unit test for {@link WilmaServiceListenerTest}.
 * @author Adam_Csaba_Kiraly, Tamas Kohegyi
 */
public class WilmaServiceListenerTest {

    private static final String FAILED_MESSAGE = "Error occurred in muService";
    private static final String ERR_MSG = "System error";
    private static final String WILMA_START_MESSAGE = "startMessage";
    private static final String TERMINATED_MESSAGE = "muService stopped.";
    @Mock
    private Logger logger;
    @Mock
    private VersionTitleProvider versionTitleProvider;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private LogFilePathProvider logFilePath;
    @Mock
    private EngineConfigurationAccess configurationAccess;

    @InjectMocks
    private WilmaServiceListener underTest;

    @Mock
    private PropertyDTO properties;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Whitebox.setInternalState(underTest, "logger", logger);
        Whitebox.setInternalState(underTest, "startMessage", ERR_MSG);
        given(configurationAccess.getProperties()).willReturn(properties);
        given(logFilePath.getLogFilePath().toAbsolutePath().toString()).willReturn("a");
        given(properties.getProxyPort()).willReturn(1234);
    }

    @Test
    public void testRunningShouldLogStartMessage() {
        //GIVEN
        Whitebox.setInternalState(underTest, "startMessage", WILMA_START_MESSAGE);
        given(logFilePath.getLogFilePath().toAbsolutePath().toString()).willReturn(WILMA_START_MESSAGE);
        given(logFilePath.getAppLogFilePath().toAbsolutePath().toString()).willReturn(WILMA_START_MESSAGE);
        //WHEN
        underTest.running();
        //THEN
        verify(logger).info(WILMA_START_MESSAGE);
    }

    @Test
    public void testFailedShouldLogError() {
        //GIVEN
        SystemException e = new SystemException(ERR_MSG);
        State state = Mockito.mock(State.class);
        //WHEN
        underTest.failed(state, e);
        //THEN
        verify(logger).error(FAILED_MESSAGE, e);
    }

    @Test
    public void testFailedWhenSystemExceptionAndErrorCauseExistsShouldLogErrorAtErrorLevel() {
        //GIVEN
        State state = Mockito.mock(State.class);
        SystemException exception = new SystemException(ERR_MSG);
        Throwable cause = new Throwable();
        exception.initCause(cause);
        //WHEN
        underTest.failed(state, exception);
        //THEN
        verify(logger).error(FAILED_MESSAGE, exception);
    }

    @Test
    public void testTerminatedShouldLogMessage() {
        //GIVEN
        State state = Mockito.mock(State.class);
        //WHEN
        underTest.terminated(state);
        //THEN
        verify(logger).info(TERMINATED_MESSAGE);
    }

}
