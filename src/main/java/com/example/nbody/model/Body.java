package com.example.nbody.model;

import lombok.Data;

@Data
public class Body {
    private double x, y, vx, vy, mass;

    public Body(double x, double y, double vx, double vy, double mass) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
    }
}
