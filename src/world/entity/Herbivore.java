package world.entity;

import pathfinding.Pathfinder;
import world.Coordinate;
import world.WorldMap;

import java.util.List;

public abstract class Herbivore extends Creature {

    public Herbivore(Coordinate position, int speed, int health, int satiety, int maxSearchDepth, Pathfinder pathExplorer) {
        super(position, speed, health, satiety, maxSearchDepth, pathExplorer);

    }

    public void performMovementAction(WorldMap worldMap){
        List<Coordinate> path = pathfinder.findPathToTarget(this.getPosition(), Grass.class);
        if(!path.isEmpty()) {
            Coordinate target = path.getLast();
            makeMove(worldMap, path, target);
        }
    }

}




