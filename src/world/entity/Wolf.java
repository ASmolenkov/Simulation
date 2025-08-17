package world.entity;

import listener.EventType;
import listener.SimulationEvent;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.WorldMap;

import java.util.List;

public class Wolf extends Predator {

    private static final int LIFE_BONUS_FOR_FOOD = 2;
    private static final int SATIETY_BONUS_FOR_FOOD = 5;
    private static final int MAX_HEALTH = 10;

    public Wolf(Coordinate position, int speed, int health, int attackPower, int satiety, int maxSearchDepth, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position, speed, health, attackPower, satiety, maxSearchDepth, targetFinder,pathfinder);
    }

    @Override
    protected Class<? extends Entity> getTargetType(){
        return Herbivore.class;
    }

    @Override
    protected Class<? extends Entity> getEnemyType(){
        return Predator.class;
    }


    @Override
    public void makeMove(WorldMap worldMap, List<Coordinate> pathInTarget, Coordinate target){
        if (!pathInTarget.isEmpty() && worldMap.isWithinBounds(pathInTarget.getFirst())) {
            if (isTargetNearby(worldMap, position, getTargetType())) {
                attack(worldMap);
                if(isTargetDied((Creature) worldMap.getEntityPosition().get(target))){
                    this.eat(worldMap);

                }
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
    protected void eat(WorldMap worldMap) {
        this.plusHealth(LIFE_BONUS_FOR_FOOD);
        this.plusSatiety(SATIETY_BONUS_FOR_FOOD);
    }
    @Override
    protected void attack(WorldMap worldMap) {
        Coordinate target = targetFinder.findNearestTarget(this, this.position,entity -> entity instanceof Herbivore);
        Creature creature = (Creature) worldMap.getEntityPosition().get(target);
        creature.minusHealth(this.getAttackPower());
        notifyAttack(worldMap, creature, target);
    }
    protected void notifyAttack(WorldMap world, Creature target, Coordinate targetPosition) {
        String message = String.format("⚔️ %s attacked %s at %s",
                getClass().getSimpleName(),
                target.getClass().getSimpleName(),
                position);
        world.notifyListeners(new SimulationEvent(EventType.ATTACK, message, this));
    }

}
