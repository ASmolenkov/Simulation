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
        List<Coordinate> path = pathfinder.findPathToTarget(this.getPosition(), Herbivore.class);
        if(!path.isEmpty()){
            Coordinate target = path.getLast();
            makeMove(worldMap, path,target);
        }

    }

    protected int getAttackPower() {
        return attackPower;
    }

    protected abstract void attack(WorldMap worldMap, Coordinate target);

    protected boolean isTargetDied(Creature creature){
        return creature.getHealth() <= MIN_HEALTH;
    }

}
