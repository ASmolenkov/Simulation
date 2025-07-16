package pathfinding;

import world.Coordinate;
import world.Entity;

import java.util.List;
import java.util.function.Predicate;

public interface Pathfinder {
    public Coordinate findNearestTarget(Coordinate start, Predicate<Entity> targetCondition);
    public List<Coordinate> findPathToTarget(Coordinate start, Coordinate target);
}
