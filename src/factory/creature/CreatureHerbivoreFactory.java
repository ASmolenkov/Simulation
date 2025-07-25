package factory.creature;

import pathfinding.BFSPathFinder;
import world.Coordinate;
import world.entity.Creature;

public interface CreatureHerbivoreFactory {
    Creature createHerbivore(Coordinate position, int speed, int health, BFSPathFinder explorer);
}
