package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Table with competence names.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "CompetenceEntity.findAll", query = "SELECT c FROM CompetenceEntity c"),
        @NamedQuery(name = "CompetenceEntity.findByName", query = "SELECT c FROM CompetenceEntity c WHERE c.name = :name"),
})
@Table(name = "competence", schema = "recruitdb")
public class CompetenceEntity {
    private long competenceId;
    private String name;
    private List<Competence_TranslationEntity> translations_fk = new ArrayList<Competence_TranslationEntity>();
    private CompetenceProfileEntity competence_fk;


    @Id
    @Column(name = "competence_id", nullable = false)
    public long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(long competenceId) {
        this.competenceId = competenceId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
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

    @OneToOne(mappedBy = "competence_profile_fk")
    public CompetenceProfileEntity getCompetence_fk() {
        return competence_fk;
    }

    public void setCompetence_fk(CompetenceProfileEntity competence_fk) {
        this.competence_fk = competence_fk;
    }


    @OneToMany
    @JoinColumn(name = "competence_id")
    public List<Competence_TranslationEntity> getTranslations_fk() {
        return translations_fk;
    }

    public void setTranslations_fk(List<Competence_TranslationEntity> translations_fk) {
        this.translations_fk = translations_fk;
    }
}
