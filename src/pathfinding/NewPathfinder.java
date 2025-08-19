package pathfinding;

import world.Coordinate;
import world.entity.Entity;

import java.util.List;

public interface NewPathfinder {
    public List<Coordinate> findPathToTarget(Coordinate start, Class<? extends Entity> targetClass);
}
