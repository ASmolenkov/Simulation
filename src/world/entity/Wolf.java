package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;

public class Wolf extends Predator {

    public Wolf(Coordinate position, int speed, int health, int attackPower, int satiety, int maxSearchDepth, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position, speed, health, attackPower, satiety, maxSearchDepth, targetFinder,pathfinder);
    }

}
