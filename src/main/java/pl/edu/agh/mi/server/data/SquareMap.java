package pl.edu.agh.mi.server.data;

import pl.edu.agh.mi.server.event.GetSquare;
import pl.edu.agh.mi.server.event.LeaveSquare;

import java.time.Instant;
import java.util.*;

import static java.lang.Math.floor;


public class SquareMap {
    private static final double TOP = 50.127254;
    private static final double LEFT = 19.790282;
    private static final double BOT = 49.968808;
    private static final double RIGHT = 20.218548;
    private static final double EDGE_LEN = 0.0001;

    private final List<List<Square>> squares = new ArrayList<>();

    public SquareMap() {
        initMap(squares, EDGE_LEN);
    }

    public Square getSquare(Position position) {
        int y = (int) floor((TOP - position.getLat()) / EDGE_LEN);
        int x = (int) floor((position.getLon() - LEFT) / EDGE_LEN);
        return squares.get(y).get(x);
    }

    public void update(GetSquare event) {
        UUID userId = event.getUserId();
        Instant time = event.getTime();
        event.getPrevSquareCenter().ifPresent(prevSquareCenter -> 
                updateLeavingTime(prevSquareCenter, userId, time)
        );
        Square square = getSquare(event.getPosition());
        square.getPeople().putIfAbsent(userId, new ArrayList<>());
        List<TimeRange> timeRanges = square.getPeople().get(userId);
        timeRanges.add(new TimeRange(time));
    }

    public void update(LeaveSquare event) {
        updateLeavingTime(event.getSquareCenter(), event.getUserId(), event.getTime());
    }

    /**
     * Updates square leaving time
     */
    private void updateLeavingTime(Position prevSquareCenter, UUID userId, Instant time) {
        Square square = getSquare(prevSquareCenter);
        if (square.getPeople().containsKey(userId)) {
            List<TimeRange> timeRanges = square.getPeople().get(userId);
            timeRanges.get(timeRanges.size() - 1).setEnd(time);
        }
    }
    
    private void initMap(List<List<Square>> grid, double edgeLen) {
        double xStart = LEFT + 0.5 * edgeLen;
        double yStart = TOP - 0.5 * edgeLen;
        for (double y = yStart; y > BOT; y-=edgeLen) {
            List<Square> row = new ArrayList<>();
            for (double x = xStart; x < RIGHT; x+=edgeLen) {
                row.add(new Square(new Position(y, x), edgeLen));
            }
            grid.add(row);
        }
    }
}
