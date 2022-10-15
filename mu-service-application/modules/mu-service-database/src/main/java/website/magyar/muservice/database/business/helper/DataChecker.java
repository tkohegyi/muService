package website.magyar.muservice.database.business.helper;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import website.magyar.muservice.database.exception.DatabaseHandlingException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataChecker {

    private final Logger logger = LoggerFactory.getLogger(DataChecker.class);

    /**
     * Checks if the user provided a dangerous text (from HTML perspective) into the text field.
     * If yes, logs the event and prohibits to move forward with the update/save action.
     *
     * @param text     is the text to be checked
     * @param userName is the person name who initiated the change
     */
    public void checkDangerousValue(@NotNull final String text, @NotNull final String userName) {
        Pattern p = Pattern.compile("[\\\\#&<>]");
        Matcher m = p.matcher(text);
        if (m.find()) {
            logger.warn("User: {} tried to use dangerous string value: {}", userName, text);
            throw new DatabaseHandlingException("Field content is not allowed.");
        }
    }
}
