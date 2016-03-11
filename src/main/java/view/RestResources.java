package view;


import com.google.gson.Gson;
import controller.RecruitmentController;
import controller.RestController;
import model.PersonEntity;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Exposes methods of the recruitment application for HTTP
 * clients. Post methods require form parameters
 */
@Path("/api")
public class RestResources {
    Gson gson = new Gson();

    @EJB
    private RestController controller;

    /**
     * Attempts to login with given parameters. 1 is returned on
     * success, 0 is returned on failure
     * @param userid
     * @param password
     * @return 1 for logged in, 0 for failure
     */
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@FormParam("userid") String userid, @FormParam("password") String password) {
        boolean status = controller.login(userid, password);
        if(status) return "1";
        else return "0";
    }

    /**
     * Returns a JSON String contaning all applicants including their
     * related information role, availability
     * @return JSON String of persons
     */
    @GET
    @Path("/getApplications")
    @Produces(MediaType.TEXT_PLAIN)
    public String getApplications() {
        List<PersonEntity> resultSet = controller.getApplicants();
        String jsonResultSet = gson.toJson(resultSet);
        return jsonResultSet;
    }
}









