package actions.turn;

import actions.Action;
import listener.EventType;
import listener.SimulationEvent;
import world.*;
import world.entity.EmptyArea;
import world.entity.Entity;
import world.entity.Grass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AddingGrassAction implements Action {
    private final WorldMap worldMap;
    private final Random random;
    private static final double TARGET_GRASS_PERCENTAGE = 0.05;
    private static final int MAX_REGROWTH_PER_TURN = 5;

    public AddingGrassAction(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.random = new Random();
    }

    @Override
    public void perform() {
        regrowGrass();

    }

    private void regrowGrass(){
        int currentGrassCount = currentAmountGrassToMapWorld();
        int totalCells = worldMap.getSize();
        int targetGrassCount = (int) (totalCells * TARGET_GRASS_PERCENTAGE);

        if(currentGrassCount < targetGrassCount){
            int grassToAdd = Math.min(targetGrassCount - currentGrassCount,MAX_REGROWTH_PER_TURN);
            addGrassInRandomEmptySpots(grassToAdd);
        }
    }

    private int currentAmountGrassToMapWorld(){
        int count = 0;
        for (Entity entity: worldMap.getEntityPosition().values()){
            if(entity instanceof Grass){
                count++;
            }
        }
        return count;
    }

    private void addGrassInRandomEmptySpots(int amount){
        List<Coordinate> emptySpots = new ArrayList<>();
        worldMap.getEntityPosition().forEach((coordinate, entity) -> {
            if(entity instanceof EmptyArea){
                emptySpots.add(coordinate);
            }
        });
        Collections.shuffle(emptySpots, random);
        int added = 0;
        for (Coordinate spot: emptySpots){
            if(added >= amount){
                break;
            }
            worldMap.addEntity(new Grass(spot));
            worldMap.notifyListeners(new SimulationEvent(EventType.GRASS_GROWING, String.format("🌿 the grass is growing %s", spot)));
            added++;
        }
    }
}
