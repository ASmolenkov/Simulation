package world.entity;

import pathfinding.Pathfinder;
import world.Coordinate;
import world.WorldMap;

import java.util.List;


public abstract class Predator extends Creature {

    private final int attackPower;

    public Predator(Coordinate position, int speed, int health, int attackPower, int satiety, int maxSearchDepth, Pathfinder pathfinder) {
        super(position,speed, health, satiety, maxSearchDepth,pathfinder);
        this.attackPower = attackPower;
    }

    public void performMovementAction(WorldMap worldMap){
        Coordinate target = findTarget(this.getPosition(), Herbivore.class);
        List<Coordinate> path = findPathToTarget(Herbivore.class);
        makeMove(worldMap, path,target);
    }

    protected int getAttackPower() {
        return attackPower;
    }

    protected abstract void attack(WorldMap worldMap);

    protected boolean isTargetDied(Creature creature){
        return creature.getHealth() <= MIN_HEALTH;
    }

}
