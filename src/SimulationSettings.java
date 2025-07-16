import creature.generate.CoordinateFinder;
import creature.generate.CreatureCountCalculator;
import creature.generate.CreatureSpawner;
import creature.generate.EmptyCoordinateFinder;
import factory.creature.CreatureFactory;
import factory.creature.RabbitFactory;
import factory.creature.WolfFactory;
import pathfinding.*;
import world.*;

import java.util.Random;

public class SimulationSettings {
    private MapWorld mapWorld;
    private final TargetFinder targetExplorer;
    private final Pathfinder pathExplorer;
    private final Random random = new Random();
    private final CoordinateFinder emptyCoordinateFinder;
    private CreatureCountCalculator creatureCountCalculator;
    private CreatureFactory<Rabbit> rabbitFactory;
    private CreatureFactory<Wolf> wolfFactory;
    private CreatureSpawner<Creature> creatureSpawner;

    public SimulationSettings(MapWorld mapWorld) {
        this.rabbitFactory = RabbitFactory.withDefaultConfig();
        this.wolfFactory = WolfFactory.withDefaultConfig();
        this.mapWorld = mapWorld;
        this.targetExplorer = new BFSTargetFinder(mapWorld);
        this.pathExplorer = new AStarPathfinder(mapWorld);
        this.creatureCountCalculator = new CreatureCountCalculator(random, 0.1);
        creatureCountCalculator.addCreatureTypeAndProbabilities(CreatureType.HERBIVORE, 0.7);
        creatureCountCalculator.addCreatureTypeAndProbabilities(CreatureType.PREDATOR, 0.3);
        this.emptyCoordinateFinder = new EmptyCoordinateFinder(random,mapWorld);
        this.creatureSpawner = new CreatureSpawner<>(mapWorld,random,emptyCoordinateFinder,creatureCountCalculator,targetExplorer,pathExplorer);
        this.creatureSpawner.addFactory(CreatureType.HERBIVORE,rabbitFactory);
        this.creatureSpawner.addFactory(CreatureType.PREDATOR, wolfFactory);
    }

    public MapWorld getMapWorld() {
        return mapWorld;
    }

    public CoordinateFinder getEmptyCoordinateFinder() {
        return emptyCoordinateFinder;
    }

    public CreatureCountCalculator getCreatureCountCalculator() {
        return creatureCountCalculator;
    }

    public CreatureSpawner<Creature> getCreatureSpawner() {
        return creatureSpawner;
    }
}
