package factory.creature;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.entity.Creature;

public interface CreatureFactory <T extends Creature>{
    T createDefault(Coordinate position, TargetFinder targetExplorer, Pathfinder pathExplorer);
    T create(Coordinate position, CreatureConfig config, TargetFinder targetExplorer, Pathfinder pathExplorer);
}
