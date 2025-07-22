package world;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;

public class Wolf extends Predator{

    public Wolf(Coordinate position, int speed, int health, int attackPower, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health, attackPower, targetExplorer,pathExplorer);
    }

}
