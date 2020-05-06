package pl.edu.agh.mi.server.data;

import pl.edu.agh.mi.server.event.GetSquare;
import pl.edu.agh.mi.server.event.LeaveSquare;

public class SquareMap {

    public Square getSquare(Position position) {
        return new Square(new Position(0, 0), 0);
    }

    public void update(GetSquare event) {
        System.out.println("updating map with: " + event);
    }

    public void update(LeaveSquare event) {
        System.out.println("updating map with: " + event);
    }
}
