package world;

import pathfinding.Pathfinder;

import java.util.*;

public abstract class Predator extends Creature {
    private final Pathfinder explorer;
    private int attackPower;

    public Predator(Coordinate position, int speed, int health, int attackPower, Pathfinder explorer) {
        super(position, speed, health);
        this.explorer = explorer;
        this.attackPower = attackPower;
    }
    @Override
    public void findAndMoveToTarget(MapWorld mapWorld) {
        Coordinate target = explorer.findNearestTarget(this.getPosition(), entity -> entity instanceof Herbivore);
        if (target == null || target.equals(this.getPosition())) {
            return;
        }
        List<Coordinate> pathInTarget = explorer.findPathToTarget(this.getPosition(), target);
        if (!pathInTarget.isEmpty()) {
            if (isHerbivoreNearby(mapWorld, getPosition())) {
                attack(mapWorld);

            } else if (mapWorld.getEntityPositionMap().get(pathInTarget.get(getSpeed())) instanceof Herbivore) {
                mapWorld.updatePosition(this, pathInTarget.getFirst());
            } else {
                mapWorld.updatePosition(this, pathInTarget.get(getSpeed()));
            }

        }

    }

    @Override
    public Coordinate findTarget(MapWorld mapWorld, Coordinate start) {
        return explorer.findNearestTarget(start, entity -> entity instanceof Herbivore);
    }

    private void attack(MapWorld mapWorld) {
        Coordinate target = explorer.findNearestTarget(this.getPosition(), entity -> entity instanceof Herbivore);
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
