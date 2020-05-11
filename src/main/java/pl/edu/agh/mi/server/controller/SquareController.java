package pl.edu.agh.mi.server.controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.mi.server.data.Square;
import pl.edu.agh.mi.server.data.SquareMap;
import pl.edu.agh.mi.server.event.GetSquare;
import pl.edu.agh.mi.server.event.LeaveSquare;

@RestController
public class SquareController {
    private SquareMap squareMap;

    private SquareController() {
        this.squareMap = new SquareMap();
    }

    @PostMapping("/getSquare")
    public Square getSquare(@RequestBody GetSquare event) {
        squareMap.update(event);
        return squareMap.getSquare(event.getPosition());
    }

    @PutMapping("/leaveSquare")
    public void leaveSquare(@RequestBody LeaveSquare event) {
        squareMap.update(event);
    }

}
