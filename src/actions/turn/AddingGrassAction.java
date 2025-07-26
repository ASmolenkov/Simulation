package actions.turn;

import actions.Action;
import world.*;
import world.entity.EmptyArea;
import world.entity.Entity;
import world.entity.Grass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AddingGrassAction implements Action {
    private final MapWorld mapWorld;
    private final Random random;
    private static final double TARGET_GRASS_PERCENTAGE = 0.05;
    private static final int MAX_REGROWTH_PER_TURN = 5;

    public AddingGrassAction(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
        this.random = new Random();
    }

    @Override
    public void perform() {
        regrowGrass();
    }

    private void regrowGrass(){
        int currentGrassCount = currentAmountGrassToMapWorld();
        int totalCells = mapWorld.getSize();
        int targetGrassCount = (int) (totalCells * TARGET_GRASS_PERCENTAGE);

        if(currentGrassCount < targetGrassCount){
            int grassToAdd = Math.min(targetGrassCount - currentGrassCount,MAX_REGROWTH_PER_TURN);
            addGrassInRandomEmptySpots(grassToAdd);
        }
    }

    private int currentAmountGrassToMapWorld(){
        int count = 0;
        for (Entity entity: mapWorld.getEntityPositionMap().values()){
            if(entity instanceof Grass){
                count++;
            }
        }
        return count;
    }

    private void addGrassInRandomEmptySpots(int amount){
        List<Coordinate> emptySpots = new ArrayList<>();
        mapWorld.getEntityPositionMap().forEach((coordinate, entity) -> {
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
            mapWorld.addEntity(new Grass(spot));
            added++;
        }
    }
}
