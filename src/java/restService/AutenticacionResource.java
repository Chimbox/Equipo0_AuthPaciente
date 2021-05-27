package restService;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import equipo0_dominio.Paciente;
import equipo0_dominio.Usuario;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import negocios.FactoryNegocios;
import negocios.INegociosAuthPac;

/**
 * REST Web Service
 *
 * @author Alfonso Felix
 */
@Path("auth")
public class AutenticacionResource {

    INegociosAuthPac negocios;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AutenticacionResource
     */
    public AutenticacionResource() {
        negocios = FactoryNegocios.getFachadaAuthPac();
    }

    @POST
    @Path("validartoken")
    @Produces(MediaType.TEXT_PLAIN)
    public Response postValidarToken(String entrada) {
        try {
            return Response.status(200).entity("1").build();
        } catch (Exception ex) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }

    @POST
    @Path("obtenerdatos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postObtenerDatos(String json) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            Usuario usuario = gson.fromJson(json, Usuario.class);

            Paciente paciente = negocios.obtenerDatosPaciente(usuario.getUsername());

            if (paciente != null) {
                return Response.status(200).entity(gson.toJson(paciente)).build();
            }
            return Response.status(404).entity("{0}").build();
        } catch (Exception e) {
            return Response.status(404).entity("{0}").build();
        }
    }

    @POST
    @Path("actualizartokenfirebase")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postActualizaTokenFirebase(String json) {
        try {
            System.out.println(json);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            
            Paciente usuario = gson.fromJson(json, Paciente.class);

            return Response.status(200).entity(gson.toJson(negocios.actualizarTokenFirebase(usuario.getUsername(), usuario.getTokenFirebase()))).build();

        } catch (Exception e) {
            return Response.status(404).entity("{0}").build();
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postLogin(String json) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            Usuario usuario = gson.fromJson(json, Usuario.class);

            System.out.println(usuario);

            String token = negocios.iniciarSesion(usuario.getUsername(), usuario.getPassword());

            if (token == null) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            } else {
                return String.format("{\"token\":\"%s\"}", token);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }
}
