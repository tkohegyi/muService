package com.epam.wilma.domain.stubconfig.dialog.response.template;

import java.util.Arrays;

/**
 * It describes a template file and how it should be handled when it is used as response.
 * @author Tunde_Kovacs
 *
 */
public class Template {

    private final String name;
    private byte[] resource;

    /**
     * Constructs a new instance of {@link Template}.
     * @param name the unique name of the template
     * @param resource in case of text, xml, html, it is embedded
     * (resource contains the value, need some protection, and only the text
     * should be implemented now), other case name of the file
     * in case of external, the resource is a class name,
     * that class is loaded and its String loadTemplateResource(name) method is called.
     */
    public Template(final String name, final byte[] resource) {
        super();
        this.name = name;
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public byte[] getResource() {
        return resource;
    }

    public void setResource(final byte[] resource) {
        this.resource = resource;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + Arrays.hashCode(resource);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Template)) {
            return false;
        }
        Template other = (Template) obj;
        return other.name.equals(name) && Arrays.equals(resource, other.resource);
    }

}
