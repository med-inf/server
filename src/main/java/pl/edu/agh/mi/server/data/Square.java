package pl.edu.agh.mi.server.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Square {
    private UUID id;
    private Position center;
    private double edgeLen;

    @JsonIgnore
    private final double top;
    @JsonIgnore
    private final double left;
    @JsonIgnore
    private final double bot;
    @JsonIgnore
    private final double right;

    public Square(Position center, double edgeLen) {
        this(UUID.randomUUID(), center, edgeLen);
    }

    public Square(UUID id, Position center, double edgeLen) {
        this.id = id;
        this.center = center;
        this.edgeLen = edgeLen;
        top = center.getLat() + 0.5 * edgeLen;
        left = center.getLon() - 0.5 * edgeLen;
        bot = center.getLat() - 0.5 * edgeLen;
        right = center.getLon() + 0.5 * edgeLen;
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

    public boolean isInSquare(Position position) {
        double lat = position.getLat();
        double lon = position.getLon();
        return  top >= lat && lat >= bot && right >= lon && lon >= left;
    }
}
