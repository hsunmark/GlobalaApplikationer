package slf4jLog;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Ivan on 01/03/16.
 */
public class Logg {
    private static final Logger slf4jLogger = LoggerFactory.getLogger(Logg.class);

    public Logg(){
        System.out.println("penis");
    };

    /**
     * Print hello in log.
     *
     * @param username
     * @param password
     */
    public void logInvalidLogInAttempt(String username, String password) {
        slf4jLogger.info("Failed login attempt for username:  {}", username);
    }

}
