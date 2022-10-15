package website.magyar.muservice.web.configuration;

import com.jcabi.manifests.Manifests;
import org.apache.commons.lang3.math.NumberUtils;
import website.magyar.muservice.configuration.ConfigurationAccessBase;
import website.magyar.muservice.configuration.PropertyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Configures the module with the necessary properties.
 */
@Component
public class WebAppConfigurationAccess implements ConfigurationAccessBase {
    private static final int SESSION_TIMEOUT_DEFAULT_VALUE = 500;

    private PropertyDto properties;

    @Autowired
    private PropertyHolder propertyHolder;

    /**
     * Returns a {@link PropertyDto} holding all module specific properties.
     *
     * @return the propertiesDTO object
     */
    public PropertyDto getProperties() {
        return properties;
    }

    @Override
    public void loadProperties() {
        String googleClientId = propertyHolder.get("google_client_id");
        String googleClientSecret = propertyHolder.get("google_client_secret");
        String googleRedirectUrl = propertyHolder.get("google_redirect_url");
        String baseUrl = propertyHolder.get("base_url");
        Integer sessionTimeout = NumberUtils.toInt(propertyHolder.get("sessionTimeout"), SESSION_TIMEOUT_DEFAULT_VALUE);
        String manifestVersion = Manifests.read("Application-Version");
        properties = new PropertyDto(googleClientId, googleClientSecret, googleRedirectUrl, baseUrl, sessionTimeout, manifestVersion);
    }
}
