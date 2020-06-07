package pl.edu.agh.mi.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.mi.server.data.SquareInfo;
import pl.edu.agh.mi.server.data.SquareMap;
import pl.edu.agh.mi.server.dto.InfectedDTO;
import pl.edu.agh.mi.server.dto.NotInfectedDTO;
import pl.edu.agh.mi.server.dto.ResultDTO;
import pl.edu.agh.mi.server.event.GetSquare;
import pl.edu.agh.mi.server.event.LeaveSquare;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
public class ApplicationController {
    private SquareMap squareMap;
    private Set<UUID> infectedPeople;

    private ApplicationController() {
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
    public String setInfected(@ModelAttribute InfectedDTO infectedDTO, Model model) {
        System.out.println("adminDTO: " + infectedDTO.getUserId());
        String userId = infectedDTO.getUserId();
        UUID infectedUserUUUID = UUID.fromString(userId);
        infectedPeople.add(infectedUserUUUID);
        infectedPeople.addAll(squareMap.findInfectedPeople(infectedUserUUUID));
        String message = String.format("User: %s set as infected", userId);
        model.addAttribute("result", new ResultDTO(message));
        return "result";
    }

    @PostMapping("/notInfected")
    public String setNotInfected(@ModelAttribute NotInfectedDTO notInfectedDTO, Model model) {
        String message;
        String userId = notInfectedDTO.getUserId();
        if (infectedPeople.remove(UUID.fromString(userId))) {
            message = String.format("User: %s set as non infected", userId);
        } else {
            message = String.format("No user with id: %s", userId);
        }
        model.addAttribute("result", new ResultDTO(message));
        return "result";
    }

    @GetMapping("/infected")
    public boolean isInfected(@RequestParam(value = "userId") UUID userId) {
        return infectedPeople.contains(userId);
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("infectedDTO", new InfectedDTO());
        model.addAttribute("notInfectedDTO", new NotInfectedDTO());
        return "index";
    }
}
