package world.entity;

import listener.EventType;
import listener.SimulationEvent;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.List;

public class Rabbit extends Herbivore {
    private static final int LIFE_BONUS_FOR_FOOD = 1;
    private static final int SATIETY_BONUS_FOR_FOOD = 3;
    private static final int MAX_HEALTH = 5;

    public Rabbit(Coordinate position, int speed, int health, int satiety,int maxSearchDepth, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position, speed, health, satiety, maxSearchDepth, targetFinder, pathfinder);

    }

    @Override
    public void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target){
        if(!pathInTarget.isEmpty() && mapWorld.isWithinBounds(pathInTarget.getFirst())){
            if(isTargetNearby(mapWorld, position,getTargetType())){
                eat(mapWorld);
                mapWorld.getEntityPositionMap().put(target, new EmptyArea(target));
                notifyEat(mapWorld,target);
                return;
            }
            int stepsToMove = Math.min(this.getSpeed(), pathInTarget.size());

            movement(this, pathInTarget.get(stepsToMove - 1),mapWorld);
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
    protected void eat(MapWorld mapWorld) {
        this.plusHealth(LIFE_BONUS_FOR_FOOD);
        this.plusSatiety(SATIETY_BONUS_FOR_FOOD);
    }

    protected void notifyEat(MapWorld world, Coordinate targetPosition) {
        String message = String.format("🍴 %s eat Grass to %s",
                getClass().getSimpleName(), targetPosition.toString());
        world.notifyListeners(new SimulationEvent(EventType.EAT, message, this));
    }

}
