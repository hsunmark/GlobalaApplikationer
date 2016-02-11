package view;

import controller.RecruitmentController;
import model.RegisterDTO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;


@ManagedBean(name = "recruitmentManager")
@SessionScoped
public class RecruitmentManager implements Serializable {

    @EJB
    private RecruitmentController controller;
    private String username;
    private String password;
    private String password2;
    private String firstname;
    private String lastname;
    private String role;
    private String ssn;
    private String email;
    private String message;

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String login(){
        String message = validateLoginParameters();
        if(message.equals("ok")){
            controller.login(username, password);
        }
        System.out.println(message);
        return "";
    }

    public String register(){
        message = validateRegisterParameters() ;
        if (message.equals("ok")){
           controller.register(new RegisterDTO("recruit", firstname, lastname, ssn, email, username, password));
        }
        System.out.println(message);
        return "";
    }

    private String validateRegisterParameters() {
        if(username.equals("")
                || password.equals("")
                || password2.equals("")
                || firstname.equals("")
                || lastname.equals("")
                || role.equals("")
                || ssn.equals("")
                || email.equals("")){
            return "You have not filled all the fields.";
        }
        if(!password.equals(password2)){
            return "Passwords does not match";
        }
        if(password.length() < 6){
            return "Password must be atleast 6 charachters long";
        }
        String nameregex = "^[a-zA-Z]+$";
        String userregex = "^[a-zA-Z0-9]+$";
        String ssnregex = "^[0-9]+$";
        if(!username.matches(userregex)
                || !password.matches(userregex)
                || !firstname.matches(nameregex)
                || !lastname.matches(nameregex)){
            return "You are using invalid characters.. " +
                    "(aA-zZ allowed for names and aA-zZ + 0-9 allowed for username and password)";
        }
        if(!isValidEmailAddress(email)){
            return "Your email is not a valid email address";
        }

        if((!ssn.matches(ssnregex) || (ssn.length()!=10))){
            return "Your ssn should be 10 numbers";
        }
        return "ok";
    }

    private String validateLoginParameters() {
        if(password.equals("") || username.equals("")){
            return "Please enter a valid username and a password";
        }
        String regex = "^[a-zA-Z0-9]+$";
        if (!password.matches(regex) || !username.matches(regex)) {
            return "You are only allowed to use characters and/or numbers";
        }
        return "ok";
    }

    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
