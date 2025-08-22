
import factory.creature.CreatureFactory;
import factory.creature.RabbitFactory;
import factory.creature.WolfFactory;
import pathfinding.*;
import world.*;
import world.entity.Rabbit;
import world.entity.Wolf;



public class SimulationSettings {

    private final WorldMap worldMap;
    private final Pathfinder pathfinder;
    private final CreatureFactory<Rabbit> rabbitFactory;
    private final CreatureFactory<Wolf> wolfFactory;

    public SimulationSettings(WorldMap worldMap) {
        this.rabbitFactory = new RabbitFactory();
        this.wolfFactory = new WolfFactory();
        this.worldMap = worldMap;
        this.pathfinder = new AstarPathfinder(worldMap);

    }

    public WorldMap getMapWorld() {
        return worldMap;
    }

    public CreatureFactory<Rabbit> getRabbitFactory() {
        return rabbitFactory;
    }

    public CreatureFactory<Wolf> getWolfFactory() {
        return wolfFactory;
    }

    public Pathfinder getPathfinder() {
        return pathfinder;
    }
}
