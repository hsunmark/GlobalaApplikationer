package model;

import javax.persistence.*;

/**
 * Created by Henrik on 2016-02-25.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "PersonEntity.findAll", query = "SELECT c FROM PersonEntity c"),
        @NamedQuery(name = "PersonEntity.findByUsername", query = "SELECT c FROM PersonEntity c WHERE c.username = :username")})
@Table(name = "person", schema = "recruitdb")
public class PersonEntity {
    private long personId;
    private String name;
    private String surname;
    private String ssn;
    private String email;
    private String password;
    private String username;
    private RoleEntity roleEntity;

    public PersonEntity(){}

    public PersonEntity(RoleEntity role, String firstname, String lastname,
                       String ssn, String email, String username, String password) {
        this.roleEntity = role;
        this.name = firstname;
        this.surname = lastname;
        this.ssn = ssn;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "person_id")
    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    @OneToOne
    public RoleEntity getOneToOne() {
        return roleEntity;
    }

    public void setOneToOne(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "ssn")
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "username")
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
        if (roleEntity != null ? !roleEntity.equals(that.roleEntity) : that.roleEntity != null) return false;
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
        result = 31 * result + (roleEntity != null ? roleEntity.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

}
