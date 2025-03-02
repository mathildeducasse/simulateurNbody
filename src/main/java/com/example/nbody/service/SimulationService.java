package com.example.nbody.service;

import com.example.nbody.engine.NBodySimulation;
import com.example.nbody.model.Body;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulationService {

    //La simulation
    private final NBodySimulation simulation = new NBodySimulation();

    //Une liste d'objet du modele Body. Ils correspondent aux bodies de notre simulation
    private List<Body> bodies = new ArrayList<>();

    //L'initialisation du service
    public SimulationService() {
        initializeSimulation();
    }

    //On ajoute deux bodies à notre liste
    public void initializeSimulation() {
        bodies.add(new Body(0, 0, 0, 0, 1.989e30)); // Soleil
        bodies.add(new Body(1.496e11, 0, 0, 29783, 5.972e24)); // Terre
    }

    //Entrée : Le pas de temps (double)
    //  Cette fonction appelle la fonction step de notre simulation avec
    // en parametre notre liste bodies et le pas de temps entré en paramètre
    // de la fonction.
    //Sortie : La liste de bodies avec leur x,y,zx et zy mis à jour. Cela représente
    // leur évolution dans l'espace au fil du temps.
    public List<Body> stepSimulation(double dt) {
        simulation.step(bodies, dt);
        return bodies;
    }

    //Entrée : Un body de type Body que l'on souhaite rajouter à la simulation
    //Cette fonction permet de rajouter un body à notre liste de body.
    public void addBody(Body body) {
        simulation.addBody(bodies, body);
    }
}
