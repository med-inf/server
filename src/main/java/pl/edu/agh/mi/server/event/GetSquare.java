package pl.edu.agh.mi.server.event;

import pl.edu.agh.mi.server.data.Position;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class GetSquare extends Event {
    private Position position;
    private Position prevSquareCenter;

    public GetSquare(UUID userId, Position position, long time, Position prevSquareCenter) {
        super(userId, Instant.ofEpochMilli(time));
        this.position = position;
        this.prevSquareCenter = prevSquareCenter;
    }

    public Position getPosition() {
        return position;
    }

    public Optional<Position> getPrevSquareCenter() {
        return Optional.ofNullable(prevSquareCenter);
    }
}
