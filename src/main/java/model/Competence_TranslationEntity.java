package model;

import javax.persistence.*;

/**
 * Created by michaelt on 15/03/16.
 */
@Entity
@Table(name = "competence_translations", schema = "recruitdb")
public class Competence_TranslationEntity {
    private CompetenceEntity competence_id;
    private String locale;
    private String name;

    @Id
    @ManyToOne
    @JoinColumn(name = "competence_id", referencedColumnName = "competence_id")
    public CompetenceEntity getCompetence_id() {
        return competence_id;
    }

    public void setCompetence_id(CompetenceEntity competence_id) {
        this.competence_id = competence_id;
    }

    @Id
    @Column(name = "locale", nullable = true)
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Basic
    @Column(name = "name", nullable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
