package model;


public class RegisterDTO {
    private String role;
    private String firstname;
    private String lastname;
    private String ssn;
    private String email;
    private String username;
    private String password;

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

    //TODO fullösning. Fixa
    public long getRole() {
        if(role.equals("recruit")) return 1;
        else if(role.equals("applicant")) return 2;
        return 0;
    }

    public String getUsername() {
        return username;
    }
}
