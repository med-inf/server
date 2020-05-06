package pl.edu.agh.mi.server.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class LeaveSquare extends Event {
    private UUID squareId;

    public LeaveSquare(UUID userId, LocalDateTime time, UUID squareId) {
        super(userId, time);
        this.squareId = squareId;
    }

    public UUID getSquareId() {
        return squareId;
    }
}
