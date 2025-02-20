package com.example.nbody.model;

import lombok.Data;

@Data
public class Body {
    //  x et y : representent les coordonnées du body (l'entité)
    //  vx et vy : représentent les composantes de la vitesse en fonction des axes X et Y dans un espace 2D.
    //  Cela indique dans quelle direction et à quelle vitesse l'entité se déplace.
    //  mass : est la masse de l'entité.
    private double x, y, vx, vy, mass;

    //Initialisation
    public Body(double x, double y, double vx, double vy, double mass) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
    }
}
