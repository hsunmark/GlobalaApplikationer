package view;

import controller.RecruitmentController;
import model.RegisterDTO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;


@ManagedBean(name = "recruitmentManager", eager = true)
@SessionScoped
public class RecruitmentManager implements Serializable {

    @EJB
    private RecruitmentController controller;
    private String loginName;
    private String loginPw;
    private String username;
    private String password;
    private String password2;
    private String firstname;
    private String lastname;
    private String role;
    private String ssn;
    private String email;
    private String message;
    private Exception error;
    private boolean loginSucces;

    public boolean getLoginSucces() {
        return loginSucces;
    }

    public void setLoginSucces(boolean loginSucces) {
        this.loginSucces = loginSucces;
    }



    public String getLoginName(){
        return loginName;
    }

    public String getLoginPw(){
        return loginPw;
    }

    public void setLoginName(String loginName){
        this.loginName = loginName;
    }

    public void setLoginPw(String loginPw){
        this.loginPw = loginPw;
    }

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

    private void handleException(Exception e) {
        e.printStackTrace(System.err);
        error = e;
    }

    public boolean getError() {
        return error == null;
    }

    public Exception getException() {
        return error;
    }

    /**
     * Method used to validate an user login attempt
     * @return returns an empty string due to jsf22bugfix
     */
    public String login(){
        String message = validateLoginParameters();
        if(message.equals("ok")){
           message = controller.login(loginName, loginPw);
            if(message.equals("logged in successfully!")){
                loginSucces = true;
            }
        }
        return "";
    }


    /**
     * Method register:
     * Validates all the fields that a user has typed in and
     * if the fields are validated method calls controller.register(RegisterDTO dto)
     * to continue registration.
     * Otherwise an errormessage is set by this method
     *
     *
     * @return      returns an empty string due to jsf22bugfix
     *
     */
    public String register(){
        try {
            error = null;
            message = validateRegisterParameters();
            if (message.equals("ok")) {
                loginSucces = controller.register(new RegisterDTO("recruit", firstname, lastname, ssn, email, username, password));
            }
        } catch (Exception e) {
        handleException(e);
    }
        return "";
    }


    //method that validates parameters for registration.
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

    //method that validates parameters for login
    private String validateLoginParameters() {
        if(loginPw.equals("") || loginName.equals("")){
            return "Invalid login";
        }
        String regex = "^[a-zA-Z0-9]+$";
        if (!loginPw.matches(regex) || !loginName.matches(regex)) {
            return "Invalid login";
        }
        return "ok";
    }

    //method that validates if a string is a valid email address.
    private boolean isValidEmailAddress(String email) {
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
