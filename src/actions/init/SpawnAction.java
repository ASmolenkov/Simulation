package actions.init;

import actions.Action;
import creature.generate.CreatureSpawner;
import factory.creature.RabbitFactory;
import util.WorldMapUtils;
import world.Coordinate;
import world.WorldMap;
import world.entity.*;

import java.util.function.Function;
import java.util.function.Supplier;

public class SpawnAction implements Action {
    private final static double MAX_LANDSCAPE_PERCENTAGE = 0.2;
    private static final double COUNT_GRASS_PERCENTAGE = 0.3;
    private static final double COUNT_TREE_PERCENTAGE = 0.2;
    private static final double COUNT_ROCK_PERCENTAGE = 0.2;


    private final WorldMap worldMap;
    private final CreatureSpawner<Creature> creatureSpawner;


    public SpawnAction(WorldMap worldMap, CreatureSpawner<Creature> creatureSpawner) {
        this.worldMap = worldMap;
        this.creatureSpawner = creatureSpawner;
    }

    @Override
    public void perform() {
        spawn(worldMap,(coordinate) -> new Grass(), COUNT_GRASS_PERCENTAGE);
        spawn(worldMap,(coordinate) -> new Rock(), COUNT_ROCK_PERCENTAGE);
        spawn(worldMap,(coordinate) -> new Tree(), COUNT_TREE_PERCENTAGE);
        creatureSpawner.spawnCreatures();
    }

    private void spawn(WorldMap worldMap, Function<Coordinate, Entity> mapper, double amount){

        int countEntity = (int) ((WorldMapUtils.getSizeMap(worldMap) * amount) * MAX_LANDSCAPE_PERCENTAGE);
            for (int i = 0; i < countEntity; i++) {
                Coordinate randomCoordinate = WorldMapUtils.getRandomEmptyCoordinate(worldMap);
                Entity entity = mapper.apply(randomCoordinate);
                worldMap.addEntity(randomCoordinate, entity);
            }
    }
    private int amountEntity(WorldMap worldMap, double amount, double maxPercentageEntity){
        return (int) ((WorldMapUtils.getSizeMap(worldMap) * amount) * maxPercentageEntity);
    }


}

