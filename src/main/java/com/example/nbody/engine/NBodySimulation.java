package com.example.nbody.engine;

import com.example.nbody.model.Body;
import java.util.List;

public class NBodySimulation {
    //La gravité
    private static final double G = 6.67430e-11;

    //Entrée :    - Une lise de bodies (de type Body) qui sont dans notre simulation
                //- Le pas de temps (un double), Plus il est grand, plus la vitesse
    //          change rapidement, ce qui peut affecter la précision de la simulation.
    //          Il affectera aussi le temps de calcul.
    // Cette fonction permet de simuler l'évolution des corps célestes sur un petit
    // intervalle de temps (le pas de temps) en appliquant les lois de la gravité de Newton.
    // Elle doit être appelée à chaque itération d'une boucle de simulation pour faire
    // évoluer les corps dans l'espace au fil du temps.
    public void step(List<Body> bodies, double dt) {
        if (bodies == null || bodies.isEmpty() || dt <= 0) {
            return; // Rien à faire si la liste est vide ou dt invalide
        }

        // Création de listes pour stocker les nouvelles vitesses temporairement
        double[] newVx = new double[bodies.size()];
        double[] newVy = new double[bodies.size()];

        // Calcul des nouvelles vitesses en fonction des forces gravitationnelles
        for (int i = 0; i < bodies.size(); i++) {
            Body b1 = bodies.get(i);
            double ax = 0, ay = 0;

            for (int j = 0; j < bodies.size(); j++) {
                if (i != j) {
                    Body b2 = bodies.get(j);
                    double dx = b2.getX() - b1.getX();
                    double dy = b2.getY() - b1.getY();
                    double distSq = dx * dx + dy * dy;
                    double dist = Math.sqrt(distSq);

                    if (dist > 0) { // Vérification pour éviter la division par zéro
                        double force = G * b1.getMass() * b2.getMass() / distSq;
                        ax += force * dx / (dist * b1.getMass());
                        ay += force * dy / (dist * b1.getMass());
                    }
                }
            }

            // Mise à jour des vitesses temporairement
            newVx[i] = b1.getVx() + ax * dt;
            newVy[i] = b1.getVy() + ay * dt;
        }

        // Mise à jour des vitesses et des positions après le calcul des forces
        for (int i = 0; i < bodies.size(); i++) {
            Body b = bodies.get(i);
            b.setVx(newVx[i]);
            b.setVy(newVy[i]);
            b.setX(b.getX() + b.getVx() * dt);
            b.setY(b.getY() + b.getVy() * dt);
        }
    }
    //Entrée : - Un body de type Body que l'on souhaite rajouter à la simulation
    //         - Une liste de Body
    //Cette fonction permet de rajouter un body à notre liste de body.
    public void addBody(List<Body> list, Body body) {
        // Gestion des cas d'entrée invalide
        if (list == null) {
            throw new IllegalArgumentException("La liste ne peut pas être null");
        }
        if (body == null) {
            throw new IllegalArgumentException("Le body ne peut pas être null");
        }
        // Empêcher l'ajout d'un body déjà existant dans la liste
        if (list.contains(body)) {
            throw new IllegalArgumentException("Le body est déjà présent dans la liste");
        }
        list.add(body);
    }

}
