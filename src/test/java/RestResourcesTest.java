import com.google.gson.reflect.TypeToken;
import controller.RestController;
import model.PersonEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import view.RestResources;



public class RestResourcesTest {

    private RestResources rest;
    private RestController mockController;
    private List<PersonEntity> resultSet, returnedResultSet;
    private String gsonResult;

    @Before
    public void setupTest() {
        System.out.println("Before...");

        rest = new RestResources();
        resultSet = new ArrayList<PersonEntity>();

        mockController = mock(RestController.class);
        rest.setRestController(mockController);
    }

    @After
    public void destroy() {
        System.out.println("After...");
    }

    @Test
    public void testLogin() {
        System.out.println("Test...");
        when(mockController.login(anyString(), anyString())).thenReturn(true);
        String result = rest.login("testuser", "Qwerty123@");

        assertEquals(result, "1");
    }

    @Test
    public void testGetApplicants() {
        when(mockController.getApplicants()).thenReturn(resultSet);
        gsonResult = rest.getApplications();
        assertNotNull(gsonResult);

        returnedResultSet = rest.getGson().fromJson(gsonResult, new TypeToken<List<PersonEntity>>()
        {}.getType());

        assertEquals(resultSet, returnedResultSet);
    }
}














