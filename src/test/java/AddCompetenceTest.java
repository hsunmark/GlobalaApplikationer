import controller.RecruitmentController;
import model.CompetenceEntity;
import model.CompetenceProfileEntity;
import model.PersonEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class AddCompetenceTest {

    private RecruitmentController controller;
    private CompetenceEntity competence;
    private CompetenceProfileEntity competenceProfile;
    private EntityManager mockEm;
    private TypedQuery<CompetenceEntity> mockCompetenceQuery;
    private BigDecimal randomBigDec;

    @Before
    public void setupTest() {
        System.out.println("Before...");
        mockEm = mock(EntityManager.class);
        mockCompetenceQuery = mock(TypedQuery.class);
        controller = new RecruitmentController();
        controller.setEm(mockEm);
        randomBigDec = new BigDecimal(2.50);

    }

    @After
    public void destroy() {
        System.out.println("After...");
    }

    @Test
    public void addCompetenceTest() {
        System.out.println("Test...");
        when(mockCompetenceQuery.getSingleResult()).thenReturn(competence);
        when(this.controller.getEm().createNamedQuery("CompetenceEntity.findByName", CompetenceEntity.class))
                .thenReturn(mockCompetenceQuery);

        competenceProfile = new CompetenceProfileEntity(randomBigDec, competence, new PersonEntity());
        controller.getEm().persist(competenceProfile);
        verify(mockEm, times(1)).persist(competenceProfile);
        controller.addCompetence(anyString(), randomBigDec);
    }
}












