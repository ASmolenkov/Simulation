package actions.init;

import actions.Action;
import factory.creature.RabbitFactory;
import factory.creature.WolfFactory;
import pathfinding.Pathfinder;
import util.WorldMapUtils;
import world.Coordinate;
import world.WorldMap;
import world.entity.*;

import java.util.function.Function;

public class SpawnAction implements Action {
    private static final int COUNT_GRASS_PERCENTAGE = 6;
    private static final int COUNT_TREE_PERCENTAGE = 5;
    private static final int COUNT_ROCK_PERCENTAGE = 5;
    private static final int COUNT_RABBIT_PERCENTAGE = 3;
    private static final int COUNT_WOLF_PERCENTAGE = 1;

    private final WorldMap worldMap;
    private final WolfFactory wolfFactory = new WolfFactory();
    private final RabbitFactory rabbitFactory = new RabbitFactory();
    private final Pathfinder pathfinder;


    public SpawnAction(WorldMap worldMap, Pathfinder pathfinder) {
        this.worldMap = worldMap;
        this.pathfinder = pathfinder;
    }

    @Override
    public void perform() {
        spawn(worldMap, Grass.class, (coordinate) -> new Grass(), COUNT_GRASS_PERCENTAGE);
        spawn(worldMap, Rock.class, (coordinate) -> new Rock(), COUNT_ROCK_PERCENTAGE);
        spawn(worldMap, Tree.class, (coordinate) -> new Tree(), COUNT_TREE_PERCENTAGE);
        spawn(worldMap, Rabbit.class, (coordinate) -> rabbitFactory.createDefault(coordinate, pathfinder), COUNT_RABBIT_PERCENTAGE);
        spawn(worldMap, Wolf.class, (coordinate) -> wolfFactory.createDefault(coordinate, pathfinder), COUNT_WOLF_PERCENTAGE);
    }

    /*private void spawn(WorldMap worldMap, Function<Coordinate, Entity> mapper, double amount){
        int countEntity = (int) ((WorldMapUtils.getSizeMap(worldMap) * amount) * MAX_LANDSCAPE_PERCENTAGE);
            for (int i = 0; i < countEntity; i++) {
                Coordinate randomCoordinate = WorldMapUtils.getRandomEmptyCoordinate(worldMap);
                Entity entity = mapper.apply(randomCoordinate);
                worldMap.addEntity(randomCoordinate, entity);
            }
    }*/

    private void spawn(WorldMap worldMap,Class<? extends Entity> clazz, Function<Coordinate, Entity> mapper, int percentage){
        int currentAmount = WorldMapUtils.getAmountEntities(worldMap,clazz);
        int maxAmount = WorldMapUtils.getSizeMap(worldMap) * percentage / 100;
        int amount = maxAmount - currentAmount;

        for (int i = 0; i < amount; i++) {
            Coordinate randomCoordinate = WorldMapUtils.getRandomEmptyCoordinate(worldMap);
            Entity entity = mapper.apply(randomCoordinate);
            worldMap.addEntity(randomCoordinate, entity);
        }
    }

}

