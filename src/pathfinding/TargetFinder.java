package pathfinding;

import world.Coordinate;
import world.entity.Creature;
import world.entity.Entity;

import java.util.function.Predicate;

public interface TargetFinder {
    public Coordinate findNearestTarget(Creature creature, Coordinate start, Predicate<Entity> targetCondition);
}
