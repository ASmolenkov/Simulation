package world;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;

import java.util.*;

public abstract class Herbivore extends Creature {
    private final TargetFinder targetExplorer;
    private final Pathfinder pathExplorer;


    public Herbivore(Coordinate position, int speed, int health, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health);
        this.targetExplorer = targetExplorer;
        this.pathExplorer = pathExplorer;
    }

    @Override
    public void findAndMoveToTarget(MapWorld mapWorld){
        Coordinate target = targetExplorer.findNearestTarget(this.getPosition(), entity -> entity instanceof Grass);
        if(target == null || target.equals(this.getPosition())){
            return;
        }
        List<Coordinate> pathInTarget = pathExplorer.findPathToTarget(this.getPosition(), target);
        if(!pathInTarget.isEmpty()){

            if(isHerbivoreNearby(mapWorld, this.position)){
                eatGrass(mapWorld);
                mapWorld.getEntityPositionMap().put(target, new EmptyArea(target));
            } else if (mapWorld.getEntityPositionMap().get(pathInTarget.get(getSpeed())) instanceof Grass) {
                mapWorld.updatePosition(this, pathInTarget.getFirst());
            } else {
                mapWorld.updatePosition(this, pathInTarget.get(getSpeed()));
            }
        }
    }

    @Override
    public Coordinate findTarget(MapWorld mapWorld, Coordinate start) {
        return targetExplorer.findNearestTarget(start, entity -> entity instanceof Grass);
    }

    private void eatGrass(MapWorld mapWorld) {
        Coordinate target = targetExplorer.findNearestTarget(this.getPosition(), entity -> entity instanceof Herbivore);
        Creature creature = (Creature) mapWorld.getEntityPositionMap().get(target);
        creature.plusHealth();
    }

    private boolean isHerbivoreNearby(MapWorld mapWorld, Coordinate currentPosition) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                Coordinate neighbor = new Coordinate(currentPosition.getWidth() + dx, currentPosition.getHeight() + dy);
                Entity entity = mapWorld.getEntityPositionMap().get(neighbor);
                if (entity instanceof Grass) {
                    return true;
                }
            }
        }
        return false;
    }
}




