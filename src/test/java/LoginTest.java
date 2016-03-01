import model.PersonEntity;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import static org.mockito.Mockito.*;


public class LoginTest {
    private PersonEntity mockPerson;
    private EntityManager mockEm;
    private TypedQuery<PersonEntity> mockPersonResult;

    @BeforeMethod
    public void setupMock() {
        System.out.println("Before...");

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
        when(mockPersonResult.setParameter("username", "username")).thenReturn(mockPersonResult);

    }
}
