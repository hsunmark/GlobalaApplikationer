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


@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class RecruitmentController {
    @PersistenceContext(unitName = "recruitPU")
    private EntityManager em;
    private PersonEntity personEntity;
    private RoleEntity roleEntity;
    private RecruitmentManager manager;

    /**
     * checks that the person trying to login are using a valid combination of
     * username and password
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password){
        try {
            personEntity = em.find(PersonEntity.class, username);
            if (personEntity != null && personEntity.getPassword().equals(password)) {
                return true;
            }
            manager.setMessage("invalid username or password");
            return false;
        } catch (Exception e) {
            manager.setMessage("Database error");
            return false;
        }
    }

    /**
     * registers a user account and persists it in database
     * @param registerDTO
     */
    public boolean register(RegisterDTO registerDTO) {
        PersonEntity personEntityCheck = em.find(PersonEntity.class, registerDTO.getUsername());

        if (personEntityCheck.getUsername() == null) {
            try {
            roleEntity = em.find(RoleEntity.class, registerDTO.getRole());
            personEntity = new PersonEntity(roleEntity, registerDTO.getFirstname(), registerDTO.getLastname(),
                    registerDTO.getSsn(), registerDTO.getEmail(), registerDTO.getUsername(),
                    registerDTO.getPassword());
                em.persist(personEntity);
            } catch (Exception e){
                return false;
            }
        } else {
            manager.setMessage("Username taken");
        }
        return true;
    }
}
