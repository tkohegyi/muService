package com.epam.wilma.stubconfig.json.parser;

import com.epam.wilma.domain.stubconfig.exception.DescriptorValidationFailedException;
import com.epam.wilma.domain.stubconfig.interceptor.InterceptorDescriptor;
import com.epam.wilma.stubconfig.json.parser.helper.ObjectParser;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Builds a new {@link InterceptorDescriptor} from a JSON object.
 *
 * @author Tamas_Kohegyi
 */
@Component
public class ServiceDescriptorJsonParser implements ObjectParser<InterceptorDescriptor> {

    @Override
    public InterceptorDescriptor parseObject(final JSONObject interceptorObject, final JSONObject root) {
        throw new DescriptorValidationFailedException("Validation of Service description failed - interceptors cannot be used.");
    }

}
