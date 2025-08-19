package world.entity;

import pathfinding.NewPathfinder;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.WorldMap;

import java.util.List;

public abstract class Herbivore extends Creature {

    public Herbivore(Coordinate position, int speed, int health, int satiety, int maxSearchDepth, NewPathfinder pathExplorer) {
        super(position, speed, health, satiety, maxSearchDepth, pathExplorer);

    }

    public void performMovementAction(WorldMap worldMap){
        Coordinate target = findTarget(this.getPosition(), Grass.class);
        List<Coordinate> path = findPathToTarget(Grass.class);
        makeMove(worldMap, path,target);
    }

}




