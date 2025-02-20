package com.example.nbody.engine;
import com.example.nbody.model.Body;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NBodySimulationTest {

    private NBodySimulation simulation;
    private final List<Body> bodies = new ArrayList<>();
    /** Test 1 : Vérifier que la fonction ne plante pas avec une liste vide **/
    @Test
    void testStepWithEmptyList() {
        assertDoesNotThrow(() -> simulation.step(bodies, 1.0),
                "La fonction ne doit pas planter avec une liste vide");
    }

    /** Test 2 : Vérifier que pour un seul corps, sa position ne change pas **/
    @Test
    void testStepWithOneBody() {
        Body b = new Body(0, 0, 0, 0, 5.0); // Position (0,0), Vitesse (0,0), Masse 5
        bodies.add(b);

        simulation.step(bodies, 1.0);

        assertEquals(0, b.getX(), 1e-6, "La position X ne doit pas changer");
        assertEquals(0, b.getY(), 1e-6, "La position Y ne doit pas changer");
        assertEquals(0, b.getVx(), 1e-6, "La vitesse Vx ne doit pas changer");
        assertEquals(0, b.getVy(), 1e-6, "La vitesse Vy ne doit pas changer");
    }

    /** Test 3 : Vérifier que deux corps identiques mais distants s'attirent mutuellement **/
    @Test
    void testStepWithTwoBodies() {
        Body b1 = new Body(-1, 0, 0, 0, 5.0);
        Body b2 = new Body(1, 0, 0, 0, 5.0);
        bodies.add(b1);
        bodies.add(b2);

        double dt = 1.0;
        simulation.step(bodies, dt);

        assertTrue(b1.getVx() > 0, "Le corps b1 doit être attiré vers b2 (vx positif)");
        assertTrue(b2.getVx() < 0, "Le corps b2 doit être attiré vers b1 (vx négatif)");
    }

    /** Test 4 : Vérifier que trois corps forment une interaction cohérente **/
    @Test
    void testStepWithThreeBodies() {
        Body b1 = new Body(-1, 0, 0, 0, 5.0);
        Body b2 = new Body(1, 0, 0, 0, 5.0);
        Body b3 = new Body(0, 1, 0, 0, 5.0);
        bodies.add(b1);
        bodies.add(b2);
        bodies.add(b3);

        double dt = 1.0;
        simulation.step(bodies, dt);

        assertTrue(b1.getVx() > 0, "b1 doit être attiré vers le centre");
        assertTrue(b2.getVx() < 0, "b2 doit être attiré vers le centre");
        assertTrue(b3.getVy() < 0, "b3 doit être attiré vers le centre");
    }

    /** Test 5 : Vérifier que les positions changent correctement **/
    @Test
    void testPositionUpdate() {
        Body b1 = new Body(0, 0, 1, 1, 10.0); // Se déplace déjà
        bodies.add(b1);

        double dt = 1.0;
        simulation.step(bodies, dt);

        assertEquals(1.0, b1.getX(), 1e-6, "X doit augmenter avec la vitesse");
        assertEquals(1.0, b1.getY(), 1e-6, "Y doit augmenter avec la vitesse");
    }
}