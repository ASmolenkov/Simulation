package actions.turn;

import actions.Action;
import world.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AddingGrassAction implements Action {
    private MapWorld mapWorld;
    private Random random;
    private final double targetGrassPercentage;
    private final int maxRegrowthPerTurn;

    public AddingGrassAction(MapWorld mapWorld, double targetGrassPercentage, int maxRegrowthPerTurn) {
        this.mapWorld = mapWorld;
        this.random = new Random();
        this.targetGrassPercentage = targetGrassPercentage;
        this.maxRegrowthPerTurn = maxRegrowthPerTurn;
    }

    @Override
    public void perform(MapWorld mapWorld) {
        regrowGrass();
    }

    private void regrowGrass(){
        int currentGrassCount = currentGrassCount();
        int totalCells = mapWorld.getSize();
        int targetGrassCount = (int) (totalCells * targetGrassPercentage);

        if(currentGrassCount < targetGrassCount){
            int grassToAdd = Math.max(targetGrassCount - currentGrassCount, maxRegrowthPerTurn);
            addGrassInRandomEmptySpots(grassToAdd);
        }
    }

    private int currentGrassCount(){
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
