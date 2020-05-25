package pl.edu.agh.mi.server.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import pl.edu.agh.mi.server.event.GetSquare;
import pl.edu.agh.mi.server.event.LeaveSquare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static java.lang.Math.floor;


public class SquareMap {
    private static final double TOP = 50.127254;
    private static final double LEFT = 19.790282;
    private static final double BOT = 49.968808;
    private static final double RIGHT = 20.218548;
    private static final double EDGE_LEN = 0.1;
    private static final String MAP_FILE_NAME = "med-inf-map.json";

    private final List<List<Square>> squares;

    public SquareMap() {
        if (new File(MAP_FILE_NAME).exists()) {
            squares = loadSquaresFromFile();
        } else {
            squares = createSquares(EDGE_LEN);
            save();
        }
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

    public Set<UUID> findInfectedPeople(UUID userId) {
        Set<UUID> infectedPeople = new HashSet<>();
        squares.stream()
                .flatMap(List::stream)
                .filter(square -> square.getPeople().containsKey(userId))
                .forEach(square -> infectedPeople.addAll(findInfectedPeopleInSquare(square, userId)));
        return infectedPeople;
    }

    private Set<UUID> findInfectedPeopleInSquare(Square square, UUID userId) {
        Set<UUID> infectedPeople = new HashSet<>();
        List<TimeRange> infectedTimeRanges = square.getPeople().get(userId);
        for (Map.Entry<UUID, List<TimeRange>> entry : square.getPeople().entrySet()) {
            if (isInfected(infectedTimeRanges, entry.getValue())) {
                infectedPeople.add(entry.getKey());
            }
        }
        return infectedPeople;
    }

    private boolean isInfected(List<TimeRange> infectedTimeRanges, List<TimeRange> suspectedTimeRanges) {
        for (TimeRange timeRange : suspectedTimeRanges) {
            for (TimeRange infectedTimeRange : infectedTimeRanges) {
                if (infectedTimeRange.isOverlaping(timeRange)) {
                    return  true;
                }
            }
        }
        return false;
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

    private List<List<Square>> loadSquaresFromFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            TypeReference<List<List<Square>>> mapType = new TypeReference<>() {
            };
            return mapper.readValue(new File(MAP_FILE_NAME), mapType);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<List<Square>> createSquares(double edgeLen) {
        List<List<Square>> grid = new ArrayList<>();
        double xStart = LEFT + 0.5 * edgeLen;
        double yStart = TOP - 0.5 * edgeLen;
        for (double y = yStart; y > BOT; y-=edgeLen) {
            List<Square> row = new ArrayList<>();
            for (double x = xStart; x < RIGHT; x+=edgeLen) {
                SquareInfo info = new SquareInfo(new Position(y, x), edgeLen);
                row.add(new Square(info));
            }
            grid.add(row);
        }
        return grid;
    }

    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try (FileWriter writer = new FileWriter(MAP_FILE_NAME)) {
            String json = ow.writeValueAsString(squares);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
