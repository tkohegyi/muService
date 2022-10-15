package website.magyar.muservice.web.provider;

import website.magyar.muservice.database.business.BusinessWithSocial;
import website.magyar.muservice.database.business.helper.DataChecker;
import website.magyar.muservice.database.exception.DatabaseHandlingException;
import website.magyar.muservice.database.tables.Social;
import website.magyar.muservice.web.json.CurrentUserInformationJson;
import website.magyar.muservice.web.json.DeleteEntityJson;
import website.magyar.muservice.web.provider.helper.ProviderBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class to provide information about social users.
 */
@Component
public class SocialProvider extends ProviderBase {

    private final Logger logger = LoggerFactory.getLogger(SocialProvider.class);

    @Autowired
    private BusinessWithSocial businessWithSocial;
    @Autowired
    private DataChecker dataChecker;

    /**
     * Get simple full list of social logins.
     *
     * @return with the list as object
     */
    public Object getSocialListAsObject() {
        return businessWithSocial.getSocialList();
    }

    /**
     * Get a specific social login.
     *
     * @param id of the social login
     * @return with the social record as object
     */
    public Object getSocialAsObject(final Long id) {
        return businessWithSocial.getSocialById(id);
    }

    /**
     * Update a Social information.
     *
     * @param proposedSocial             is the updated Social information to be saved
     * @param currentUserInformationJson is the actual user
     * @return with the id of the updated Social
     */
    public Long updateSocial(Social proposedSocial, CurrentUserInformationJson currentUserInformationJson) {
        var refId = proposedSocial.getId();
        var targetSocial = businessWithSocial.getSocialById(refId);
        //personId
        var newLongValue = proposedSocial.getPersonId();
        var oldLongValue = targetSocial.getPersonId();
        if (isLongChanged(oldLongValue, newLongValue)) {
            targetSocial.setPersonId(newLongValue); //if we are here, it must have been changed
        }
        //socialStatus
        var newStatus = proposedSocial.getSocialStatus();
        var oldStatus = targetSocial.getSocialStatus();
        if (!oldStatus.equals(newStatus)) {
            targetSocial.setSocialStatus(newStatus);
        }
        try {
            handleSocialUpdatePreparationGooglePart(targetSocial, proposedSocial, currentUserInformationJson.userName);
        } catch (DatabaseHandlingException e) {
            return null;
        }
        //comment
        var newString = proposedSocial.getComment();
        var oldString = targetSocial.getComment();
        try {
            isSocialStringFieldChangeValid(oldString, newString, currentUserInformationJson.userName);
            targetSocial.setComment(newString);
        } catch (DatabaseHandlingException e) {
            return null;
        }
        return businessWithSocial.updateSocial(targetSocial);
    }

    private void handleSocialUpdatePreparationGooglePart(Social targetSocial, Social proposedSocial, String userName) {
        String newString;
        String oldString;
        //googleUserName
        newString = proposedSocial.getGoogleUserName();
        oldString = targetSocial.getGoogleUserName();
        isSocialStringFieldChangeValid(oldString, newString, userName);
        targetSocial.setGoogleUserName(newString);
        //googleUserPicture
        newString = proposedSocial.getGoogleUserPicture();
        oldString = targetSocial.getGoogleUserPicture();
        isSocialStringFieldChangeValid(oldString, newString, userName);
        targetSocial.setGoogleUserPicture(newString);
        //googleEmail
        newString = proposedSocial.getGoogleEmail();
        oldString = targetSocial.getGoogleEmail();
        isSocialStringFieldChangeValid(oldString, newString, userName);
        targetSocial.setGoogleEmail(newString);
        //googleUserId
        newString = proposedSocial.getGoogleUserId();
        oldString = targetSocial.getGoogleUserId();
        isSocialStringFieldChangeValid(oldString, newString, userName);
        targetSocial.setGoogleUserId(newString);
    }

    private void isSocialStringFieldChangeValid(String oldString, String newString, String userName) {
        if ((oldString == null) || (newString == null)) {
            var issue = "User: " + userName + " tried to create/update Social with null string.";
            logger.info(issue);
            throw new DatabaseHandlingException(issue);
        }
        dataChecker.checkDangerousValue(newString, userName);
    }

    /**
     * Delete a specific Social record.
     *
     * @param deleteEntityJson identifies the Social record
     * @return with the id of the deleted Social record
     */
    public Long deleteSocial(DeleteEntityJson deleteEntityJson) {
        var id = Long.parseLong(deleteEntityJson.entityId);
        var social = businessWithSocial.getSocialById(id);
        //collect related audit records
        Long result;
        result = businessWithSocial.deleteSocial(social);
        return result;
    }
}
