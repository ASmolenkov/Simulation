package actions.init;

import actions.Action;
import world.*;
import world.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerateLandscapeAction implements Action {
    private final static double MAX_LANDSCAPE_PERCENTAGE = 0.19;
    private final static double ROCK_PROBABILITY = 0.325;
    private final static double GRASS_PROBABILITY = 0.350;
    private final static double TREE_PROBABILITY = 0.325;
    private final Random random;
    private final WorldMap worldMap;

    public GenerateLandscapeAction(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.random = new Random();
    }

    @Override
    public void perform() {
        List<Coordinate> availableCoordinates = fillFreeCoordinates(worldMap);
        int totalLandscape = (int)(availableCoordinates.size() * MAX_LANDSCAPE_PERCENTAGE);
        for (int i = 0; i < availableCoordinates.size() && i < totalLandscape; i++) {
            Coordinate pos = availableCoordinates.get(i);
            Entity entity = generateRandomTerrain(pos);
            worldMap.addEntity(entity);
        }
    }

    private Entity generateRandomTerrain(Coordinate position){
        double rand = random.nextDouble();
        double cumulativeProbability = 0.0;

        cumulativeProbability += ROCK_PROBABILITY;
        if(rand < cumulativeProbability){
            return new Rock(position);
        }

        cumulativeProbability += GRASS_PROBABILITY;
        if(rand < cumulativeProbability){
            return new Grass(position);
        }

        return new Tree(position);
    }

    private List<Coordinate> fillFreeCoordinates(WorldMap worldMap){
        List<Coordinate> availableCoordinates = new ArrayList<>();
        for (int x = 0; x < worldMap.getHeight(); x++) {
            for (int y = 0; y < worldMap.getWidth(); y++) {
                Coordinate pos = new Coordinate(x, y);
                if (worldMap.isPositionAvailable(pos)) {
                    availableCoordinates.add(pos);
                }
            }
        }
        Collections.shuffle(availableCoordinates, random);
        return availableCoordinates;
    }
}
