package com.epam.wilma.core.configuration.domain;

/**
 * Holds module specific properties.
 * @author Tunde_Kovacs
 * @author Tamas_Kohegyi
 *
 */
public final class PropertyDto {

    private String interceptorMode;

    public String getInterceptorMode() {
        return interceptorMode;
    }

    /**
     * Builder for {@link PropertyDto} object.
     * @author Adam_Csaba_Kiraly
     *
     */
    public static class Builder {
        private String interceptorMode;

        /**
         * Sets the interceptorMode value.
         * @param interceptorMode enable/disable MVT - Interceptor.
         * @return the {@link Builder} for chaining
         */
        public Builder interceptorMode(final String interceptorMode) {
            this.interceptorMode = interceptorMode;
            return this;
        }

        /**
         * Constructs a new property holding object.
         * @return the new {@link PropertyDto} instance.
         */
        public PropertyDto build() {
            PropertyDto propertyDto = new PropertyDto();
            setFields(propertyDto);
            return propertyDto;
        }

        private void setFields(final PropertyDto propertyDto) {
            propertyDto.interceptorMode = interceptorMode;
        }

    }

}
