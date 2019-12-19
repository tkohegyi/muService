package org.rockhill.muService.authenticatorService.helper;

/**
 * Data transfer object for Test Heads in Authenticator Service.
 * @author Tamas_Kohegyi
 *
 */
public class TestHeadDTO {

    private String actualToken;

    private Long validity;

    /**
     * Constructs the DTO.
     */
    public TestHeadDTO() {
        super();
        this.actualToken = "";
        this.validity = Long.valueOf(0l);
    }

    public String getActualToken() {
        return actualToken;
    }

    public void setActualToken(String actualToken) {
        this.actualToken = actualToken;
    }

    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

}
