package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;

public class Rabbit extends Herbivore {

    public Rabbit(Coordinate position, int speed, int health, int satiety, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position, speed, health, satiety, targetFinder, pathfinder);
    }

    @Override
    public String toString() {
        return "Rabbit{" +
                "position=" + position +
                '}';
    }

}
