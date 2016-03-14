import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.RecruitmentManager;
import javax.faces.context.FacesContext;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class ManagerTest {
    private RecruitmentManager manager;
    private Locale currentLocale;

    @BeforeMethod
    public void setupTest() {
        System.out.println("Before...");
        Locale.setDefault(new Locale("en", "EN"));
        manager = new RecruitmentManager();

    }

    @AfterMethod
    public void destroy() {
        System.out.println("After...");
        manager = null;
    }

    @Test
    public void runTest() {
        System.out.println("Test...");
    }
}
