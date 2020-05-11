package pl.edu.agh.mi.server.data;

import java.time.Instant;

public class TimeRange {
    private Instant start;
    private Instant end;

    public TimeRange(Instant start) {
        this.start = start;
    }

    public TimeRange(Instant start, Instant end) {
        this.start = start;
        this.end = end;
    }

    public boolean isOverlaping(TimeRange other) {
        Instant otherStart = other.getStart();
        Instant otherEnd = other.getEnd();
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

    public void setStart(Instant start) {
        this.start = start;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }
}
