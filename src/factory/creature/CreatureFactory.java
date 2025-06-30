package factory.creature;

import world.BFSExplorer;
import world.Coordinate;
import world.Creature;

public interface CreatureFactory <T extends Creature>{
    T createDefault(Coordinate position, BFSExplorer explorer);
    T create(Coordinate position, CreatureConfig config, BFSExplorer explorer);
}
