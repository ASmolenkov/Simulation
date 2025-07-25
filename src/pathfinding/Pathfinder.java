package pathfinding;

import world.Coordinate;

import java.util.List;

public interface Pathfinder {
    public List<Coordinate> findPathToTarget(Coordinate start, Coordinate target);
}
