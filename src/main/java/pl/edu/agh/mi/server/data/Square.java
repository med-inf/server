package pl.edu.agh.mi.server.data;

import java.util.UUID;

public class Square {
    private UUID id;
    private Position center;
    private double edgeLen;

    public Square(Position center, double edgeLen) {
        this.id = UUID.randomUUID();
        this.center = center;
        this.edgeLen = edgeLen;
    }

    public UUID getId() {
        return id;
    }

    public Position getCenter() {
        return center;
    }

    public double getEdgeLen() {
        return edgeLen;
    }
}
