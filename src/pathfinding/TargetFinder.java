package pathfinding;

import world.Coordinate;
import world.Entity;

import java.util.function.Predicate;

public interface TargetFinder {
    public Coordinate findNearestTarget(Coordinate start, Predicate<Entity> targetCondition);
}
