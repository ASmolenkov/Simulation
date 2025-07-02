package actions.turn;

import actions.Action;
import factory.creature.RabbitFactory;
import world.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AddingHerbivoreAction implements Action {
    private MapWorld mapWorld;
    private Random random;
    private final double targetHerbivorePercentage;
    private final int maxRegrowthPerTurn;
    private final RabbitFactory rabbitFactory;

    public AddingHerbivoreAction(int maxRegrowthPerTurn, MapWorld mapWorld, double targetHerbivorePercentage) {
        this.mapWorld = mapWorld;
        this.random = new Random();
        this.targetHerbivorePercentage = targetHerbivorePercentage;
        this.maxRegrowthPerTurn = maxRegrowthPerTurn;
        this.rabbitFactory = RabbitFactory.withDefaultConfig();
    }


    @Override
    public void perform(MapWorld mapWorld) {
        addHerbivore();

    }

    private void addHerbivore(){
        int currentHerbivore = currentHerbivoreCount();
        int totalCells = mapWorld.getSize();
        int targetHerbivoreCount = (int) (totalCells * targetHerbivorePercentage);

        if(currentHerbivore < targetHerbivoreCount){
            int herbivoreToAdd = Math.min(targetHerbivoreCount - currentHerbivore, maxRegrowthPerTurn);
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
            Rabbit rabbit = rabbitFactory.createDefault(spot, new BFSExplorer(mapWorld));
            mapWorld.addEntity(rabbit);
            added++;
        }
    }
}
