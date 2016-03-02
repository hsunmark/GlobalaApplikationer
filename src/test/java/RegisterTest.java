import controller.RecruitmentController;
import model.PersonEntity;
import model.RegisterDTO;
import model.RoleEntity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.RecruitmentManager;

import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


/**
 * Created by Henrik on 2016-03-01.
 */


public class RegisterTest {
    private RecruitmentController mockController, controller;
    private RecruitmentManager mockManager, manager;
    private PersonEntity mockPerson;
    private RegisterDTO mockDTO, realDTO;
    private RoleEntity mockRole;
    private EntityManager mockEm;
    private InternetAddress address;
    private TypedQuery<RoleEntity> roleResult;
    private TypedQuery<PersonEntity> personResult;

    @BeforeMethod
    public void setupTest() {
        System.out.println("Before...");
        controller = new RecruitmentController();
        manager = new RecruitmentManager();
        mockController = mock(RecruitmentController.class);
        mockManager =  mock(RecruitmentManager.class);
        mockPerson = mock(PersonEntity.class);
        mockDTO = mock(RegisterDTO.class);
        realDTO = new RegisterDTO("recruiter", "firstname", "lastname", "1234567890",
                                "valid@email.se","testUser", "Qwerty123@");
        mockRole = mock(RoleEntity.class);
        mockEm = mock(EntityManager.class);
        roleResult = mock(TypedQuery.class);
        personResult = mock(TypedQuery.class);
    }

    @AfterMethod
    public void destroy() {
        System.out.println("After...");
        controller = null;
        manager = null;
        realDTO = null;
    }

    @Test
    public void testRegister() {
        System.out.println("Test...");

        when((mockEm.createNamedQuery("PersonEntity.findByUsername", PersonEntity.class))).thenReturn(personResult);
        when(personResult.setParameter("username", "testUser")).thenReturn(personResult);
        when(mockEm.createNamedQuery("RoleEntity.findByName", RoleEntity.class)).thenReturn(roleResult);
        when(roleResult.setParameter("name", "recruit")).thenReturn(roleResult);
        /**
         * Fails java.lang.NoClassDefFoundError: com/sun/mail/util/PropUtil
         */
        //assertTrue(controller.validateRegisterParameters(realDTO, manager));
    }

/*    @Test
    public void testValidEmail() {
        when(mockManager.isValidEmailAddress("test@domain.se")).thenReturn(true);
        assertTrue(mockManager.isValidEmailAddress("test@domain.se"));
        verify(mockManager, times(1)).isValidEmailAddress("test@domain.se");
        assertFalse(mockManager.isValidEmailAddress(".test."));
    }
*/
}
