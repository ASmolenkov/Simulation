package creature.generate;

import factory.creature.CreatureFactory;
import pathfinding.NewPathfinder;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import util.WorldMapUtils;
import world.*;
import world.entity.Creature;
import world.entity.CreatureType;

import java.util.*;

public class CreatureSpawner<T extends Creature> {
    private static final String NO_FACTORY = "No factory registered for type: ";
    private static final String NO_FREE_PLACES = "There are no available coordinates";

    private final WorldMap worldMap;
    private final Random random;
    private final CoordinateFinder emptyCoordinatesFinder;
    private final CreatureCountCalculator countCalculator;
    private final Map<CreatureType, CreatureFactory<? extends T>> factories;
    private final TargetFinder targetExplorer;
    private final Pathfinder pathExplorer;
    private final NewPathfinder pathfinder;


    public CreatureSpawner(WorldMap worldMap, Random random, CoordinateFinder emptyCoordinatesFinder, CreatureCountCalculator countCalculator,
                           TargetFinder targetExplorer, Pathfinder pathExplorer, NewPathfinder pathfinder) {
        this.worldMap = worldMap;
        this.emptyCoordinatesFinder = emptyCoordinatesFinder;
        this.countCalculator = countCalculator;
        this.pathfinder = pathfinder;
        this.factories = new HashMap<>();
        this.targetExplorer = targetExplorer;
        this.pathExplorer = pathExplorer;
        this.random = random;
    }

    public void addFactory(CreatureType creatureType, CreatureFactory<? extends T> factory){
        factories.put(creatureType,factory);
    }

    public void spawnCreatures(){
        ensureSpawnPossible();
        Map<CreatureType, Integer> creatureCounts = countCalculator.calculateCounts(WorldMapUtils.getSizeMap(worldMap));
        creatureCounts.forEach(this::spawnCreaturesOfType);
    }

    private void validateFactoryExists(CreatureType creatureType){
        if(!factories.containsKey(creatureType)){
            throw new IllegalStateException(NO_FACTORY + creatureType);
        }
    }

    private void spawnSingleCreature(CreatureType creatureType){
        Coordinate position = emptyCoordinatesFinder.findRandomEmptyCoordinate();
        CreatureFactory<? extends T> factory = factories.get(creatureType);
        T creature = factory.createDefault(position, pathfinder);
        worldMap.addEntity(position, creature);
        emptyCoordinatesFinder.markPositionAsOccupied(position);
    }

    private void spawnCreaturesOfType(CreatureType creatureType, int count){
        validateFactoryExists(creatureType);
        for (int i = 0; i < count; i++) {
            spawnSingleCreature(creatureType);
        }
    }

    private void ensureSpawnPossible() {
        if (emptyCoordinatesFinder.hasEmptyCoordinates()) {
            throw new IllegalStateException(NO_FREE_PLACES);
        }
    }
}
