package factory.creature;

import pathfinding.Pathfinder;
import world.Coordinate;
import world.entity.Creature;

public interface CreatureFactory <T extends Creature>{
    T createDefault(Coordinate position, Pathfinder pathfinder);
    T create(Coordinate position, CreatureConfig config, Pathfinder pathfinder);
}
