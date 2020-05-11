package pl.edu.agh.mi.server.event;

import pl.edu.agh.mi.server.data.Position;

import java.time.Instant;
import java.util.UUID;

public class LeaveSquare extends Event {
    private final Position squareCenter;

    public LeaveSquare(UUID userId, long time, Position squareCenter) {
        super(userId, Instant.ofEpochMilli(time));
        this.squareCenter = squareCenter;
    }

    public Position getSquareCenter() {
        return squareCenter;
    }
}
