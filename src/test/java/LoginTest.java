import controller.RecruitmentController;
import model.PersonEntity;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.RecruitmentManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;



public class LoginTest {
    private RecruitmentController controller;
    private RecruitmentManager manager;
    private PersonEntity mockPerson;
    private EntityManager mockEm;
    private TypedQuery<PersonEntity> mockPersonResult;

    @BeforeMethod
    public void setupMock() {
        System.out.println("Before...");

        controller = mock(RecruitmentController.class);
        manager = mock(RecruitmentManager.class);
        mockPerson = mock(PersonEntity.class);
        mockEm = mock(EntityManager.class);
        mockPersonResult = mock(TypedQuery.class);
    }

    @AfterMethod
    public void destroy() {
        System.out.println("After...");

        mockPerson = null;
        mockEm = null;
        mockPersonResult = null;
    }

    @Test
    public void testLogin() throws Exception {
        System.out.println("Test...");

        when((mockEm.createNamedQuery("PersonEntity.findByUsername", PersonEntity.class))).thenReturn(mockPersonResult);
        when(mockPersonResult.getSingleResult()).thenReturn(mockPerson);
        when(mockPersonResult.setParameter("username", "testUsername")).thenReturn(mockPersonResult);

        when(controller.login("testusr", "testpwd", manager)).thenReturn(true);
        assertTrue(controller.login("testusr", "testpwd", manager));
        //Verifies that the method was actually invoked with the correct parameters and only once
        verify(controller, times(1)).login("testusr", "testpwd", manager);
        assertFalse(controller.login("testusr", "testpwd2", manager));
        verify(controller, times(1)).login("testusr", "testpwd2", manager);
    }
}












