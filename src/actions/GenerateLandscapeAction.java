package actions;

import world.*;
import java.util.Random;

public class GenerateLandscapeAction implements Action{
    private final static double ROCK_PROBABILITY = 0.1;
    private final static double GRASS_PROBABILITY = 0.150;
    private final static double TREE_PROBABILITY = 0.210;
    private final Random random;

    public GenerateLandscapeAction() {
        this.random = new Random();
    }

    @Override
    public void perform(MapWorld mapWorld) {
        for (int x = 0; x < mapWorld.getHeight(); x++) {
            for (int y = 0; y < mapWorld.getWidth(); y++) {
                Coordinate position = new Coordinate(x,y);
                if(mapWorld.isPositionAvailable(position)){
                    Entity entity = generateTerrain(position, mapWorld);
                    mapWorld.addEntity(entity);
                }

            }
        }
    }

    private Entity generateTerrain(Coordinate position, MapWorld mapWorld){
        double rand = random.nextDouble();
        if(rand < ROCK_PROBABILITY){
            return new Rock(position);
        }
        if(rand < GRASS_PROBABILITY){
            return new Grass(position);
        }
        if(rand < TREE_PROBABILITY){
            return new Tree(position);
        }
        return new EmptyArea(position);
    }
}
