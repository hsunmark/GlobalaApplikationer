import controller.RecruitmentController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.RecruitmentManager;

import javax.mail.internet.InternetAddress;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class ManagerTest {
    private RecruitmentManager manager;

    @Before
    public void setupTest() {
        System.out.println("Before...");
        manager = new RecruitmentManager();
        manager.setRecruitmentController(mock(RecruitmentController.class));
       /* NoClassDefFoundError ???
        manager.setInternetAdress(new InternetAddress());
        */
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

    @Test
    public void testRegister() {
        manager.setRole("recruiter");
        manager.setFirstname("test");
        manager.setLastname("tester");
        manager.setSsn("1234567890");
        manager.setEmail("test@kth.se");
        manager.setUsername("testusr");
        manager.setPassword("Qwerty123@");
        manager.setPassword2("Qwerty123@");

       // manager.register();
    }

    @Test
    public void testLocale() {
        manager.setCurrentLocale("swe");
        manager.setMessage("RegisterMessage5");
        assertEquals(manager.getMessage(), "Ogiltig mailaddress");

        manager.setCurrentLocale("eng");
        manager.setMessage("RegisterMessage5");
        assertEquals(manager.getMessage(), "Your email is not a valid email address");
    }
}







