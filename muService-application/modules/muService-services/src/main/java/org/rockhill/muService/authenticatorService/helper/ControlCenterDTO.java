package org.rockhill.muService.authenticatorService.helper;

/**
 * Data transfer object for Control Centers in Server Service.
 * @author Tamas_Kohegyi
 *
 */
public class ControlCenterDTO {

    private String actualToken;

    /**
     * Constructs the DTO.
     */
    public ControlCenterDTO() {
        super();
        this.actualToken = "";
    }

    public String getActualToken() {
        return actualToken;
    }

    public void setActualToken(String actualToken) {
        this.actualToken = actualToken;
    }

}
