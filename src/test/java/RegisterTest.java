import controller.RecruitmentController;
import model.PersonEntity;
import model.RegisterDTO;
import model.RoleEntity;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.RecruitmentManager;

import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.constraints.AssertTrue;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


/**
 * Created by Henrik on 2016-03-01.
 */


public class RegisterTest {
    private RecruitmentController controller;
    private RecruitmentManager mockManager;
    private PersonEntity mockPerson;
    private RegisterDTO mockDTO;
    private RoleEntity mockRole;
    private EntityManager mockEm;
    private InternetAddress address;
    private TypedQuery<RoleEntity> roleResult;
    private TypedQuery<PersonEntity> personResult;

    @BeforeMethod
    public void setupTest() {
        System.out.println("Before...");

        controller = new RecruitmentController();
        mockManager =  mock(RecruitmentManager.class);
        mockPerson = mock(PersonEntity.class);
        mockDTO = mock(RegisterDTO.class);
        mockRole = mock(RoleEntity.class);
        mockEm = mock(EntityManager.class);
        roleResult = mock(TypedQuery.class);
        personResult = mock(TypedQuery.class);
    }

    @AfterMethod
    public void destroy() {
        System.out.println("After...");

    }

    @Test
    public void testRegister() {
        System.out.println("Test...");

        when((mockEm.createNamedQuery("PersonEntity.findByUsername", PersonEntity.class))).thenReturn(personResult);
        when(mockEm.createNamedQuery("RoleEntity.findByName", RoleEntity.class)).thenReturn(roleResult);

        //assertFalse(controller.register(mockDTO, mockManager));
    }

    @Test
    public void testValidEmail() {

        //assertTrue(mockManager.isValidEmailAddress("test@domain.se"));
    }

}
