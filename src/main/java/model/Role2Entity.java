package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelt on 14/03/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Role2Entity.findAll", query = "SELECT c FROM Role2Entity c")
})
@Table(name = "role2", schema = "recruitdb")
public class Role2Entity {
    private int role_id;
    private List<Role_TranslationEntity> translations_fk = new ArrayList<Role_TranslationEntity>();

    /**
     * Creates a new instance of Role2Entity.
     */
    public Role2Entity(){}

    @Id
    @Column(name = "role_id", nullable = false)
    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    @OneToMany
    @JoinColumn(name = "role_id")
    public List<Role_TranslationEntity> getTranslations_fk() {
        return translations_fk;
    }

    public void setTranslations_fk(List<Role_TranslationEntity> translations_fk) {
        this.translations_fk = translations_fk;
    }

    public String toString(){
        /*StringBuilder br = new StringBuilder();
        List<Role_TranslationEntity> translations = getTranslations_fk();
        for(int i = 0; i < translations.size(); i++){
            br.append(translations.get(i).getName()+ ", ");
        }
        String x = br.toString();*/
        return "{ ID: "+getRole_id()+" words:  }";
    }
}
