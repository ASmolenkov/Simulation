package world;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;

public class Rabbit extends Herbivore{

    public Rabbit(Coordinate position, int speed, int health, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health, targetExplorer, pathExplorer);
    }

    @Override
    public String toString() {
        return "Rabbit{" +
                "position=" + position +
                '}';
    }

}
