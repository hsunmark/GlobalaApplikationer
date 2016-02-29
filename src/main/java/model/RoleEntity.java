package model;

import javax.persistence.*;

/**
 * Created by Henrik on 2016-02-29.
 */
@Entity
@Table(name = "role", schema = "recruitdb")
public class RoleEntity {
    private long roleId;
    private String name;
    private PersonEntity role_id;

    public RoleEntity(){}

    @Id
    @Column(name = "role_id", nullable = false)
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
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

    @OneToOne(mappedBy = "role_id")
    public PersonEntity getRole_id() {
        return role_id;
    }

    public void setRole_id(PersonEntity role_id) {
        this.role_id = role_id;
    }
}
