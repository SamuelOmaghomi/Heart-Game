/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.omag0003.heartbeat.service;

import cst8218.omag0003.heartbeat.entity.Heart;
import java.util.List;
import java.util.Objects;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class contains all the methods of the RESTful api
 * @author samue
 */
@Stateless
@RolesAllowed({"Admin", "ApiGroup"})
@Path("cst8218.omag0003.heartbeat.entity.heart")
public class HeartFacadeREST extends AbstractFacade<Heart> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public HeartFacadeREST() {
        super(Heart.class);
    }
    
    /**
     * This method updates the values of an existing Heart depending on the id passed in the body
     * or it creates a new Heart if is is null
     * @param entity Post Body
     * @return Response
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createWithoutID(Heart entity) {  
        if(entity != null){ //checks if entity is empty
            if(entity.getId() != null){ //checks if id of entity is not null
                if(exists(entity.getId())){ //checks if there is an existing heart withthe Id passes
                    Heart temp = entity;    //temporary variable for new heart
                    entity = super.find(entity.getId()); //gets the heart from the database
                    entity.updates(temp); //updates the herat with the new values
                    super.edit(entity); //saves the changes to the database
                    return Response.status(Response.Status.ACCEPTED).build();
                }else{
                   return Response.status(Response.Status.BAD_REQUEST).entity("this is the message").build();
                }
            }else{  //if id is null
                super.create(entity); //creates a new Heart 
                return Response.status(Response.Status.ACCEPTED).entity(entity).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
  
    }
    
    /**
     * This method updates the values of an existing Heart depending on the id passed in the request header
     * @param id Id in the request header
     * @param entity Body of the request
     * @return Response
     */
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createWithID(@PathParam("id") Long id, Heart entity) {  
        if(entity != null){ //if the body is not empty
            if(exists(id) && id.equals(entity.getId())){ //if a heart with the id is in the database
                Heart temp = entity; //temporary variable for new heart
                entity = super.find(id); //gets the heart from the database
                entity.updates(temp); //updates the herat with the new values
                super.edit(entity); //saves the changes to the database
                return Response.status(Response.Status.ACCEPTED).entity(entity).build();
            }
        }   
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    
    /**
     * This method replaces the data of an existing heart with the data provided in the body
     * @param id Id in the request header
     * @param entity Body of the request
     * @return Response
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Heart entity) {
        if(entity != null){
            if(entity.getId() != null){
                if(exists(id) && id.equals(entity.getId())){    //if id exists and matches the id in the body
                    super.edit(Heart.setDefaultValues(entity)); //resets the values of the heart
                    return Response.status(Response.Status.ACCEPTED).entity(entity).build();
                }
            }
        } 
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Heart find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Heart> findAll() {
        return super.findAll();
    }


    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Heart> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}