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
import javax.persistence.Query;
import javax.persistence.metamodel.CollectionAttribute;
import java.util.Collection;


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

        //Collection<PersonEntity> personEntityCheck = em.createNamedQuery("PersonEntity.findByUsername")
                //.setParameter("username", registerDTO.getUsername()).getResultList();
        //TODO ta bort hårdkodningen
        //Collection<RoleEntity> role = em.createNamedQuery("RoleEntity.findByName")
          //      .setParameter("name", "applicant").getResultList();
        //TODO fixa bättre lösning än loop?
       // for (RoleEntity i : role) {
         //   roleEntity = i;
        //}
        //PersonEntity test = em.find(PersonEntity.class, (long)1);
        //System.out.println("****************" + test.getName());

        RoleEntity test = em.find(RoleEntity.class, (long)1);
        System.out.println("****************" + test.getName());

        //if (personEntityCheck.isEmpty()) {
            try {
            personEntity = new PersonEntity(roleEntity, registerDTO.getFirstname(), registerDTO.getLastname(),
                    registerDTO.getSsn(), registerDTO.getEmail(), registerDTO.getUsername(),
                    registerDTO.getPassword());
                em.persist(personEntity);
            } catch (Exception e){
                return false;
            }
        //} else {
            //manager.setMessage("Username taken");
        //}
        return true;
    }
}
