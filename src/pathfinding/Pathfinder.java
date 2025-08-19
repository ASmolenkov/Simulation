package pathfinding;

import world.Coordinate;
import world.entity.Entity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface Pathfinder {
    public List<Coordinate> findPathToTarget(Coordinate start, Coordinate target);


}
