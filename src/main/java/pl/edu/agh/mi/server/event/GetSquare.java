package pl.edu.agh.mi.server.event;

import pl.edu.agh.mi.server.data.Position;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetSquare extends Event {
    private Position position;
    private UUID prevSquareId;

    public GetSquare(UUID userId, Position position, LocalDateTime time, UUID prevSquareId) {
        super(userId, time);
        this.position = position;
        this.prevSquareId = prevSquareId;
    }

    public Position getPosition() {
        return position;
    }

    public UUID getPrevSquareId() {
        return prevSquareId;
    }
}
