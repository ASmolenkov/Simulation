import creature.generate.CoordinateFinder;
import creature.generate.CreatureCountCalculator;
import creature.generate.CreatureSpawner;
import creature.generate.EmptyCoordinateFinder;
import factory.creature.CreatureFactory;
import factory.creature.RabbitFactory;
import factory.creature.WolfFactory;
import pathfinding.*;
import world.*;
import world.entity.Creature;
import world.entity.CreatureType;
import world.entity.Rabbit;
import world.entity.Wolf;

import java.util.Random;

public class SimulationSettings {
    private static final double PERCENTAGE_FILL_MAP_WORLD = 0.1;
    private static final double PROBABILITIES_HERBIVORE = 0.7;
    private static final double PROBABILITIES_PREDATOR = 0.3;
    private static final double MAX_SEARCH_DEPTH_RABBIT = 7;

    private final WorldMap worldMap;
    private final TargetFinder targetExplorer;
    private final Pathfinder pathExplorer;
    private final Random random = new Random();
    private final CoordinateFinder emptyCoordinateFinder;
    private final CreatureCountCalculator creatureCountCalculator;
    private final CreatureFactory<Rabbit> rabbitFactory;
    private final CreatureFactory<Wolf> wolfFactory;
    private final CreatureSpawner<Creature> creatureSpawner;

    public SimulationSettings(WorldMap worldMap) {
        this.rabbitFactory = RabbitFactory.withDefaultConfig();
        this.wolfFactory = WolfFactory.withDefaultConfig();
        this.worldMap = worldMap;
        this.targetExplorer = new BFSTargetFinder(worldMap);
        this.pathExplorer = new AStarPathfinder(worldMap);
        this.creatureCountCalculator = new CreatureCountCalculator(random, PERCENTAGE_FILL_MAP_WORLD);
        creatureCountCalculator.addCreatureTypeAndProbabilities(CreatureType.HERBIVORE, PROBABILITIES_HERBIVORE);
        creatureCountCalculator.addCreatureTypeAndProbabilities(CreatureType.PREDATOR, PROBABILITIES_PREDATOR);
        this.emptyCoordinateFinder = new EmptyCoordinateFinder(random, worldMap);
        this.creatureSpawner = new CreatureSpawner<>(worldMap,random,emptyCoordinateFinder,creatureCountCalculator,targetExplorer,pathExplorer);
        this.creatureSpawner.addFactory(CreatureType.HERBIVORE,rabbitFactory);
        this.creatureSpawner.addFactory(CreatureType.PREDATOR, wolfFactory);
    }

    public WorldMap getMapWorld() {
        return worldMap;
    }

    public CreatureSpawner<Creature> getCreatureSpawner() {
        return creatureSpawner;
    }
}
