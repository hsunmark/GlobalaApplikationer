package model;

import javax.persistence.*;

/**
 * Created by Henrik on 2016-02-24.
 */
@Entity
@Table(name = "competence", schema = "recruitdb")
public class CompetenceEntity {
    private long competenceId;
    private String name;
    private CompetenceProfileEntity competence_id;

    @Id
    @Column(name = "competence_id")
    public long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(long competenceId) {
        this.competenceId = competenceId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetenceEntity that = (CompetenceEntity) o;

        if (competenceId != that.competenceId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (competenceId ^ (competenceId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToOne(mappedBy = "competence_id")
    public CompetenceProfileEntity getCompetence_id() {
        return competence_id;
    }

    public void setCompetence_id(CompetenceProfileEntity competence_id) {
        this.competence_id = competence_id;
    }
}
