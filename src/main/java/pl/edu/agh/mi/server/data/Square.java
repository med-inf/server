package pl.edu.agh.mi.server.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Square {
    private final SquareInfo info;
    private final Map<UUID, List<TimeRange>> people;

    @JsonCreator
    private Square(@JsonProperty("info") SquareInfo info,
                   @JsonProperty("people") Map<UUID, List<TimeRange>> people) {
        this.info = info;
        this.people = people;
    }

    public Square(SquareInfo info) {
        this.info = info;
        people = new HashMap<>();
    }

    public SquareInfo getInfo() {
        return info;
    }

    public Map<UUID, List<TimeRange>> getPeople() {
        return people;
    }
}
