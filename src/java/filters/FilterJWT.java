/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import negocios.FactoryNegocios;
import negocios.INegociosAuthPac;
/**
 *
 * @author Alfonso Felix
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class FilterJWT implements ContainerRequestFilter {
    
    private INegociosAuthPac negocios = FactoryNegocios.getFachadaAuthPac();
    
    @Override
    public void filter(ContainerRequestContext requestContext) {
        String metodo = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        if (path.contains("login") && metodo.equals("POST")) {

//            try {
////                String json = IOUtils.toString(requestContext.getEntityStream(), Charsets.UTF_8);
////
////                
////
////                requestContext.setEntityStream(IOUtils.toInputStream(json));
//                // String token = crearToken("alfonso");
//
//                // System.out.println(token);
//            } catch (IOException ex) {
//                Logger.getLogger(FilterJWT.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } else if ((path.contains("validartoken") && metodo.equals("POST")) || (path.contains("obtenerdatos") && metodo.equals("POST")) || (path.contains("actualizartokenfirebase")&&metodo.equals("POST"))) {
            
            String token = requestContext.getHeaderString("Autorizacion");
            if (token != null) {
                if (!negocios.validarAutenticacion(token)) {
                    throw new WebApplicationException(Response.Status.UNAUTHORIZED);
                }
            } else {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        } else {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }
}
