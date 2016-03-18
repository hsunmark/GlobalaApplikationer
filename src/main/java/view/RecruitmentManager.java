package view;

import controller.RecruitmentController;
import model.RegisterDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
    private Exception exception;
    private boolean loginSuccess;
    private boolean applicant;
    private boolean recruit;
    private Locale currentLocale;
    private ResourceBundle labels;
    private Date fromDate;
    private Date toDate;
    private String competence;
    private BigDecimal years;
    private List<String> competenceList;
    private String msg;


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
        msg = message;
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

    public Locale getCurrentLocale() {
        return currentLocale;
    }

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

    public boolean getexception() {
        return exception == null;
    }

    public Exception getException() {
        return exception;
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

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public BigDecimal getYears() {
        return years;
    }

    public void setYears(BigDecimal years) {
        this.years = years;
    }

    public List<String> getCompetenceList() {
        return competenceList;
    }

    public void setCompetenceList() {
        competenceList = controller.getCompetenceList();
    }

    private void handleException(Exception e) {
        e.printStackTrace(System.err);
        exception = e;
    }

    @PostConstruct
    public void init() {
        currentLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
        labels = ResourceBundle.getBundle("labelsbundle", currentLocale);
    }

    /**
     * Sets the language chosen by user.
     *
     * @return returns an empty string due to jsf22bugfix
     */
    public String setCurrentLocale(String lang) {
        try {
            exception = null;
            if (lang.equals("swe")) {
                currentLocale = new Locale("sv", "SE");
                labels = ResourceBundle.getBundle("labelsbundle", currentLocale);

            }
            if (lang.equals("eng")) {
                currentLocale = new Locale("en", "US");
                labels = ResourceBundle.getBundle("labelsbundle", currentLocale);
            }
            if (loginSuccess) {
                setCompetenceList();
            }
            if (msg != null) {
                setMessage(msg);
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
            exception = null;
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) externalContext.getSession(false);
            session.invalidate();
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
            exception = null;
            if (validateLoginParameters()) {
                if (controller.login(loginName, loginPw, this)) {
                    setCompetenceList();
                    message = null;
                    msg = null;
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
     * Otherwise an exceptionmessage is set by this method
     *
     * @return returns an empty string due to jsf22bugfix
     */
    public String register() {
        try {
            exception = null;
            if (validateRegisterParameters()) {
                loginSuccess = controller.register(new RegisterDTO(role, firstname, lastname, ssn, email, username, password), this);
                setCompetenceList();
                message = null;
                msg = null;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return "";
    }

    /**
     * Sets a controller for the recruitmentManager
     *
     * @param controller
     */
    public void setRecruitmentController(RecruitmentController controller) {
        this.controller = controller;
    }


    //method that validates parameters for registration.
    private boolean validateRegisterParameters() {
        if (username.equals("")
                || password.equals("")
                || password2.equals("")
                || firstname.equals("")
                || lastname.equals("")
                || role.equals("")
                || ssn.equals("")
                || email.equals("")) {
            setMessage("RegisterMessage1");
            return false;
        }
        if (!password.equals(password2)) {
            setMessage("RegisterMessage2");
            return false;
        }
        if (password.length() < 6) {
            setMessage("RegisterMessage3");
            return false;
        }

        if (!username.matches(USER_REGEX)
                || !password.matches(PW_REGEX)
                || !firstname.matches(NAME_REGEX)
                || !lastname.matches(NAME_REGEX)) {
            setMessage("RegisterMessage4");
            return false;
        }
        if (!isValidEmailAddress(email)) {
            setMessage("RegisterMessage5");
            return false;
        }

        if ((!ssn.matches(SSN_REGEX) || (ssn.length() != 10))) {
            setMessage("RegisterMessage6");
            return false;
        }
        return true;
    }

    //method that validates parameters for login
    private boolean validateLoginParameters() {
        if (loginPw.equals("") || loginName.equals("")) {
            setMessage("LoginMessage3");
            return false;
        }
        if (!loginPw.matches(PW_REGEX) || !loginName.matches(USER_REGEX)) {
            setMessage("LoginMessage1");
            return false;
        }
        return true;
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

    /*public String addDates() {
        try {
            exception = null;
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update("form:display");
            requestContext.execute("PF('dlg').show()");
            if (controller.addDates(fromDate, toDate)) {
                //TODO set confirmation msg
            }
        } catch (Exception e) {
            handleException(e);
        }
        return "";
    }*/

    public String addCompetence() {
        try {
            exception = null;
            //TODO validate competence?
            if (controller.addCompetence(competence, years)) {
                setMessage("competenceAdded");
            } else {
                setMessage("invalidYears");
            }
        } catch (Exception e) {
            handleException(e);
        }
        return "";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}