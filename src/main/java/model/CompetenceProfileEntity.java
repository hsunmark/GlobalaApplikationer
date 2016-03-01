package model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Henrik on 2016-02-29.
 */
@Entity
@Table(name = "competence_profile", schema = "recruitdb")
public class CompetenceProfileEntity {
    private long competenceProfileId;
    private BigDecimal yearsOfExperience;
    private CompetenceEntity competence_profile_fk;
    private PersonEntity person_fk;

    @Id
    @Column(name = "competence_profile_id", nullable = false)
    public long getCompetenceProfileId() {
        return competenceProfileId;
    }

    public void setCompetenceProfileId(long competenceProfileId) {
        this.competenceProfileId = competenceProfileId;
    }

    @Basic
    @Column(name = "years_of_experience", nullable = true, precision = 2)
    public BigDecimal getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(BigDecimal yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetenceProfileEntity that = (CompetenceProfileEntity) o;

        if (competenceProfileId != that.competenceProfileId) return false;
        if (yearsOfExperience != null ? !yearsOfExperience.equals(that.yearsOfExperience) : that.yearsOfExperience != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (competenceProfileId ^ (competenceProfileId >>> 32));
        result = 31 * result + (yearsOfExperience != null ? yearsOfExperience.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "competence_id", referencedColumnName = "competence_id")
    public CompetenceEntity getCompetence_profile_fk() {
        return competence_profile_fk;
    }

    public void setCompetence_profile_fk(CompetenceEntity competence_profile_fk) {
        this.competence_profile_fk = competence_profile_fk;
    }

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    public PersonEntity getPerson_fk() {
        return person_fk;
    }

    public void setPerson_fk(PersonEntity person_fk) {
        this.person_fk = person_fk;
    }
}
