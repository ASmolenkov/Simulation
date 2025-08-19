package actions.turn;

import actions.Action;
import factory.creature.CreatureFactory;
import factory.creature.RabbitFactory;
import factory.creature.WolfFactory;
import listener.EventType;
import listener.SimulationEvent;
import pathfinding.AStarPathfinder;
import pathfinding.BFSTargetFinder;
import pathfinding.NewAstarPathfinder;
import util.WorldMapUtils;
import world.*;
import world.entity.*;


import java.util.List;
import java.util.Random;

public class AddingCreatureAction implements Action {
    private static final double TARGET_HERBIVORE_PERCENTAGE = 0.03;
    private static final double TARGET_PREDATOR_PERCENTAGE = 0.02;
    private static final int MAX_REGROWTH_PER_TURN = 1;
    private final WorldMap worldMap;
    private final Random random;
    private final CreatureFactory<Rabbit> rabbitFactory;
    private final CreatureFactory<Wolf> wolfFactory;

    public AddingCreatureAction(WorldMap worldMap) {
        this.worldMap = worldMap;
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
        int targetCount = (int) (WorldMapUtils.getSizeMap(worldMap) * targetPercentage);

        if (currentCount < targetCount) {
            int toAdd = Math.min(targetCount - currentCount, MAX_REGROWTH_PER_TURN);
            addCreatureInRandomEmptySpots(toAdd, factory);

        }
    }

    private <T extends Creature> void addCreatureInRandomEmptySpots(int amount, CreatureFactory<T> creatureFactory) {
        getEmptySpots().stream().limit(amount).forEach(spot ->{
            T creature = creatureFactory.createDefault(spot, new NewAstarPathfinder(worldMap));
            worldMap.addEntity(creature);
            notifyBirth(creature);
        });
    }

    private <T extends Creature> int getCreatureCount(Class<T> creatureType) {
        return (int) worldMap.getAllEntity().stream()
                .filter(creatureType::isInstance)
                .count();
    }

    private List<Coordinate> getEmptySpots(){
        return WorldMapUtils.getEmptyCoordinates(worldMap);
    }

    private void notifyBirth(Creature creature){
        worldMap.notifyListeners(new SimulationEvent(EventType.ENTITY_SPAWNED, String.format("🙏 A new one was born %s", creature.getClass().getSimpleName()),creature));
    }
}
