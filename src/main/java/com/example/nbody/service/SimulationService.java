package com.example.nbody.service;

import com.example.nbody.engine.NBodySimulation;
import com.example.nbody.model.Body;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulationService {
    private final NBodySimulation simulation = new NBodySimulation();
    private List<Body> bodies = new ArrayList<>();

    public SimulationService() {
        initializeSimulation();
    }

    public void initializeSimulation() {
        bodies.add(new Body(0, 0, 0, 0, 1.989e30)); // Soleil
        bodies.add(new Body(1.496e11, 0, 0, 29783, 5.972e24)); // Terre
    }

    public List<Body> stepSimulation(double dt) {
        simulation.step(bodies, dt);
        return bodies;
    }
}
