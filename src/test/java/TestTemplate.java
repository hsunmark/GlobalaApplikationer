import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

/**
 * Created by Henrik on 2016-03-01.
 */


public class TestTemplate {

    @BeforeMethod
    public void setupTest() {
        System.out.println("Before...");
    }

    @AfterMethod
    public void destroy() {
        System.out.println("After...");
    }

    @Test
    public void runTest() {
        System.out.println("Test...");
    }
}
