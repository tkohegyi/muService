package com.epam.wilma.core.toggle.mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.epam.wilma.core.processor.entity.LocalhostRequestProcessor;

/**
 * Handles enabling or disabling the processor that handles localhost usage blocking.
 * @author Adam_Csaba_Kiraly
 *
 */
@Component
public class LocalhostRequestProcessorToggle implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private LocalhostRequestProcessor localhostRequestProcessor;

    /**
     * Disables the LocalhostRequestPreprocessor.
     */
    public void switchOff() {
        localhostRequestProcessor.setEnabled(false);
    }

    public boolean isOn() {
        return localhostRequestProcessor.isEnabled();
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        switchOff();
    }
}
