package pl.edu.agh.mi.server.event;

import java.time.Instant;
import java.util.UUID;

public abstract class Event {
    private UUID userId;
    private Instant time;

    protected Event(UUID userId, Instant time) {
        this.userId = userId;
        this.time = time;
    }

    public UUID getUserId() {
        return userId;
    }

    public Instant getTime() {
        return time;
    }
}
