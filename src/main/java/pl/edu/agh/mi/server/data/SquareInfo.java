package pl.edu.agh.mi.server.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SquareInfo {
    private final Position center;
    private final double edgeLen;

    @JsonCreator
    public SquareInfo(@JsonProperty("center") Position center,
                      @JsonProperty("edgeLen") double edgeLen) {
        this.center = center;
        this.edgeLen = edgeLen;
    }

    public Position getCenter() {
        return center;
    }

    public double getEdgeLen() {
        return edgeLen;
    }
}
