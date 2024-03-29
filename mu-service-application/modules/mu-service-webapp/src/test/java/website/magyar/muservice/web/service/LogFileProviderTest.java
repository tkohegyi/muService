package website.magyar.muservice.web.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import website.magyar.muservice.web.provider.LogFileProvider;
import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Unit test for {@link LogFileProvider}.
 */
public class LogFileProviderTest {

    private static final String LOG_EXTENSION = "txt";
    private static final String LOG_PATH = "log";
    private static final String FILE_NOT_FOUND_MESSAGE = "File not found!";
    private static final String ERROR_MESSAGE = "Error occurred while reading file!";

    @Mock
    private FileUtils fileUtils;

    @InjectMocks
    private LogFileProvider underTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLogFileNames() {
        Collection<File> files = getFileCollection();
        Collection<String> expectedNames = new ArrayList<>();
        expectedNames.add("a");
        expectedNames.add("b");
        expectedNames.add("c");
        given(fileUtils.getFilesWithExtension(LOG_PATH, LOG_EXTENSION)).willReturn(files);
        //WHEN
        Collection<String> result = underTest.getLogFileNames();
        //THEN
        assertEquals(expectedNames, result);
    }

    @Test
    public void testGetLogContentWhenFileDoesNotExistShouldReturnFileNotFoundMessage() {
        //GIVEN
        Collection<File> files = getFileCollection();
        String fileName = "non-existent-file";
        given(fileUtils.getFilesWithExtension(LOG_PATH, LOG_EXTENSION)).willReturn(files);
        //WHEN
        String result = underTest.getLogContent(fileName);
        //THEN
        assertEquals(FILE_NOT_FOUND_MESSAGE, result);
    }

    @Test
    public void testGetLogContent() throws IOException {
        //GIVEN
        String expectedFileContent = "content";
        Collection<File> files = new ArrayList<>();
        String fileName = "a";
        File file = new File("a");
        files.add(file);
        given(fileUtils.getFilesWithExtension(LOG_PATH, LOG_EXTENSION)).willReturn(files);
        given(fileUtils.readFileToString(file)).willReturn(expectedFileContent);
        //WHEN
        String result = underTest.getLogContent(fileName);
        //THEN
        assertEquals(expectedFileContent, result);
    }

    @Test
    public void testGetLogContentWhenErrorOccursShouldReturnErrorMessage() throws IOException {
        //GIVEN
        Collection<File> files = new ArrayList<>();
        String fileName = "a";
        File file = new File("a");
        files.add(file);
        given(fileUtils.getFilesWithExtension(LOG_PATH, LOG_EXTENSION)).willReturn(files);
        given(fileUtils.readFileToString(file)).willThrow(new IOException());
        //WHEN
        String result = underTest.getLogContent(fileName);
        //THEN
        assertEquals(ERROR_MESSAGE, result);
    }

    private Collection<File> getFileCollection() {
        Collection<File> files = new ArrayList<>();
        files.add(new File("a"));
        files.add(new File("b"));
        files.add(new File("c"));
        return files;
    }
}
