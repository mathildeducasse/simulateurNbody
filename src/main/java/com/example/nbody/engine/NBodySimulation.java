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
        for (Body b1 : bodies) {
            double ax = 0, ay = 0;
            for (Body b2 : bodies) {
                if (b1 != b2) {
                    double dx = b2.getX() - b1.getX();
                    double dy = b2.getY() - b1.getY();
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    double force = G * b1.getMass() * b2.getMass() / (dist * dist);
                    ax += force * dx / (dist * b1.getMass());
                    ay += force * dy / (dist * b1.getMass());
                }
            }
            b1.setVx(b1.getVx() + ax * dt);
            b1.setVy(b1.getVy() + ay * dt);
        }

        for (Body b : bodies) {
            b.setX(b.getX() + b.getVx() * dt);
            b.setY(b.getY() + b.getVy() * dt);
        }
    }
}
