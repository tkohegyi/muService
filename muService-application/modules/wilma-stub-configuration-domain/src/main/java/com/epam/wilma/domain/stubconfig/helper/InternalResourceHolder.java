package com.epam.wilma.domain.stubconfig.helper;

import com.epam.wilma.domain.stubconfig.interceptor.ExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Holds internal services.
 * @author Tamas_Kohegyi
 *
 */
@Component
public class InternalResourceHolder {
    @Autowired(required = false)
    private List<ExternalService> externalServices;

    public List<ExternalService> getExternalServices() {
        return externalServices;
    }

}
