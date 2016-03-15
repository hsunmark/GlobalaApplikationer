package view;

import controller.RecruitmentController;
import model.RegisterDTO;
import org.primefaces.context.RequestContext;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.security.auth.Subject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A Manager. Handles all interactions from the client interface.
 */
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
    private boolean loginSuccess;
    private boolean applicant;
    private boolean recruit;
    private Locale currentLocale;
    private ResourceBundle labels;
    private Date fromDate;
    private Date toDate;


    private String NAME_REGEX = "^[a-zA-Z]+$";
    private String USER_REGEX = "^[a-zA-Z0-9]+$";
    private String SSN_REGEX = "^[0-9]+$";
    private String PW_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public boolean getLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSucces) {
        this.loginSuccess = loginSuccess;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getLoginPw() {
        return loginPw;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = labels.getString(message);
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

    public Locale getCurrentLocale() { return currentLocale; }

    public boolean isApplicant() {
        return applicant;
    }

    public void setApplicant(boolean applicant) {
        this.applicant = applicant;
    }

    public boolean isRecruit() {
        return recruit;
    }

    public void setRecruit(boolean recruit) {
        this.recruit = recruit;
    }

    public boolean getError() {
        return error == null;
    }

    public Exception getException() {
        return error;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    private void handleException(Exception e) {
        e.printStackTrace(System.err);
        error = e;
    }

    /**
     * Sets the language chosen by user.
     *
     * @return returns an empty string due to jsf22bugfix
     */
    public String setCurrentLocale(String lang) {
        try {
            error = null;
            if (lang.equals("swe")) {
                currentLocale = new Locale("sv", "SE");
                labels = ResourceBundle.getBundle("labelsbundle", currentLocale);

            }
            if (lang.equals("eng")) {
                currentLocale = new Locale("en", "US");
                labels = ResourceBundle.getBundle("labelsbundle", currentLocale);
            }
        } catch (Exception e) {
            handleException(e);
        }
        return "";
    }

    /**
     * Method used to log out a user.
     *
     * @return returns an empty string due to jsf22bugfix
     */
    public String logOut() {
        try {

            error = null;
            //"logOut?faces-redirect=true"
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            HttpSession session = (HttpSession) externalContext.getSession(false);

//            Subject subject = SecurityContext.getCurrent().getSubject();
//            Set<Principal> principals = subject.getPrincipals();
//            System.out.println("**********************" + principals.size());
//            for (Principal i : principals) {
//                System.out.println("**********************" + i.getName() + "**" + i.getName());
//            }
//
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("logOut.xhtml");
            requestDispatcher.forward(request, response);


            //requestDispatcher.include(request, response);
            //session.invalidate();

        } catch (Exception e) {
            handleException(e);
        }
        return "";
    }

    /**
     * Method used to validate an user login attempt
     *
     * @return returns an empty string due to jsf22bugfix
     */
    public String login() {
        try {
            error = null;
            String msg = validateLoginParameters();
            if (msg.equals("ok")) {
                if (controller.login(loginName, loginPw, this)) {
                    message = null;
                    loginSuccess = true;
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return "";
    }


    /**
     * Validates all the fields that a user has typed in and
     * if the fields are validated method calls controller.register(RegisterDTO dto)
     * to continue registration.
     * Otherwise an errormessage is set by this method
     *
     * @return returns an empty string due to jsf22bugfix
     */
    public String register() {
        try {
            error = null;
            String msg = validateRegisterParameters();
            if (msg.equals("ok")) {
                loginSuccess = controller.register(new RegisterDTO(role, firstname, lastname, ssn, email, username, password), this);
                message = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return "";
    }

    /**
     * Sets a controller for the recruitmentManager
     * @param controller
     */
    public void setRecruitmentController(RecruitmentController controller) {
        this.controller = controller;
    }


    //method that validates parameters for registration.
    private String validateRegisterParameters() {
        if (username.equals("")
                || password.equals("")
                || password2.equals("")
                || firstname.equals("")
                || lastname.equals("")
                || role.equals("")
                || ssn.equals("")
                || email.equals("")) {
            setMessage("RegisterMessage1");
            return "You have not filled all the fields.";
        }
        if (!password.equals(password2)) {
            setMessage("RegisterMessage2");
            return "Passwords does not match";
        }
        if (password.length() < 6) {
            setMessage("RegisterMessage3");
            return "Password must be atleast 6 charachters long";
        }

        if (!username.matches(USER_REGEX)
                || !password.matches(PW_REGEX)
                || !firstname.matches(NAME_REGEX)
                || !lastname.matches(NAME_REGEX)) {
            setMessage("RegisterMessage4");
            return "You are using invalid characters.. " +
                    "(aA-zZ allowed for names and aA-zZ + 0-9 allowed for username and password)";
        }
        if (!isValidEmailAddress(email)) {
            setMessage("RegisterMessage5");
            return "Your email is not a valid email address";
        }

        if ((!ssn.matches(SSN_REGEX) || (ssn.length() != 10))) {
            setMessage("RegisterMessage6");
            return "Your social security number should be 10 numbers";
        }
        return "ok";
    }

    //method that validates parameters for login
    private String validateLoginParameters() {
        if (loginPw.equals("") || loginName.equals("")) {
            setMessage("LoginMessage3");
            return "Enter login credentials";
        }
        if (!loginPw.matches(PW_REGEX) || !loginName.matches(USER_REGEX)) {
            setMessage("LoginMessage1");
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

    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }
}