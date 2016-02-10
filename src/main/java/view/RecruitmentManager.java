package view;

import model.RegisterDTO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.logging.Logger;


@ManagedBean(name = "recruitmentManager")
@SessionScoped
public class RecruitmentManager implements Serializable {

  //  @EJB
 //   private RecruitmentController controller;
    private String username;
    private String password;
    private String password2;
    private String firstname;
    private String lastname;
    private String role;
    private String ssn;
    private String email;
    private Logger log = Logger.getLogger(RecruitmentManager.class.getName());

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
        String message ="";// validateLoginParameters();
      /*  if(message =="ok"){
            controller.login(username, password);
        }*/
        System.out.println("Login!!!");
        return message;
    }

    public String register(){
       String message = validateRegisterParameters() ;
      // if (validateRegisterParameters()!= "ok") {
        //   controller.register(new RegisterDTO(role, firstname, lastname, ssn, email, username, password));
       //}
        return message;
    }

    private String validateRegisterParameters() {
        if(username==null || password==null || password2== null || firstname==null || lastname==null || role==null
                || ssn==null || email==null){
            return "You have not filled all the fields.";
        }
        if(!password.equals(password2)){
            return "Passwords does not match";
        }
        String regex = "^[a-zA-Z0-9]+$";
        String emailregex = "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";
        String ssnregex = "^[0-9]+$";
        if(!username.matches(regex) || !password.matches(regex) || !firstname.matches(regex)
                || !lastname.matches(regex)){
            return "You are using invalid characters.. (a-z , 0-9 allowed)";
        }
        if(!email.matches(emailregex)){
            return "Your email includes invalid characters";
        }

        if(!ssn.matches(ssnregex)){
            return "Your ssn should be 10 numbers";
        }
        return "ok";
    }

    private String validateLoginParameters() {
        //TODO validera alla andra parametrar
        if(password==null || username==null){
            System.out.println("Please enter a valid username and a password");
            return "Please enter a valid username and a password";
        }
        String regex = "^[a-zA-Z0-9]+$";
        if (!password.matches(regex) || !username.matches(regex)) {
            System.out.println("You are only allowed to use characters and/or numbers");
            return "You are only allowed to use characters and/or numbers";
        }
        System.out.println("ok");
        return "ok";
    }
}
