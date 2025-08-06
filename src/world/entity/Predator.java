package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;


public abstract class Predator extends Creature {

    private final int attackPower;

    public Predator(Coordinate position, int speed, int health, int attackPower, int satiety, int maxSearchDepth, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position,speed, health, satiety, maxSearchDepth, targetFinder, pathfinder);
        this.attackPower = attackPower;
    }

    protected int getAttackPower() {
        return attackPower;
    }

    protected abstract void attack(MapWorld mapWorld);

    protected boolean isTargetDied(MapWorld mapWorld, Coordinate target){
        if(mapWorld.getEntityPositionMap().get(target) instanceof Creature creature){
            return creature.getHealth() <= MIN_HEALTH;
        }
        return false;
    }

}
