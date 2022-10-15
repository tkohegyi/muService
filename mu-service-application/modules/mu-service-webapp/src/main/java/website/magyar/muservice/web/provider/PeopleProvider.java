package website.magyar.muservice.web.provider;

import website.magyar.muservice.database.business.BusinessWithNextGeneralKey;
import website.magyar.muservice.database.business.BusinessWithPerson;
import website.magyar.muservice.database.business.BusinessWithSocial;
import website.magyar.muservice.database.business.helper.DataChecker;
import website.magyar.muservice.database.exception.DatabaseHandlingException;
import website.magyar.muservice.database.tables.Person;
import website.magyar.muservice.web.json.CurrentUserInformationJson;
import website.magyar.muservice.web.json.DeleteEntityJson;
import website.magyar.muservice.web.json.PersonInformationJson;
import website.magyar.muservice.web.provider.helper.ProviderBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class to provide information about users.
 */
@Component
public class PeopleProvider extends ProviderBase {

    private final Logger logger = LoggerFactory.getLogger(PeopleProvider.class);

    @Autowired
    private BusinessWithPerson businessWithPerson;
    @Autowired
    private BusinessWithNextGeneralKey businessWithNextGeneralKey;
    @Autowired
    private BusinessWithSocial businessWithSocial;
    @Autowired
    private DataChecker dataChecker;

    /**
     * Get simple full list of people.
     *
     * @return with the list as object
     */
    public Object getPersonListAsObject() {
        var people = businessWithPerson.getPersonList();
        return people;
    }

    /**
     * Get a specific person.
     *
     * @param id of the person
     * @return with the person as object
     */
    public Object getPersonAsObject(final Long id) {
        var person = businessWithPerson.getPersonById(id);
        return person;
    }

    /**
     * Update a Person information.
     *
     * @param personInformationJson      is the updated Person information to be saved
     * @param currentUserInformationJson is the actual user
     * @return with the id of the updated Person
     */
    public Long updatePerson(PersonInformationJson personInformationJson, CurrentUserInformationJson currentUserInformationJson) {
        var id = Long.parseLong(personInformationJson.id);
        var person = businessWithPerson.getPersonById(id);
        if (person == null) {
            //new Person
            id = createNewPerson(personInformationJson, currentUserInformationJson.userName);
            return id;
        }
        //prepare new name and validate it
        handleNameUpdate(personInformationJson, person, currentUserInformationJson.userName);
        //other string fields
        handleAllOtherStringFields(personInformationJson, person, currentUserInformationJson.userName);
        //other boolean fields - nothing to do

        //we do not set the string languageCode
        id = businessWithPerson.updatePerson(person);
        return id;
    }

    private void handleAllOtherStringFields(PersonInformationJson personInformationJson, Person person, String userName) {
        String newValue;
        //mobile
        newValue = handleSimpleStringFieldUpdate(personInformationJson.mobile.trim(), userName);
        person.setMobile(newValue);
        //email
        newValue = handleSimpleStringFieldUpdate(personInformationJson.email.trim(), userName);
        person.setEmail(newValue);
        //comment
        newValue = handleSimpleStringFieldUpdate(personInformationJson.comment.trim(), userName);
        person.setComment(newValue);
    }

    private String handleSimpleStringFieldUpdate(String newValue, String userName) {
        dataChecker.checkDangerousValue(newValue, userName);
        return newValue;
    }

    private void handleNameUpdate(PersonInformationJson personInformationJson, Person person, String userName) {
        var newValue = personInformationJson.name.trim();
        dataChecker.checkDangerousValue(newValue, userName);
        //name length must be > 0, and shall not fit to other existing names
        if (newValue.length() == 0) {
            logger.info("User: {} tried to create/update Person with empty name.", userName);
            throw new DatabaseHandlingException("Field content is not allowed.");
        }
        person.setName(newValue);
    }

    private Long createNewPerson(PersonInformationJson personInformationJson, String userName) {
        Long id;
        var person = new Person();
        person.setId(businessWithNextGeneralKey.getNextGeneralId());
        person.setName(personInformationJson.name);
        person.setComment(personInformationJson.comment);
        person.setEmail(personInformationJson.email);
        person.setMobile(personInformationJson.mobile);
        id = businessWithPerson.newPerson(person);
        return id;
    }

    /**
     * Delete a specific Person.
     *
     * @param personJson identifies the Person
     * @return with the id of the deleted Person
     */
    public Long deletePerson(DeleteEntityJson personJson) {
        var personId = Long.parseLong(personJson.entityId);
        var person = businessWithPerson.getPersonById(personId);
        //collect related social - this can be null, if there was no social for the person + we need to clear the social - person connection only
        var socialList = businessWithSocial.getSocialsOfPerson(person);
        Long result;
        result = businessWithPerson.deletePerson(person, socialList);
        return result;
    }

}
