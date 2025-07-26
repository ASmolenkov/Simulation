package world.entity;

import listener.EventType;
import listener.SimulationEvent;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.*;

public abstract class Predator extends Creature {
    private static final int LIFE_BONUS_FOR_FOOD = 2;
    private static final int SATIETY_BONUS_FOR_FOOD = 5;
    private static final int MAX_HEALTH = 10;

    private final int attackPower;

    public Predator(Coordinate position, int speed, int health, int attackPower,  int satiety, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position,speed, health, satiety, targetFinder, pathfinder);
        this.attackPower = attackPower;
    }

    @Override
    protected Class<? extends Entity> getTargetType(){
        return Herbivore.class;
    }


    @Override
    public void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target){
        if (!pathInTarget.isEmpty() && mapWorld.isWithinBounds(pathInTarget.getFirst())) {
            if (isEntityNearby(mapWorld, position, getTargetType())) {
                attack(mapWorld);
                if(isTargetDied(mapWorld, target)){
                    this.eat(mapWorld);
                }
            }
            else if(pathInTarget.size() == 1){
                mapWorld.updatePosition(this, pathInTarget.getFirst());
            }
            else {
                mapWorld.updatePosition(this, pathInTarget.get(getSpeed()));
            }
        }
    }

    @Override
    public  void plusHealth(int plusHealth) {
        this.health += plusHealth;
        if(this.health > MAX_HEALTH){
            this.health = MAX_HEALTH;
        }
    }

    protected void eat(MapWorld mapWorld) {
        this.plusHealth(LIFE_BONUS_FOR_FOOD);
        this.plusSatiety(SATIETY_BONUS_FOR_FOOD);
    }

    protected void attack(MapWorld mapWorld) {
        Coordinate target = targetFinder.findNearestTarget(this.position,entity -> entity instanceof Herbivore);
        Creature creature = (Creature) mapWorld.getEntityPositionMap().get(target);
        creature.minusHealth(attackPower);
        notifyAttack(mapWorld, creature, target);
    }

    protected void notifyAttack(MapWorld world, Creature target, Coordinate targetPosition) {
        String message = String.format("⚔️ %s attacked %s at %s",
                getClass().getSimpleName(),
                target.getClass().getSimpleName(),
                position);
        world.notifyListeners(new SimulationEvent(EventType.ATTACK, message, this));
    }



    private boolean isTargetDied(MapWorld mapWorld, Coordinate target){
        if(mapWorld.getEntityPositionMap().get(target) instanceof Herbivore herbivore){
            return herbivore.getHealth() <= MIN_HEALTH;
        }
        return false;
    }

}
