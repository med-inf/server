package pl.edu.agh.mi.server.controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.mi.server.data.SquareInfo;
import pl.edu.agh.mi.server.data.SquareMap;
import pl.edu.agh.mi.server.event.GetSquare;
import pl.edu.agh.mi.server.event.LeaveSquare;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
public class SquareController {
    private SquareMap squareMap;
    private Set<UUID> infectedPeople;

    private SquareController() {
        this.squareMap = new SquareMap();
        this.infectedPeople = new HashSet<>();
    }

    @PostMapping("/getSquare")
    public SquareInfo getSquare(@RequestBody GetSquare event) {
        squareMap.update(event);
        squareMap.save();
        return squareMap.getSquare(event.getPosition()).getInfo();
    }

    @PutMapping("/leaveSquare")
    public void leaveSquare(@RequestBody LeaveSquare event) {
        squareMap.update(event);
        squareMap.save();
    }

    @PostMapping("/infected")
    public void setInfected(@RequestBody UUID userId) {
        infectedPeople.add(userId);
        infectedPeople.addAll(squareMap.findInfectedPeople(userId));
    }

    @GetMapping("/infected")
    public boolean isInfected(@RequestParam(value = "userId") UUID userId) {
        return infectedPeople.contains(userId);
    }
}
