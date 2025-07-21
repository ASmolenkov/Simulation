package actions.turn;

import actions.Action;
import factory.creature.RabbitFactory;
import pathfinding.BFSPathFinder;
import pathfinding.BFSTargetFinder;
import world.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AddingHerbivoreAction implements Action {
    private MapWorld mapWorld;
    private Random random;
    private static final double TARGET_HERBIVORE_PERCENTAGE = 0.03;
    private static final int MAX_REGROWTH_PER_TURN = 1;
    private final RabbitFactory rabbitFactory;

    public AddingHerbivoreAction(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
        this.random = new Random();
        this.rabbitFactory = RabbitFactory.withDefaultConfig();
    }


    @Override
    public void perform() {
        addHerbivore();

    }

    private void addHerbivore(){
        int currentHerbivore = currentHerbivoreCount();
        int totalCells = mapWorld.getSize();
        int targetHerbivoreCount = (int) (totalCells * TARGET_HERBIVORE_PERCENTAGE);

        if(currentHerbivore < targetHerbivoreCount){
            int herbivoreToAdd = Math.min(targetHerbivoreCount - currentHerbivore, MAX_REGROWTH_PER_TURN);
            addHerbivoreInRandomEmptySpots(herbivoreToAdd);
        }
    }



    private int currentHerbivoreCount() {
        int count = 0;

        for (Entity entity: mapWorld.getEntityPositionMap().values()){
            if(entity instanceof Herbivore){
                count++;
            }
        }
        return count;
    }

    private void addHerbivoreInRandomEmptySpots(int amount) {
        List<Coordinate> emptySpots = new ArrayList<>();

        mapWorld.getEntityPositionMap().forEach((coordinate, entity) -> {
            if (entity instanceof EmptyArea){
                emptySpots.add(coordinate);
            }
        });

        Collections.shuffle(emptySpots,random);

        int added = 0;

        for (Coordinate spot: emptySpots){
            if(added >= amount){
                break;
            }
            Rabbit rabbit = rabbitFactory.createDefault(spot,new BFSTargetFinder(mapWorld), new BFSPathFinder(mapWorld));
            mapWorld.addEntity(rabbit);
            added++;
        }
    }
}
