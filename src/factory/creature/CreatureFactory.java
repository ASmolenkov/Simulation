package factory.creature;

import pathfinding.NewPathfinder;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.entity.Creature;

public interface CreatureFactory <T extends Creature>{
    T createDefault(Coordinate position, NewPathfinder pathfinder);
    T create(Coordinate position, CreatureConfig config,NewPathfinder pathfinder);
}
