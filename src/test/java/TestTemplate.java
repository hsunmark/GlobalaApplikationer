import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;




public class TestTemplate {

    @Before
    public void setupTest() {
        System.out.println("Before...");
    }

    @After
    public void destroy() {
        System.out.println("After...");
    }

    @Test
    public void runTest() {
        System.out.println("Test...");
    }
}
