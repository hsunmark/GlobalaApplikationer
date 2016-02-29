package model;

/**
 * Creates a data transfer object represntring an application user
 */
public class RegisterDTO {
    private String role;
    private String firstname;
    private String lastname;
    private String ssn;
    private String email;
    private String username;
    private String password;

    /**
     *
     * @param role
     * @param firstname
     * @param lastname
     * @param ssn
     * @param email
     * @param username
     * @param password
     */
    public RegisterDTO(String role, String firstname, String lastname,
                       String ssn, String email, String username, String password) {
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssn = ssn;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPassword() {
        return password;
    }

    public String getSsn() {
        return ssn;
    }

    public String getLastname() {
        return lastname;
    }

    public String getRole() { return role; }

    public String getUsername() {
        return username;
    }
}
