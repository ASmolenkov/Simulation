package actions.turn;

import actions.Action;
import factory.creature.CreatureFactory;
import listener.EventType;
import listener.SimulationEvent;
import pathfinding.Pathfinder;
import util.WorldMapUtils;
import world.Coordinate;
import world.WorldMap;
import world.entity.*;

public class MapReplenishmentAction implements Action {
    private static final double TARGET_HERBIVORE_PERCENTAGE = 0.04;
    private static final double TARGET_PREDATOR_PERCENTAGE = 0.015;
    private static final double TARGET_GRASS_PERCENTAGE = 0.06;

    private final WorldMap worldMap;
    private final CreatureFactory<Wolf> wolfFactory;
    private final CreatureFactory<Rabbit> rabbitFactory;
    private final Pathfinder pathfinder;

    public MapReplenishmentAction(WorldMap worldMap, CreatureFactory<Wolf> wolfFactory, CreatureFactory<Rabbit> rabbitFactory, Pathfinder pathfinder) {
        this.worldMap = worldMap;
        this.wolfFactory = wolfFactory;
        this.rabbitFactory = rabbitFactory;
        this.pathfinder = pathfinder;
    }

    @Override
    public void perform() {
        refillGrassOnMap(worldMap);
        refillCreatureOnMap(worldMap, WorldMapUtils.getAmountHerbivore(worldMap), TARGET_HERBIVORE_PERCENTAGE,rabbitFactory);
        refillCreatureOnMap(worldMap, WorldMapUtils.getAmountPredator(worldMap), TARGET_PREDATOR_PERCENTAGE,wolfFactory);
    }

    private void refillGrassOnMap(WorldMap worldMap){
        int targetAmountGrass = (int) (WorldMapUtils.getSizeMap(worldMap) * TARGET_GRASS_PERCENTAGE);
        int amountGrass = WorldMapUtils.getAmountGrass(worldMap);
        if(amountGrass < targetAmountGrass){
            for (int i = amountGrass; i <= targetAmountGrass ; i++) {
                Coordinate coordinate = WorldMapUtils.getRandomEmptyCoordinate(worldMap);
                worldMap.addEntity(coordinate, new Grass());
                notifyGrowing(coordinate);
            }
        }

    }
    private void refillCreatureOnMap(WorldMap worldMap, int amount, double minAmountPercentage, CreatureFactory<? extends Creature> creatureFactory){
        int minAmount = (int) (minAmountPercentage * WorldMapUtils.getSizeMap(worldMap));
        if(amount < minAmount){
            for (int i = amount; i <= minAmount ; i++) {
                Coordinate coordinate = WorldMapUtils.getRandomEmptyCoordinate(worldMap);
                Creature creature = creatureFactory.createDefault(coordinate, pathfinder);
                worldMap.addEntity(coordinate, creature);
                notifyBirth(creature);
            }
        }
    }
    private void notifyBirth(Creature creature){
        worldMap.notifyListeners(new SimulationEvent(EventType.ENTITY_SPAWNED, String.format("🙏 A new one was born %s", creature.getClass().getSimpleName()),creature));
    }
    private void notifyGrowing(Coordinate coordinate){
        worldMap.notifyListeners(new SimulationEvent(EventType.GRASS_GROWING, String.format("🌿 the grass is growing %s", coordinate)));
    }
}
