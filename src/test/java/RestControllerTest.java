import controller.RestController;
import model.PersonEntity;
import model.RoleEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


public class RestControllerTest {

    private RestController controller;
    private RoleEntity role, returnedRole;
    private PersonEntity person, returnedPerson;
    private TypedQuery<RoleEntity> mockRoleQuery;
    private TypedQuery<PersonEntity> mockPersonQuery;

    @Before
    public void setupTest() {
        System.out.println("Before...");

        this.controller = new RestController();
        this.controller.em = mock(EntityManager.class);
        mockRoleQuery = mock(TypedQuery.class);
        mockPersonQuery = mock(TypedQuery.class);
        role = new RoleEntity();
        person = new PersonEntity();
    }

    @After
    public void destroy() {
        System.out.println("After...");
    }

    @Test
    public void returnApplicantsTest() {
        System.out.println("Test...");

        when(this.controller.em.createNamedQuery("RoleEntity.findByName", RoleEntity.class)).thenReturn(mockRoleQuery);
        when(mockRoleQuery.setParameter("name", "applicant")).thenReturn(mockRoleQuery);
        when(mockRoleQuery.getSingleResult()).thenReturn(role);
        returnedRole = this.controller.em.createNamedQuery("RoleEntity.findByName", RoleEntity.class).getSingleResult();
        assertSame(role, returnedRole);

        when(this.controller.em.createNamedQuery("PersonEntity.findAllByRole", PersonEntity.class)).thenReturn(mockPersonQuery);
        when(mockPersonQuery.getSingleResult()).thenReturn(person);
        returnedPerson = this.controller.em.createNamedQuery("PersonEntity.findAllByRole", PersonEntity.class).getSingleResult();
        assertSame(person, returnedPerson);

    }
}














