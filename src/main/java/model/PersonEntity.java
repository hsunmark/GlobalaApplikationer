package model;

import javax.persistence.*;

/**
 * Created by Henrik on 2016-02-29.
 */
@Entity
@Table(name = "person", schema = "recruitdb")
public class PersonEntity {
    private long personId;
    private String name;
    private String surname;
    private String ssn;
    private String email;
    private String password;
    private String username;
    private RoleEntity role_id;

    public PersonEntity(){}
    public PersonEntity(RoleEntity role, String firstname, String lastname,
                        String ssn, String email, String username, String password) {
        this.role_id = role;
        this.name = firstname;
        this.surname = lastname;
        this.ssn = ssn;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Id
    @Column(name = "person_id", nullable = false)
    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = true, length = 255)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "ssn", nullable = true, length = 255)
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonEntity that = (PersonEntity) o;

        if (personId != that.personId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (ssn != null ? !ssn.equals(that.ssn) : that.ssn != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (personId ^ (personId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (ssn != null ? ssn.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    public RoleEntity getRole_id() {
        return role_id;
    }

    public void setRole_id(RoleEntity role_id) {
        this.role_id = role_id;
    }
}
