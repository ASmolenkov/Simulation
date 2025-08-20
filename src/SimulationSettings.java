
import creature.generate.CreatureCountCalculator;
import creature.generate.CreatureSpawner;
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
    private final NewPathfinder pathfinder;
    private final Random random = new Random();
    private final CreatureCountCalculator creatureCountCalculator;
    private final CreatureFactory<Rabbit> rabbitFactory;
    private final CreatureFactory<Wolf> wolfFactory;
    private final CreatureSpawner<Creature> creatureSpawner;

    public SimulationSettings(WorldMap worldMap) {
        this.rabbitFactory = new RabbitFactory();
        this.wolfFactory = new WolfFactory();
        this.worldMap = worldMap;
        this.targetExplorer = new BFSTargetFinder(worldMap);
        this.pathExplorer = new AStarPathfinder(worldMap);
        this.pathfinder = new NewAstarPathfinder(worldMap);
        this.creatureCountCalculator = new CreatureCountCalculator(random, PERCENTAGE_FILL_MAP_WORLD);
        creatureCountCalculator.addCreatureTypeAndProbabilities(CreatureType.HERBIVORE, PROBABILITIES_HERBIVORE);
        creatureCountCalculator.addCreatureTypeAndProbabilities(CreatureType.PREDATOR, PROBABILITIES_PREDATOR);
        this.creatureSpawner = new CreatureSpawner<>(worldMap,random,creatureCountCalculator,targetExplorer,pathExplorer, pathfinder);
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
