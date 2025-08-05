package world.entity;

import listener.EventType;
import listener.SimulationEvent;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.*;

public abstract class Herbivore extends Creature {

    public Herbivore(Coordinate position, int speed, int health, int satiety, int maxSearchDepth, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health, satiety, maxSearchDepth, targetExplorer, pathExplorer);

    }

}




