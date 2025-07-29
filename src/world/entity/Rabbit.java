package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;

public class Rabbit extends Herbivore {
    public Rabbit(Coordinate position, int speed, int health, int satiety,int maxSearchDepth, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position, speed, health, satiety, maxSearchDepth, targetFinder, pathfinder);

    }

}
