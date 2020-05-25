package pl.edu.agh.mi.server.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    private double lat;
    private double lon;

    @JsonCreator
    public Position(@JsonProperty("lat") double lat,
                    @JsonProperty("lon") double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
