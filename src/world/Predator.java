package world;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;

import java.util.*;

public abstract class Predator extends Creature {
    private int attackPower;
    private final TargetFinder targetExplorer;
    private final Pathfinder pathExplorer;

    public Predator(Coordinate position, int speed, int health, int attackPower, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health);
        this.targetExplorer = targetExplorer;
        this.pathExplorer = pathExplorer;
        this.attackPower = attackPower;
    }

    @Override
    protected Class<? extends Entity> getTargetType(){
        return Herbivore.class;
    }

    @Override
    public void findAndMoveToTarget(MapWorld mapWorld) {
        Coordinate target = targetExplorer.findNearestTarget(this.getPosition(), entity -> entity instanceof Herbivore);
        if (target == null || target.equals(this.getPosition())) {
            return;
        }
        List<Coordinate> pathInTarget = pathExplorer.findPathToTarget(this.getPosition(), target);
        makeMove(mapWorld,pathInTarget,target);
    }

    @Override
    public void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target){
        if (!pathInTarget.isEmpty() && mapWorld.isWithinBounds(pathInTarget.getFirst())) {
            if (isHerbivoreNearby(mapWorld, getPosition())) {
                attack(mapWorld);

            } else if (mapWorld.getEntityPositionMap().get(pathInTarget.get(getSpeed())) instanceof Herbivore) {
                mapWorld.updatePosition(this, pathInTarget.getFirst());
            } else {
                mapWorld.updatePosition(this, pathInTarget.get(getSpeed()));
            }

        }
    }

    private void attack(MapWorld mapWorld) {
        Coordinate target = targetExplorer.findNearestTarget(this.getPosition(), entity -> entity instanceof Herbivore);
        Creature creature = (Creature) mapWorld.getEntityPositionMap().get(target);
        creature.minusHealth(attackPower);
    }

    private boolean isHerbivoreNearby(MapWorld mapWorld, Coordinate currentPosition) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                Coordinate neighbor = new Coordinate(currentPosition.getWidth() + dx, currentPosition.getHeight() + dy);
                Entity entity = mapWorld.getEntityPositionMap().get(neighbor);

                if (entity instanceof Herbivore) {
                    return true;
                }
            }
        }
        return false;
    }
}
