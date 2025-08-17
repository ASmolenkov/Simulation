package world.entity;

import listener.EventType;
import listener.SimulationEvent;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.WorldMap;

import java.util.List;

public class Rabbit extends Herbivore {
    private static final int LIFE_BONUS_FOR_FOOD = 1;
    private static final int SATIETY_BONUS_FOR_FOOD = 3;
    private static final int MAX_HEALTH = 5;

    public Rabbit(Coordinate position, int speed, int health, int satiety,int maxSearchDepth, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position, speed, health, satiety, maxSearchDepth, targetFinder, pathfinder);

    }

    @Override
    public void makeMove(WorldMap worldMap, List<Coordinate> pathInTarget, Coordinate target){
        if(!pathInTarget.isEmpty() && worldMap.isWithinBounds(pathInTarget.getFirst())){
            if(isTargetNearby(worldMap, position,getTargetType())){
                eat(worldMap);
                worldMap.subEntity(target);
                notifyEat(worldMap,target);
                return;
            }
            int stepsToMove = Math.min(this.getSpeed(), pathInTarget.size());

            movement(this, pathInTarget.get(stepsToMove - 1), worldMap);
        }
    }

    @Override
    public  void plusHealth(int plusHealth) {
        this.health += plusHealth;
        if(this.health > MAX_HEALTH){
            this.health = MAX_HEALTH;
        }
    }

    @Override
    protected Class<? extends Entity> getTargetType(){
        return Grass.class;
    }

    @Override
    protected Class<? extends Entity> getEnemyType(){
        return Predator.class;
    }

    @Override
    protected void eat(WorldMap worldMap) {
        this.plusHealth(LIFE_BONUS_FOR_FOOD);
        this.plusSatiety(SATIETY_BONUS_FOR_FOOD);
    }

    protected void notifyEat(WorldMap world, Coordinate targetPosition) {
        String message = String.format("🍴 %s eat Grass to %s",
                getClass().getSimpleName(), targetPosition.toString());
        world.notifyListeners(new SimulationEvent(EventType.EAT, message, this));
    }

}
