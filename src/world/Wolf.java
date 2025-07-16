package world;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;

public class Wolf extends Predator{
    private static final String SPRITE = "🐺";

    public Wolf(Coordinate position, int speed, int health, int attackPower, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health, attackPower, targetExplorer,pathExplorer);
    }

    @Override
    public String getSprite() {
        return SPRITE;
    }
}
