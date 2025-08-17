package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;

public abstract class Herbivore extends Creature {

    public Herbivore(Coordinate position, int speed, int health, int satiety, int maxSearchDepth, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health, satiety, maxSearchDepth, targetExplorer, pathExplorer);

    }

}




