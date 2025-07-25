package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.*;

public abstract class Herbivore extends Creature {

    public Herbivore(Coordinate position, int speed, int health, int satiety, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health, satiety, targetExplorer, pathExplorer);

    }
    @Override
    protected Class<? extends Entity> getTargetType(){
        return Grass.class;
    }

    @Override
    protected void starve(){
        this.minusSatiety(1);
    }

    @Override
    public void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target){
        if(!pathInTarget.isEmpty() && mapWorld.isWithinBounds(pathInTarget.getFirst())){
            if(isEntityNearby(mapWorld, position,getTargetType())){
                eat(mapWorld);
                System.out.println("Кролик съел траву");
                mapWorld.getEntityPositionMap().put(target, new EmptyArea(target));
            }
            else if(pathInTarget.size() == 1){
                mapWorld.updatePosition(this, pathInTarget.getFirst());
                this.starve();
            }
            else {
                mapWorld.updatePosition(this, pathInTarget.get(getSpeed()));
                this.starve();
            }
        }
    }

    protected void eat(MapWorld mapWorld) {
        this.plusHealth(1);
        this.plusSatiety(1);
    }

}




