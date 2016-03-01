package controller;

import model.PersonEntity;
import model.RegisterDTO;
import model.RoleEntity;
import view.RecruitmentManager;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;


@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class RecruitmentController {
    @PersistenceContext(unitName = "recruitPU")
    private EntityManager em;
    private PersonEntity personEntity;
    private RoleEntity roleEntity;

    private String NAME_REGEX = "^[a-zA-Z]+$";
    private String USER_REGEX = "^[a-zA-Z0-9]+$";
    private String SSN_REGEX = "^[0-9]+$";
    private String PW_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    /**
     * checks that the person trying to login are using a valid combination of
     * username and password
     *
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password, RecruitmentManager manager) {
        try {
            TypedQuery<PersonEntity> getUser = em.createNamedQuery("PersonEntity.findByUsername", PersonEntity.class)
                                    .setParameter("username", username);
            personEntity = getUser.getSingleResult();
            if (personEntity != null && personEntity.getPassword().equals(password)) {
                return true;
            }
            manager.setMessage("invalid username or password");
            return false;
        } catch (Exception e) {
            manager.setMessage("Database error");
        }

        if (validateLoginParameters(username, password)) {
            try {
                personEntity = em.find(PersonEntity.class, username);
                if (personEntity != null && personEntity.getPassword().equals(password)) {
                    return true;
                }
                manager.setMessage("invalid username or password");
                return false;
            } catch (Exception e1) {
                manager.setMessage("Database error");
                return false;
            }
        } else {
            manager.setMessage("Login parameters not valid");
            return false;
        }
    }

    /**
     * registers a user account and persists it in database
     *
     * @param registerDTO
     */
    public boolean register(RegisterDTO registerDTO, RecruitmentManager manager) {

        if (validateRegisterParameters(registerDTO, manager)) {
            Collection<PersonEntity> usernameCheck = em.createNamedQuery("PersonEntity.findByUsername")
                    .setParameter("username", registerDTO.getUsername()).getResultList();

            Collection<RoleEntity> role = em.createNamedQuery("RoleEntity.findByName")
                    .setParameter("name", registerDTO.getRole()).getResultList();

            roleEntity = role.iterator().next();

            if (usernameCheck.isEmpty()) {
                try {
                    personEntity = new PersonEntity(roleEntity, registerDTO.getFirstname(), registerDTO.getLastname(),
                            registerDTO.getSsn(), registerDTO.getEmail(), registerDTO.getUsername(),
                            registerDTO.getPassword());
                    em.persist(personEntity);
                } catch (Exception e) {
                    return false;
                }
            } else {
                manager.setMessage("Username taken");
                return false;
            }
        } else {
            manager.setMessage("registration failed due to invalid paramters");
            return false;
        }

        return true;
    }

    //method that validates login parametrs 
    private boolean validateLoginParameters(String loginName, String loginPw) {
        if (loginPw.equals("") || loginName.equals("")) {
            return false;
        }

        if (!loginPw.matches(PW_REGEX) || !loginName.matches(USER_REGEX)) {
            return false;
        }
        return true;

    }

    //method that validates register parameters 
    private boolean validateRegisterParameters(RegisterDTO registerDTO, RecruitmentManager manager) {
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

        if(!manager.isValidEmailAddress(registerDTO.getEmail())) {
            return false;
        }

        if(!registerDTO.getSsn().matches(SSN_REGEX) || (registerDTO.getSsn().length() != 10)) {
            return false;
        }
        return true;
    }
}