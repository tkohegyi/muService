package website.magyar.muservice.database.business;

import com.sun.istack.NotNull; //NOSONAR
import org.hibernate.Session;
import org.hibernate.query.Query;
import website.magyar.muservice.database.SessionFactoryHelper;
import website.magyar.muservice.database.business.helper.BusinessBase;
import website.magyar.muservice.database.tables.Person;
import website.magyar.muservice.database.tables.Social;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Business class to handle Person Database table.
 * This table holds information about registered adorators.
 */
@Component
public class BusinessWithPerson extends BusinessBase {
    private final Logger logger = LoggerFactory.getLogger(BusinessWithPerson.class);

    /**
     * Get full list of adorators.
     *
     * @return with the list of people.
     */
    public List<Person> getPersonList() {
        List<Person> result;
        Session session = SessionFactoryHelper.getOpenedSession();
        session.beginTransaction();
        result = session.createQuery("from Person", Person.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Get (search for) a specific person data by specifying its name.
     *
     * @param name is the name of the searched person
     * @return with the Person data, if found, or null, if not found
     */
    public Person getPersonByName(@NotNull final String name) {
        Session session = SessionFactoryHelper.getOpenedSession();
        session.beginTransaction();
        String hql = "from Person as P where P.name = :expectedName";
        Query<Person> query = session.createQuery(hql, Person.class);
        query.setParameter("expectedName", name);
        List<Person> result = query.list();
        session.getTransaction().commit();
        session.close();
        return (Person) returnWithFirstItem(result);
    }

    /**
     * Create a new record for a Person.
     *
     * @param newPerson  is the Person data to be saved
     * @return with the id of the newly created Person record
     */
    public Long newPerson(@NotNull Person newPerson) {
        Long id;
        Session session = SessionFactoryHelper.getOpenedSession();
        try {
            session.beginTransaction();
            session.save(newPerson);
            session.getTransaction().commit();
            session.close();
            id = newPerson.getId();
            logger.info("Person created successfully: {} with name: {}", id, newPerson.getName());
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            throw e;
        }
        return id;
    }

    /**
     * Get (search for) a Person object by specifying its Id.
     *
     * @param id is the unique id of the Person
     * @return with the Person object found, or null if not found
     */
    public Person getPersonById(@NotNull final Long id) {
        Session session = SessionFactoryHelper.getOpenedSession();
        session.beginTransaction();
        String hql = "from Person as P where P.id = :" + EXPECTED_PARAMETER;
        Query<Person> query = session.createQuery(hql, Person.class);
        query.setParameter(EXPECTED_PARAMETER, id);
        List<Person> result = query.list();
        session.getTransaction().commit();
        session.close();
        return (Person) returnWithFirstItem(result);
    }

    /**
     * Get (search for) a Person object by specifying its e-mail address.
     *
     * @param email is the e-mail of the Person
     * @return with the Person object found, or null if not found
     */
    public Person getPersonByEmail(@NotNull final String email) {
        Session session = SessionFactoryHelper.getOpenedSession();
        session.beginTransaction();
        String hql = "from Person as P where P.email like :email";
        Query<Person> query = session.createQuery(hql, Person.class);
        query.setParameter("email", email);
        List<Person> result = query.list();
        session.getTransaction().commit();
        session.close();
        return (Person) returnWithFirstItem(result);
    }

    /**
     * Update a specific Person object.
     *
     * @param person               is the Person to be updated
     * @return with the Id of the updated Person object
     */
    public Long updatePerson(@NotNull Person person) {
        Session session = SessionFactoryHelper.getOpenedSession();
        try {
            session.beginTransaction();
            session.update(person);
            session.getTransaction().commit();
            session.close();
            logger.info("Person updated successfully: {}", person.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            throw e;
        }
        return person.getId();
    }

    /**
     * Delete a specific Person object.
     *
     * @param person         id the Person object to be deleted
     * @param socialList     list of the connected Social login record - they will be deleted as well
     * @return with the id of the Person that was deleted
     */
    public Long deletePerson(@NotNull Person person, List<Social> socialList) {
        //the huge method of deleting an activity from DB.
        Session session = SessionFactoryHelper.getOpenedSession();
        try {
            session.beginTransaction();
            if (socialList != null) {
                for (Social social : socialList) {
                    session.update(social); //this is just update, the rest is delete
                }
            }
            session.delete(person);
            session.getTransaction().commit();
            session.close();
            logger.info("Person deleted successfully: {}", person.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            logger.info("Person delete failed: {}", person.getId());
            throw e;
        }
        return person.getId();
    }

}
