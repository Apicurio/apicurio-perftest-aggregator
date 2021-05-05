package io.apicurio.perftest.agg.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/aggregator")
public interface AggregatorResource {

    @Path("/commands/aggregate")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public void aggregate() throws Exception;
    
    @Path("/workers/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> workers() throws Exception;

    @Path("/workers/{workerId}/start")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public void workerStart(@PathParam("workerId") String workerId) throws Exception;

    @Path("/workers/{workerId}/stop")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public void workerStop(@PathParam("workerId") String workerId) throws Exception;

    @Path("/logs/{logName}")
    @POST
    @Consumes({MediaType.TEXT_PLAIN, "application/zip"})
    public void uploadLog(@PathParam("logName") String logName, InputStream data) throws IOException;

    @Path("/info")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String info();
}