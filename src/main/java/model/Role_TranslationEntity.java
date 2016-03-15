package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by michaelt on 14/03/16.
 */
@Entity
@Table(name = "role_translation", schema = "recruitdb")
public class Role_TranslationEntity implements Serializable {

    private RoleEntity roleID;
    private String locale;
    private String name;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    public RoleEntity getRoleID() {
        return roleID;
    }

    public void setRoleID(RoleEntity roleID) {
        this.roleID = roleID;
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
