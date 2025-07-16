package factory.creature;

import pathfinding.Pathfinder;
import world.Coordinate;
import world.Creature;

public interface CreatureFactory <T extends Creature>{
    T createDefault(Coordinate position, Pathfinder explorer);
    T create(Coordinate position, CreatureConfig config, Pathfinder explorer);
}
