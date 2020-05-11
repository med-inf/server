package pl.edu.agh.mi.server.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Square {
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
    @JsonIgnore
    private final Map<UUID, List<TimeRange>> people = new HashMap<>();

    public Square(Position center, double edgeLen) {
        this.center = center;
        this.edgeLen = edgeLen;
        top = center.getLat() + 0.5 * edgeLen;
        left = center.getLon() - 0.5 * edgeLen;
        bot = center.getLat() - 0.5 * edgeLen;
        right = center.getLon() + 0.5 * edgeLen;
    }

    public Position getCenter() {
        return center;
    }

    public double getEdgeLen() {
        return edgeLen;
    }

    public Map<UUID, List<TimeRange>> getPeople() {
        return people;
    }

    public boolean isInSquare(Position position) {
        double lat = position.getLat();
        double lon = position.getLon();
        return  top >= lat && lat >= bot && right >= lon && lon >= left;
    }
}
