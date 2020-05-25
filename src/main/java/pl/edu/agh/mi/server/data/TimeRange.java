package pl.edu.agh.mi.server.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class TimeRange {
    private final Instant start;
    private Instant end;

    public TimeRange(Instant start) {
        this.start = start;
    }

    @JsonCreator
    public TimeRange(@JsonProperty("start") Instant start,
                     @JsonProperty("end") Instant end) {
        this.start = start;
        this.end = end;
    }

    public boolean isOverlaping(TimeRange other) {
        Instant otherStart = other.getStart();
        Instant otherEnd = other.getEnd();
        if (end == null) {
            return otherStart.compareTo(start) >= 0;
        }
        return otherStart.compareTo(start) >= 0 && otherStart.compareTo(end) <= 0
                || otherEnd.compareTo(start) >= 0 && otherEnd.compareTo(end) <= 0
                || otherStart.compareTo(start) <= 0 && otherEnd.compareTo(end) >= 0;

    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }
}
