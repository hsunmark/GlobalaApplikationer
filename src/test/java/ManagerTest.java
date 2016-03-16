import controller.RecruitmentController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.RecruitmentManager;

import javax.mail.internet.InternetAddress;
import java.math.BigDecimal;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class ManagerTest {
    private RecruitmentManager manager;
    private RecruitmentController mockController;
    private BigDecimal bigDecimal;

    @Before
    public void setupTest() {
        System.out.println("Before...");
        manager = new RecruitmentManager();
        manager.setRecruitmentController(mock(RecruitmentController.class));
        mockController = mock(RecruitmentController.class);
        bigDecimal = new BigDecimal(2.50);
    }

    @After
    public void destroy() {
        System.out.println("After...");
        manager = null;
    }

    @Test
    public void testLoginValid() {
        System.out.println("testLoginValid:");

        manager.setLoginName("testuser");
        manager.setLoginPw("Qwerty123@");

        String result = manager.login();
        assertEquals(result, ""); //Successful method call returns empty string (jsfbugfix)
    }

    @Test
    public void testLoginInvalid() {
        System.out.println("testLoginNull:");
        manager.setCurrentLocale("swe");

        manager.setLoginName("");
        manager.setLoginPw("");

        manager.login();
        String msg = manager.getMessage();
        assertEquals(msg, "Fyll alla fällt");

        manager.setLoginName("testuser");
        manager.setLoginPw("Qwerty123");

        manager.login();
        msg = manager.getMessage();
        assertEquals(msg, "Ogiltig inloggning");
    }

    /**
     * If this test throws any other exception, such as NullPointerException,
     * the validateparameters failed
     */
    @Test(expected = NoClassDefFoundError.class)
        public void testRegister() {
        System.out.println("testRegister:");
        manager.setRole("applicant");
        manager.setFirstname("test");
        manager.setLastname("tester");
        manager.setSsn("1234567890");
        manager.setEmail("test@kth.se");
        manager.setUsername("testusr");
        manager.setPassword("Qwerty123@");
        manager.setPassword2("Qwerty123@");

        manager.register();
    }

    @Test
    public void testLocale() {
        System.out.println("testLocale:");

        manager.setCurrentLocale("swe");
        manager.setMessage("RegisterMessage5");
        assertEquals(manager.getMessage(), "Ogiltig mailaddress");

        manager.setCurrentLocale("eng");
        manager.setMessage("RegisterMessage5");
        assertEquals(manager.getMessage(), "Your email is not a valid email address");
    }

    @Test
    public void testAddCompetence() {
        System.out.println("testAddCompetence:");
        when(mockController.addCompetence("someCompetence", bigDecimal)).thenReturn(true);
        manager.setRecruitmentController(mockController);
        manager.setCompetence("someCompetence");
        manager.setYears(bigDecimal);

        manager.addCompetence();
        verify(mockController, times(1)).addCompetence(manager.getCompetence(), bigDecimal);
        assertEquals("Kompetens sparad", manager.getMessage());
    }
}
