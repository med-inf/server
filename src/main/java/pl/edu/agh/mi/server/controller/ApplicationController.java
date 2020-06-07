package pl.edu.agh.mi.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.io.File;
import java.util.UUID;

import static pl.edu.agh.mi.server.data.SquareMap.MAP_FILE_NAME;

@Controller
public class ApplicationController {
    Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    private SquareMap squareMap;

    private ApplicationController() {
        if (new File(MAP_FILE_NAME).exists()) {
            squareMap = SquareMap.loadSquaresFromFile();
        } else {
            squareMap = new SquareMap();
        }
    }

    @PostMapping("/getSquare")
    @ResponseBody
    public SquareInfo getSquare(@RequestBody GetSquare event) {
        logger.info("GetSquare: " + event.getUserId() + " " + event.getPosition());
        squareMap.update(event);
        squareMap.save();
        return squareMap.getSquare(event.getPosition()).getInfo();
    }

    @PutMapping("/leaveSquare")
    @ResponseBody
    public void leaveSquare(@RequestBody LeaveSquare event) {
        logger.info("LeaveSquare: " + event.getUserId() + " " + event.getSquareCenter());
        squareMap.update(event);
        squareMap.save();
    }

    @PostMapping("/infected")
    public String setInfected(@ModelAttribute InfectedDTO infectedDTO, Model model) {
        String userId = infectedDTO.getUserId();
        logger.info("Set infected: " + userId);
        UUID infectedUserUUUID = UUID.fromString(userId);
        squareMap.addInfected(infectedUserUUUID);
        squareMap.addAllInfected(squareMap.findInfectedPeople(infectedUserUUUID));
        String message = String.format("User: %s set as infected", userId);
        model.addAttribute("result", new ResultDTO(message));
        squareMap.save();
        return "result";
    }

    @PostMapping("/notInfected")
    public String setNotInfected(@ModelAttribute NotInfectedDTO notInfectedDTO, Model model) {
        String message;
        String userId = notInfectedDTO.getUserId();
        logger.info("Set not infected " + userId);
        if (squareMap.removeInfected(UUID.fromString(userId))) {
            message = String.format("User: %s set as non infected", userId);
        } else {
            message = String.format("No user with id: %s", userId);
        }
        model.addAttribute("result", new ResultDTO(message));
        squareMap.save();
        return "result";
    }

    @GetMapping("/infected")
    @ResponseBody
    public boolean isInfected(@RequestParam(value = "userId") UUID userId) {
        logger.info("Is infected " + userId.toString());
        return squareMap.isInfected(userId);
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        logger.info("GET Admin");
        model.addAttribute("infectedDTO", new InfectedDTO());
        model.addAttribute("notInfectedDTO", new NotInfectedDTO());
        return "index";
    }
}
