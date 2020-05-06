package pl.edu.agh.mi.server.event;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Event {
    private UUID userId;
    private LocalDateTime time;

    protected Event(UUID userId, LocalDateTime time) {
        this.userId = userId;
        this.time = time;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
