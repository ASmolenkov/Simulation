package actions.init;

import actions.Action;
import world.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerateLandscapeAction implements Action {
    private final static double MAX_LANDSCAPE_PERCENTAGE = 0.15;
    private final static double ROCK_PROBABILITY = 0.3;
    private final static double GRASS_PROBABILITY = 0.3;
    private final static double TREE_PROBABILITY = 0.3;
    private final Random random;
    private final MapWorld mapWorld;

    public GenerateLandscapeAction(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
        this.random = new Random();
    }

    @Override
    public void perform() {
        List<Coordinate> availableCoordinates = fillFreeCoordinates(mapWorld);
        int totalLandscape = (int)(availableCoordinates.size() * MAX_LANDSCAPE_PERCENTAGE);
        for (int i = 0; i < availableCoordinates.size(); i++) {
            Coordinate pos = availableCoordinates.get(i);
            Entity entity;
            if (i < totalLandscape) {
                entity = generateRandomTerrain(pos);
            } else {
                entity = new EmptyArea(pos);
            }
            mapWorld.addEntity(entity);
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

        cumulativeProbability += TREE_PROBABILITY;
        if(rand < cumulativeProbability){
            return new Tree(position);
        }
        return new EmptyArea(position);
    }

    private List<Coordinate> fillFreeCoordinates(MapWorld mapWorld){
        List<Coordinate> availableCoordinates = new ArrayList<>();
        for (int x = 0; x < mapWorld.getHeight(); x++) {
            for (int y = 0; y < mapWorld.getWidth(); y++) {
                Coordinate pos = new Coordinate(x, y);
                if (mapWorld.isPositionAvailable(pos)) {
                    availableCoordinates.add(pos);
                }
            }
        }
        Collections.shuffle(availableCoordinates, random);
        return availableCoordinates;
    }
}
