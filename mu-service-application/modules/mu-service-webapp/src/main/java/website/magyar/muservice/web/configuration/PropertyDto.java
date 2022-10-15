package website.magyar.muservice.web.configuration;

/**
 * Holds module specific properties.
 */
public class PropertyDto {

    private final String googleClientId;
    private final String googleClientSecret;
    private final String googleRedirectUrl;
    private final String baseUrl;
    private final Integer sessionTimeout;
    private final String manifestVersion;

    /**
     * Constructs a new property holding object with the given fields.
     */
    public PropertyDto(final String googleClientId, final String googleClientSecret, final String googleRedirectUrl, //NOSONAR
                       final String baseUrl, final Integer sessionTimeout, final String manifestVersion) {
        super();
        this.googleClientId = googleClientId;
        this.googleClientSecret = googleClientSecret;
        this.googleRedirectUrl = googleRedirectUrl;
        this.baseUrl = baseUrl;
        this.sessionTimeout = sessionTimeout;
        this.manifestVersion = manifestVersion;
    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }

    public String getGoogleRedirectUrl() {
        return googleRedirectUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public String getManifestVersion() {
        return manifestVersion;
    }
}
