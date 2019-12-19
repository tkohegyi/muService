package com.epam.wilma.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.wilma.common.configuration.ConfigurationAccessBase;
import com.epam.wilma.core.configuration.domain.PropertyDto;
import com.epam.wilma.properties.PropertyHolder;

/**
 * Provides configuration access for the module.
 * @author Tunde_Kovacs
 *
 */
@Component
public class CoreConfigurationAccess implements ConfigurationAccessBase {

    private PropertyDto properties;

    @Autowired
    private PropertyHolder propertyHolder;

    @Override
    public void loadProperties() {
        String interceptorMode = propertyHolder.get("interceptor");
        properties = new PropertyDto.Builder().interceptorMode(interceptorMode).build();
    }

    /**
     * Returns a {@link PropertyDto} holding all module specific properties.
     * @return the propertiesDTO object
     */
    public PropertyDto getProperties() {
        return properties;
    }

}
