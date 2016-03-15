import controller.RecruitmentController;
import model.PersonEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.RecruitmentManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;



public class LoginTest {
    private RecruitmentController mockController, controller;
    private RecruitmentManager mockManager, manager;
    private PersonEntity mockPerson;
    private EntityManager mockEm;
    private TypedQuery<PersonEntity> mockPersonResult;

    @Before
    public void setupMock() {
        System.out.println("Before...");
        controller = new RecruitmentController();
        mockController = mock(RecruitmentController.class);
        manager = mock(RecruitmentManager.class);
        mockManager = mock(RecruitmentManager.class);
        mockPerson = mock(PersonEntity.class);
        mockEm = mock(EntityManager.class);
        mockPersonResult = mock(TypedQuery.class);
    }

    @After
    public void destroy() {
        System.out.println("After...");

        controller = null;
    }

    @Test
    public void testValidLogin() throws Exception {
        System.out.println("Test...");

        when((mockEm.createNamedQuery("PersonEntity.findByUsername", PersonEntity.class))).thenReturn(mockPersonResult);
        when(mockPersonResult.getSingleResult()).thenReturn(mockPerson);
        when(mockPersonResult.setParameter("username", "testUser")).thenReturn(mockPersonResult);
        when(mockPerson.getUsername()).thenReturn("testUser");
        when(mockPerson.getPassword()).thenReturn("testpwd");
        when(mockController.login("testUser", "testpwd", manager)).thenReturn(true);
        assertTrue(mockController.login(mockPerson.getUsername(), mockPerson.getPassword(), manager));
        //Verifies that the method was actually invoked with the correct parameters and only once
        verify(mockController, times(1)).login("testUser", "testpwd", manager);
        assertFalse(mockController.login("testusr", "testpwd2", manager));
        verify(mockController, times(1)).login("testusr", "testpwd2", manager);
    }

    @Test
    public void testEmptyLogin() {
        when(mockEm.createNamedQuery("PersonEntity.findByUsername", PersonEntity.class)).thenReturn(mockPersonResult);
        when(this.mockPersonResult.getSingleResult()).thenReturn(mockPerson);
        when(this.mockPerson.getUsername()).thenReturn("testuser");
        when(this.mockPerson.getPassword()).thenReturn("Qwerty123@");
        when(this.mockController.login(mockPerson.getUsername(), mockPerson.getPassword(), manager)).thenReturn(true);
        assertTrue(this.mockController.login("testuser", "Qwerty123@", manager));
    }

    @Test
    public void testLoginParams() {
        boolean trueResult = controller.validateLoginParameters("testusr", "Qwerty123@");
        boolean falseResult = controller.validateLoginParameters("testusr", "qwerty");
        assertEquals(true, trueResult);
        assertEquals(false, falseResult);

    }
}












