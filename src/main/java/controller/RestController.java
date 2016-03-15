package controller;

import model.PersonEntity;
import model.RoleEntity;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;


@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class RestController {
    @PersistenceContext(unitName = "recruitPU")
    public  EntityManager em;
    private PersonEntity personEntity;
    private RoleEntity roleEntity;
    private Logger logger = Logger.getLogger(getClass().getName());

    private String NAME_REGEX = "^[a-zA-Z]+$";
    private String USER_REGEX = "^[a-zA-Z0-9]+$";
    private String SSN_REGEX = "^[0-9]+$";
    private String PW_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";



    /**
     * Checks that the person trying to login are using a valid combination of
     * username and password. Use this method for RestServices
     *
     * @param username
     * @param password
     * @return true if login is successful, false otherwise.
     */
    public boolean login(String username, String password) {
        if (validateLoginParameters(username, password)) {
            TypedQuery<PersonEntity> user;
            try {
                user = em.createNamedQuery(
                        "PersonEntity.findByUsername", PersonEntity.class)
                        .setParameter("username", username);
            } catch (Exception e) {
                logger.info("Someone used a WRONG username: " + username + " at login");
                return false;
            }
            personEntity = user.getSingleResult();
            if (!personEntity.getRole_id().getName().equals("recruit")) {
                logger.info("login attempt from non-recruiter: " + username);
                return false;
            }
            if (personEntity.getPassword().equals(password)) {
                logger.info(username + " logged in succesfully");
                return true;
            } else {
                logger.info("Someone used a WRONG password for user: " + username + " at login");
                return false;
            }
        } else {
            logger.info("logging attempt with invalid parameters from user: " + username);
            return false;
        }
    }

    /**
     * Returns a list of all persons with the role applicants
     *
     * @return List of PersonEntity classes
     */
    public List<PersonEntity> getApplicants() {
        List<PersonEntity> result = getPersonsByRole("applicant").getResultList();
        return result;
    }

    /**
     * Returns a list of all persons by the role selected
     *
     * @param role
     * @return List of PersonEntities
     */
    public TypedQuery<PersonEntity> getPersonsByRole(String role) {
        TypedQuery<RoleEntity> roleQuery = em.createNamedQuery("RoleEntity.findByName", RoleEntity.class);
        roleQuery.setParameter("name", role);
        roleEntity = roleQuery.getSingleResult();
        /*roleEntity = em.createNamedQuery("RoleEntity.findByName", RoleEntity.class)
                .setParameter("name", role).getSingleResult();*/
        TypedQuery<PersonEntity> resultSet = em.createNamedQuery("PersonEntity.findAllByRole", PersonEntity.class)
                .setParameter("role", roleEntity);

        return resultSet;
    }

    private boolean validateLoginParameters(String loginName, String loginPw) {
        if (loginPw.equals("") || loginName.equals("")) {
            return false;
        }

        if (!loginPw.matches(PW_REGEX) || !loginName.matches(USER_REGEX)) {
            return false;
        }
        return true;
    }
}
