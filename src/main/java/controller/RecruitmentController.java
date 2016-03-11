package controller;

import model.PersonEntity;
import model.RegisterDTO;
import model.RoleEntity;
import view.RecruitmentManager;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * A controller. Handles all calls to the database and the requests
 * from the Manager.
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class RecruitmentController {
    @PersistenceContext(unitName = "recruitPU")
    private EntityManager em;
    private PersonEntity personEntity;
    private RoleEntity roleEntity;
    private RecruitmentManager manager;
    private Logger logger = Logger.getLogger(getClass().getName());

    private String NAME_REGEX = "^[a-zA-Z]+$";
    private String USER_REGEX = "^[a-zA-Z0-9]+$";
    private String SSN_REGEX = "^[0-9]+$";
    private String PW_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    /**
     * Checks that the person trying to login are using a valid combination of
     * username and password.
     *
     * @param username
     * @param password
     * @param manager
     * @return true if login is successful, false otherwise.
     */
    public boolean login(String username, String password, RecruitmentManager manager) {
        this.manager = manager;
        if (validateLoginParameters(username, password)) {
            TypedQuery<PersonEntity> user;
            try {
                user = em.createNamedQuery(
                        "PersonEntity.findByUsername", PersonEntity.class)
                        .setParameter("username", username);
            } catch (Exception e) {
                manager.setMessage("LoginMessage2");
                logger.info("Someone used a WRONG username: " + username + " at login");
                return false;
            }
            personEntity = user.getSingleResult();
            setPermission(personEntity);

            if (personEntity.getPassword().equals(password)) {
                logger.info(username + " logged in succesfully");
                return true;
            } else {
                manager.setMessage("LoginMessage2");
                logger.info("Someone used a WRONG password for user: " + username + " at login");
                return false;
            }

        } else {
            manager.setMessage("LoginMessage2");
            logger.info("logging attempt with invalid parameters from user: " + username);
            return false;
        }
    }

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
     * Registers a user account and store the information
     * in the database
     *
     * @param registerDTO
     * @param manager
     * @return true if register is successful, false otherwise.
     */
    public boolean register(RegisterDTO registerDTO, RecruitmentManager manager) {
        this.manager = manager;
        if (validateRegisterParameters(registerDTO)) {
            try {
                TypedQuery<PersonEntity> usernameCheck = em.createNamedQuery("PersonEntity.findByUsername", PersonEntity.class)
                        .setParameter("username", registerDTO.getUsername());

                roleEntity = em.createNamedQuery("RoleEntity.findByName", RoleEntity.class)
                        .setParameter("name", registerDTO.getRole()).getSingleResult();

                if (usernameCheck.getResultList().isEmpty()) {
                    personEntity = new PersonEntity(roleEntity, registerDTO.getFirstname(), registerDTO.getLastname(),
                            registerDTO.getSsn(), registerDTO.getEmail(), registerDTO.getUsername(),
                            registerDTO.getPassword());
                    em.persist(personEntity);
                    setPermission(personEntity);
                } else {
                    manager.setMessage("Username taken");
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        } else {
            manager.setMessage("registration failed due to invalid paramters");
            return false;
        }

        logger.info("new user registered: " + registerDTO.getUsername());
        return true;
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
        roleEntity = em.createNamedQuery("RoleEntity.findByName", RoleEntity.class)
                .setParameter("name", role).getSingleResult();
        TypedQuery<PersonEntity> resultSet = em.createNamedQuery("PersonEntity.findAllByRole", PersonEntity.class)
                .setParameter("role", roleEntity);

        return resultSet;
    }

    //method that validates login parametrs 
    //public for testing (remove later)
    public boolean validateLoginParameters(String loginName, String loginPw) {
        if (loginPw.equals("") || loginName.equals("")) {
            return false;
        }

        if (!loginPw.matches(PW_REGEX) || !loginName.matches(USER_REGEX)) {
            return false;
        }
        return true;

    }

    //method that validates register parameters 
    private boolean validateRegisterParameters(RegisterDTO registerDTO) {
        if (registerDTO.getUsername().equals("")
                || registerDTO.getPassword().equals("")
                || registerDTO.getFirstname().equals("")
                || registerDTO.getLastname().equals("")
                || registerDTO.getRole().equals("")
                || registerDTO.getSsn().equals("")
                || registerDTO.getEmail().equals("")) {
            return false;
        }

        if (registerDTO.getPassword().length() < 6) {
            return false;
        }
        if (!registerDTO.getUsername().matches(USER_REGEX)
                || !registerDTO.getPassword().matches(PW_REGEX)
                || !registerDTO.getFirstname().matches(NAME_REGEX)
                || !registerDTO.getLastname().matches(NAME_REGEX)) {
            return false;
        }

        if (!isValidEmailAddress(registerDTO.getEmail())) {
            return false;
        }

        if (!registerDTO.getSsn().matches(SSN_REGEX) || (registerDTO.getSsn().length() != 10)) {
            return false;
        }
        return true;
    }

    private void setPermission(PersonEntity personEntity) {
        RoleEntity role = personEntity.getRole_id();
        String roleName = role.getName();
        if (roleName.equals("applicant")) {
            manager.setApplicant(true);
        } else if (roleName.equals("recruit")) {
            manager.setRecruit(true);
        }
    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}