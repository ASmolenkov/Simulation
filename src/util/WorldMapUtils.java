package util;

import world.Coordinate;
import world.WorldMap;
import world.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WorldMapUtils {

    private WorldMapUtils(){}

    public static List<Coordinate> getEmptyCoordinates(WorldMap worldMap){
        List<Coordinate> emptyCoordinates = new ArrayList<>();
        for (int i = 0; i < worldMap.getHeight(); i++) {
            for (int j = 0; j < worldMap.getWidth(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                if(worldMap.isFreePosition(coordinate)){
                    emptyCoordinates.add(coordinate);
                }
            }
        }
        Collections.shuffle(emptyCoordinates);
        return emptyCoordinates;
    }

    public static Coordinate getRandomEmptyCoordinate(WorldMap worldMap){
        Random random = new Random();
        List<Coordinate> emptyCoordinates = getEmptyCoordinates(worldMap);
        return emptyCoordinates.get(random.nextInt(emptyCoordinates.size()));
    }

    public static int getAmountHerbivore (WorldMap worldMap){
        int amountHerbivore = 0;
        List<Entity> entities = worldMap.getAllEntity();
        for (Entity entity: entities){
            if(entity instanceof Herbivore){
                amountHerbivore++;
            }
        }
        return amountHerbivore;
    }

    public static int getAmountPredator (WorldMap worldMap){
        int amountPredator = 0;
        List<Entity> entities = worldMap.getAllEntity();
        for (Entity entity: entities){
            if(entity instanceof Predator){
                amountPredator++;
            }
        }
        return amountPredator;
    }

    public static int getAmountGrass (WorldMap worldMap){
        int amountGrass = 0;
        List<Entity> entities = worldMap.getAllEntity();
        for (Entity entity: entities){
            if(entity instanceof Grass){
                amountGrass++;
            }
        }
        return amountGrass;
    }

    public static int getAmountEntities(WorldMap worldMap, Class<? extends Entity> entityClass){
        int amount = 0;
        List<Entity> entities = worldMap.getAllEntity();
        for (Entity entity1: entities){
            if(entityClass.isInstance(entity1)){
                amount++;
            }
        }
        return amount;
    }

    public static int getSizeMap(WorldMap worldMap){
        return worldMap.getHeight() * worldMap.getWidth();
    }

    public static boolean noEmptyCoordinates(WorldMap worldMap){
        List<Coordinate> emptyCoordinates = getEmptyCoordinates (worldMap);
        return emptyCoordinates.isEmpty();
    }
}


