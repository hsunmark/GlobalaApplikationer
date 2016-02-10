package model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Henrik on 2016-02-10.
 */
@Entity
@Table(name = "competence_profile", schema = "recruitdb")
public class CompetenceProfileEntity {
    private long competenceProfileId;
    private Long personId;
    private Long competenceId;
    private BigDecimal yearsOfExperience;

    @Id
    @Column(name = "competence_profile_id")
    public long getCompetenceProfileId() {
        return competenceProfileId;
    }

    public void setCompetenceProfileId(long competenceProfileId) {
        this.competenceProfileId = competenceProfileId;
    }

    @Basic
    @Column(name = "person_id")
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "competence_id")
    public Long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }

    @Basic
    @Column(name = "years_of_experience")
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
        if (personId != null ? !personId.equals(that.personId) : that.personId != null) return false;
        if (competenceId != null ? !competenceId.equals(that.competenceId) : that.competenceId != null) return false;
        if (yearsOfExperience != null ? !yearsOfExperience.equals(that.yearsOfExperience) : that.yearsOfExperience != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (competenceProfileId ^ (competenceProfileId >>> 32));
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (competenceId != null ? competenceId.hashCode() : 0);
        result = 31 * result + (yearsOfExperience != null ? yearsOfExperience.hashCode() : 0);
        return result;
    }
}
