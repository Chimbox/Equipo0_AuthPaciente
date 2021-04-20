package restService;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Alfonso Felix
 */
@Path("auth")
public class AutenticacionResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AutenticacionResource
     */
    public AutenticacionResource() {
    }

    /**
     * Retrieves representation of an instance of restService.AutenticacionResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        String json="{"
                + "\"estado\":\"conectado\""
                + "}";
        return json;
    }

    /**
     * PUT method for updating or creating an instance of AutenticacionResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
