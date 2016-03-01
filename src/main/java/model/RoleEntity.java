package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Henrik on 2016-02-25.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "RoleEntity.findAll", query = "SELECT c FROM RoleEntity c"),
        @NamedQuery(name = "RoleEntity.findByName", query = "SELECT OBJECT(c) FROM RoleEntity c WHERE c.name = :name")})
@Table(name = "role", schema = "recruitdb")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long roleId;
    private String name;

    public RoleEntity () {}

    //@OneToMany(targetEntity = PersonEntity.class)
    //private List<PersonEntity> personEntities;

    //public List<PersonEntity> getPersonEntities() {
    //    return personEntities;
   // }

    //public void setPersonEntities(List<PersonEntity> personEntities) {
        //this.personEntities = personEntities;
    //}

    @Column(name = "role_id")
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
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

        RoleEntity that = (RoleEntity) o;

        if (roleId != that.roleId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
