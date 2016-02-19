package controller;

import model.PersonEntity;
import model.RegisterDTO;
import model.RoleEntity;

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

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password){
        try {
            personEntity = em.find(PersonEntity.class, username);
            if (personEntity != null && personEntity.getPassword().equals(password)) {
                return "logged in successfully!";
            }
            return "invalid username or password";
        } catch (Exception e) {
            return "Database error";
        }
    }

    /**
     * registers a user account and persists it in database
     * @param registerDTO
     */
    public boolean register(RegisterDTO registerDTO) {
        try {
            roleEntity = em.find(RoleEntity.class, registerDTO.getRole());
            personEntity = new PersonEntity(registerDTO.getFirstname(), registerDTO.getLastname(),
                    registerDTO.getSsn(), registerDTO.getEmail(), registerDTO.getUsername(),
                    registerDTO.getPassword(), roleEntity.getRoleId());

            em.persist(personEntity);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
