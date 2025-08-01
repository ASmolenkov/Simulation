package creature.generate;

import factory.creature.CreatureFactory;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.*;
import world.entity.Creature;
import world.entity.CreatureType;

import java.util.*;

public class CreatureSpawner<T extends Creature> {
    private final MapWorld mapWorld;
    private final Random random;
    private final CoordinateFinder emptyCoordinatesFinder;
    private final CreatureCountCalculator countCalculator;
    private final Map<CreatureType, CreatureFactory<? extends T>> factories;
    private final TargetFinder targetExplorer;
    private final Pathfinder pathExplorer;


    public CreatureSpawner(MapWorld mapWorld, Random random, CoordinateFinder emptyCoordinatesFinder, CreatureCountCalculator countCalculator, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        this.mapWorld = mapWorld ;
        this.emptyCoordinatesFinder = emptyCoordinatesFinder;
        this.countCalculator = countCalculator;
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
        Map<CreatureType, Integer> creatureCounts = countCalculator.calculateCounts(mapWorld.getSize());
        creatureCounts.forEach(this::spawnCreaturesOfType);
    }

    private void validateFactoryExists(CreatureType creatureType){
        if(!factories.containsKey(creatureType)){
            throw new IllegalStateException("No factory registered for type: " + creatureType);
        }
    }

    private void spawnSingleCreature(CreatureType creatureType){
        Coordinate position = emptyCoordinatesFinder.findRandomEmptyCoordinate();
        CreatureFactory<? extends T> factory = factories.get(creatureType);
        T creature = factory.createDefault(position, targetExplorer, pathExplorer);
        mapWorld.addEntity(creature);
        emptyCoordinatesFinder.markPositionAsOccupied(position);
    }

    private void spawnCreaturesOfType(CreatureType creatureType, int count){
        validateFactoryExists(creatureType);
        for (int i = 0; i < count; i++) {
            spawnSingleCreature(creatureType);
        }
    }

    private void ensureSpawnPossible() {
        if (!emptyCoordinatesFinder.hasEmptyCoordinates()) {
            throw new IllegalStateException("There are no free places for creatures to spawn.");
        }
    }
}
