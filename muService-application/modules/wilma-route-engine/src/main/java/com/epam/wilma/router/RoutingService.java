package com.epam.wilma.router;

import com.epam.wilma.domain.stubconfig.StubDescriptor;
import com.epam.wilma.router.command.StubDescriptorModificationCommand;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains route logic of request messages.
 *
 * @author Tunde_Kovacs
 * @author Tamas_Bihari
 */
@Component
public class RoutingService {

    private final Object guard = new Object();
    private Map<String, StubDescriptor> stubDescriptors = new LinkedHashMap<>();


    public Map<String, StubDescriptor> getStubDescriptors() {
        return stubDescriptors;
    }

    /**
     * This method execute the given command. The given command is any operation which works with the stubDescriptors collection.
     *
     * @param command is the given operation
     * @throws ClassNotFoundException if problem happens
     */
    public void performModification(final StubDescriptorModificationCommand command) throws ClassNotFoundException {
        synchronized (guard) {
            stubDescriptors = command.modify(stubDescriptors);
        }
    }
}
