package actions.turn;

import actions.Action;
import factory.creature.CreatureFactory;
import factory.creature.RabbitFactory;
import factory.creature.WolfFactory;
import listener.ConsoleLogger;
import listener.EventType;
import listener.SimulationEvent;
import pathfinding.AStarPathfinder;
import pathfinding.BFSTargetFinder;
import world.*;
import world.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AddingCreatureAction implements Action {
    private static final double TARGET_HERBIVORE_PERCENTAGE = 0.03;
    private static final double TARGET_PREDATOR_PERCENTAGE = 0.02;
    private static final int MAX_REGROWTH_PER_TURN = 1;
    private final MapWorld mapWorld;
    private final Random random;
    private final CreatureFactory<Rabbit> rabbitFactory;
    private final CreatureFactory<Wolf> wolfFactory;

    public AddingCreatureAction(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
        this.random = new Random();
        this.rabbitFactory = RabbitFactory.withDefaultConfig();
        this.wolfFactory = WolfFactory.withDefaultConfig();
    }


    @Override
    public void perform() {
        addCreaturesByType(Rabbit.class, TARGET_HERBIVORE_PERCENTAGE, rabbitFactory);
        addCreaturesByType(Wolf.class, TARGET_PREDATOR_PERCENTAGE, wolfFactory);

    }

    private <T extends Creature> void addCreaturesByType(Class<T> creatureType, double targetPercentage, CreatureFactory<T> factory) {
        int currentCount = getCreatureCount(creatureType);
        int targetCount = (int) (mapWorld.getSize() * targetPercentage);

        if (currentCount < targetCount) {
            int toAdd = Math.min(targetCount - currentCount, MAX_REGROWTH_PER_TURN);
            addCreatureInRandomEmptySpots(toAdd, factory);

        }
    }

    private <T extends Creature> void addCreatureInRandomEmptySpots(int amount, CreatureFactory<T> creatureFactory) {
        getEmptySpots().stream().limit(amount).forEach(spot ->{
            T creature = creatureFactory.createDefault(spot, new BFSTargetFinder(mapWorld), new AStarPathfinder(mapWorld));
            mapWorld.addEntity(creature);
            notifyBirth(creature);
        });
    }

    private <T extends Creature> int getCreatureCount(Class<T> creatureType) {
        return (int) mapWorld.getEntityPositionMap().values().stream()
                .filter(creatureType::isInstance)
                .count();
    }

    private List<Coordinate> getEmptySpots(){
        List<Coordinate> emptySpots = new ArrayList<>();
        mapWorld.getEntityPositionMap().forEach((coordinate, entity) -> {
            if (entity instanceof EmptyArea){
                emptySpots.add(coordinate);
            }
        });
        Collections.shuffle(emptySpots,random);
        return emptySpots;
    }

    private void notifyBirth(Creature creature){
        mapWorld.notifyListeners(new SimulationEvent(EventType.ENTITY_SPAWNED, String.format("🙏 A new one was born %s", creature.getClass().getSimpleName()),creature));
    }
}
