package world;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;

public class Rabbit extends Herbivore{
    private static final String SPRITE = "🐇";

    public Rabbit(Coordinate position, int speed, int health, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health, targetExplorer, pathExplorer);
    }

    @Override
    public String getSprite() {
        return SPRITE;
    }

    @Override
    public String toString() {
        return "Rabbit{" +
                "position=" + position +
                '}';
    }

}
