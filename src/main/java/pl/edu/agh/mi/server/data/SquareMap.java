package pl.edu.agh.mi.server.data;

import pl.edu.agh.mi.server.event.GetSquare;
import pl.edu.agh.mi.server.event.LeaveSquare;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SquareMap {

    private final Map<UUID, Square> squares = new HashMap<>();
    private final double top = 50.127254;
    private final double left = 19.790282;
    private final double bot = 49.968808;
    private final double right = 20.218548;

    public SquareMap() {
        initMap(squares, 0.0001);
    }

    public Square getSquare(Position position) {
        return new Square(new Position(0, 0), 0);
    }

    public void update(GetSquare event) {
        System.out.println("updating map with: " + event);
    }

    public void update(LeaveSquare event) {
        System.out.println("updating map with: " + event);
    }

    private void initMap(Map<UUID, Square> map, double edgeLen) {
        double x_start = left + 0.5 * edgeLen;
        double y_start = top - 0.5 * edgeLen;
        for (double y = y_start; y > bot; y-=edgeLen) {
            for (double x = x_start; x < right; x+=edgeLen) {
                UUID id = UUID.randomUUID();
                map.put(id, new Square(id, new Position(y, x), edgeLen));
            }
        }
    }
}
