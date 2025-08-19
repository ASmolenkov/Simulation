package pathfinding;

import world.Coordinate;
import world.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface NewPathfinder {
    public List<Coordinate> findPathToTarget(Coordinate start, Class<? extends Entity> targetClass);
    public Optional<Coordinate> findNearestTarget(Coordinate start, Class<? extends Entity> targetClass);
}
