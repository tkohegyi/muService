package com.epam.wilma.stubconfig.initializer.interceptor;

import java.util.List;

import com.epam.wilma.domain.stubconfig.interceptor.ExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.wilma.domain.stubconfig.StubResourcePathProvider;
import com.epam.wilma.domain.stubconfig.TemporaryConfigResourceHolder;
import com.epam.wilma.stubconfig.initializer.CommonClassInitializer;

/**
 * Loads an external service class referenced in the configuration.
 * @author Tunde_Kovacs, TAmas_Kohegyi
 *
 */
@Component
public class ExternalServiceInitializer extends CommonClassInitializer<ExternalService> {

    @Autowired
    private StubResourcePathProvider stubResourcePathProvider;
    @Autowired
    private TemporaryConfigResourceHolder stubResourceHolder;

    @Override
    protected String getPathOfExternalClasses() {
        return stubResourcePathProvider.getInterceptorPathAsString();
    }

    @Override
    protected List<ExternalService> getExternalClassObjects() {
        return stubResourceHolder.getExternalServices();
    }

    @Override
    protected void addExternalClassObject(ExternalService externalClassObject) {
        stubResourceHolder.addRequestInterceptor(externalClassObject);
    }

    @Override
    protected Class<ExternalService> getExternalClassType() {
        return ExternalService.class;
    }

}
