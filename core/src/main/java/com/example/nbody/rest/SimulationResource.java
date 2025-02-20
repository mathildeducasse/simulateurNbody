package com.example.nbody.rest;

import com.example.nbody.model.Body;
import com.example.nbody.service.SimulationService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/simulation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulationResource {

    private final SimulationService simulationService;

    public SimulationResource(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @GET
    @Path("/step")
    public List<Body> step(@QueryParam("dt") double dt) {
        return simulationService.stepSimulation(dt);
    }
}
