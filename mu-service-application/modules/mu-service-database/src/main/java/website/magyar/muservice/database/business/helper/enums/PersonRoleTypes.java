package website.magyar.muservice.database.business.helper.enums;

import website.magyar.muservice.database.exception.DatabaseHandlingException;

/**
 * Enum to represent status of a Person record.
 */
public enum PersonRoleTypes {
    ADMINISTRATOR("Administrator", 1);

    private final String translatedText;
    private final Integer typeValue;

    PersonRoleTypes(String translatedText, Integer typeValue) {
        this.translatedText = translatedText;
        this.typeValue = typeValue;
    }

    /**
     * Get it as a string.
     *
     * @param typeValue is the Id based identified of the enum.
     * @return with the string representation of the enum identified by the typeValue
     * @throws DatabaseHandlingException in case the give Id is invalid
     */
    public static String getTranslatedString(Integer typeValue) {
        for (PersonRoleTypes socialStatusTypes : PersonRoleTypes.values()) {
            if (socialStatusTypes.typeValue.equals(typeValue)) {
                return socialStatusTypes.getTranslatedText();
            }
        }
        throw new DatabaseHandlingException("Incorrect usage of data -> PersonRoleTypes type:" + typeValue.toString() + " was requested.");
    }

    /**
     * Gets the enum specified by its Id.
     *
     * @param id is the id that identified the Social ytpe enum
     * @return with the enum identified by the specified Id
     * @throws DatabaseHandlingException in case the specified Id is invalid
     */
    public static PersonRoleTypes getTypeFromId(Integer id) {
        for (PersonRoleTypes socialStatusTypes : PersonRoleTypes.values()) {
            if (socialStatusTypes.typeValue.equals(id)) {
                return socialStatusTypes;
            }
        }
        throw new DatabaseHandlingException("Incorrect usage of data -> PersonRoleTypes id:" + id.toString() + " was requested.");
    }

    // helper functions

    public Integer getTypeValue() {
        return typeValue;
    }

    public String getTranslatedText() {
        return translatedText;
    }

}
