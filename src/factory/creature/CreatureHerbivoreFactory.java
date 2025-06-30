package factory.creature;

import world.BFSExplorer;
import world.Coordinate;
import world.Creature;

public interface CreatureHerbivoreFactory {
    Creature createHerbivore(Coordinate position, int speed, int health, BFSExplorer explorer);
}
