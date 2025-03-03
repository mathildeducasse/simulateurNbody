package com.example.nbody.websockets;

import com.example.nbody.engine.NBodySimulation;
import com.example.nbody.model.Body;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
@ServerEndpoint("/nbody")
public class NBodyWebsocket {

    private final NBodySimulation simulation = new NBodySimulation();
    private final List<Body> bodies = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Client connected: " + session.getId());

        Thread simulationThread = new Thread(() -> {
            try {
                while (session.isOpen()) {
                    simulation.step(bodies, 1.0);
                    session.getBasicRemote().sendText(toJson(bodies));
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        simulationThread.start();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (message.startsWith("ADD:")) {
            String[] parts = message.substring(4).split(",");
            try {
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                double vx = Double.parseDouble(parts[2]);
                double vy = Double.parseDouble(parts[3]);
                double mass = Double.parseDouble(parts[4]);
                Body newBody = new Body(x, y, vx, vy, mass);
                simulation.addBody(bodies, newBody);
                System.out.println("Body added: " + newBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Client disconnected: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error on session " + session.getId() + ": " + throwable.getMessage());
    }

    private String toJson(List<Body> bodies) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < bodies.size(); i++) {
            Body b = bodies.get(i);
            json.append(String.format("{\"x\":%f,\"y\":%f,\"vx\":%f,\"vy\":%f,\"mass\":%f}",
                    b.getX(), b.getY(), b.getVx(), b.getVy(), b.getMass()));
            if (i < bodies.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }
}
