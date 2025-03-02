package com.example.nbody.engine;
import com.example.nbody.model.Body;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NBodySimulationTest {

    private NBodySimulation simulation;
    private List<Body> bodies ;

    @BeforeEach
    void setUp() {
        simulation = new NBodySimulation();
        bodies = new ArrayList<>();
    }

    /** Test 1 fonction step: Vérifier que la fonction ne plante pas avec une liste vide **/
    @Test
    void testStepWithEmptyList() {
        assertDoesNotThrow(() -> simulation.step(bodies, 1.0),
                "La fonction ne doit pas planter avec une liste vide");
    }

    /** Test 2 fonction step: Vérifier que pour un seul corps, sa position ne change pas **/
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

    /** Test 3 fonction step: Vérifier que deux corps identiques mais distants s'attirent mutuellement **/
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

    /** Test 4 fonction step: Vérifier que trois corps forment une interaction cohérente **/
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

    /** Test 5 fonction step: Vérifier que les positions changent correctement **/
    @Test
    void testPositionUpdate() {
        Body b1 = new Body(0, 0, 1, 1, 10.0); // Se déplace déjà
        bodies.add(b1);

        double dt = 1.0;
        simulation.step(bodies, dt);

        assertEquals(1.0, b1.getX(), 1e-6, "X doit augmenter avec la vitesse");
        assertEquals(1.0, b1.getY(), 1e-6, "Y doit augmenter avec la vitesse");
    }

    /** Test 1 fonction add: Ajouter un body valide à la liste **/
    @Test
    void testAddValidBody() {
        Body body = new Body(0, 0, 1, 1, 10.0);
        simulation.addBody(bodies, body);

        assertEquals(1, bodies.size(), "La liste doit contenir 1 élément");
        assertSame(body, bodies.get(0), "Le body ajouté doit être celui fourni");
    }

    /** Test 2 fonction add : Ne doit pas ajouter un body si la liste est null **/
    @Test
    void testAddBodyToNullList() {
        Body body = new Body(0, 0, 1, 1, 10.0);

        assertThrows(IllegalArgumentException.class, () -> simulation.addBody(null, body),
                "Ajouter à une liste null doit lever une exception");
    }

    /** Test 3 fonction add: Ne doit pas ajouter un body null **/
    @Test
    void testAddNullBody() {
        assertThrows(IllegalArgumentException.class, () -> simulation.addBody(bodies, null),
                "Ajouter un body null doit lever une exception");
    }

    /** Test 4 fonction add : Ne doit pas ajouter un body déjà présent dans la liste **/
    @Test
    void testAddDuplicateBody() {
        Body body = new Body(0, 0, 1, 1, 10.0);
        bodies.add(body);

        assertThrows(IllegalArgumentException.class, () -> simulation.addBody(bodies, body),
                "Ne doit pas ajouter un body déjà présent dans la liste");
    }

    /** Test 5 fonction add : Ajouter plusieurs bodies distincts **/
    @Test
    void testAddMultipleBodies() {
        Body body1 = new Body(0, 0, 1, 1, 10.0);
        Body body2 = new Body(1, 1, -1, -1, 20.0);

        simulation.addBody(bodies, body1);
        simulation.addBody(bodies, body2);

        assertEquals(2, bodies.size(), "La liste doit contenir 2 éléments");
        assertSame(body1, bodies.get(0), "Le premier élément doit être body1");
        assertSame(body2, bodies.get(1), "Le second élément doit être body2");
    }

}