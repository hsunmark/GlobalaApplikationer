package controller;

import model.PersonEntity;
import model.RegisterDTO;
import model.RoleEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class RecruitmentController {
    @PersistenceContext(unitName = "recruitPU")
    private EntityManager em;
    private PersonEntity personEntity;
    private RoleEntity roleEntity;

    public String login(String username, String password){
        return"";
    }

    public void register(RegisterDTO registerDTO) {
        // TODO validera alla värden i objektet
        roleEntity = em.find(RoleEntity.class, registerDTO.getRole());
        personEntity = new PersonEntity(registerDTO.getFirstname(), registerDTO.getLastname(),
                registerDTO.getSsn(), registerDTO.getEmail(), registerDTO.getUsername(),
                registerDTO.getPassword(), roleEntity.getRoleId());

        em.persist(personEntity);
    }
}
