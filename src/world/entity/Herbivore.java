package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.*;

public abstract class Herbivore extends Creature {

    private static final int LIFE_BONUS_FOR_FOOD = 1;
    private static final int SATIETY_BONUS_FOR_FOOD = 3;

    public Herbivore(Coordinate position, int speed, int health, int satiety, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, speed, health, satiety, targetExplorer, pathExplorer);

    }
    @Override
    protected Class<? extends Entity> getTargetType(){
        return Grass.class;
    }


    @Override
    public void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target){
        if(!pathInTarget.isEmpty() && mapWorld.isWithinBounds(pathInTarget.getFirst())){
            if(isEntityNearby(mapWorld, position,getTargetType())){
                eat(mapWorld);
                mapWorld.getEntityPositionMap().put(target, new EmptyArea(target));
            }
            else if(pathInTarget.size() == 1){
                mapWorld.updatePosition(this, pathInTarget.getFirst());

            }
            else {
                mapWorld.updatePosition(this, pathInTarget.get(getSpeed()));

            }
        }
    }

    protected void eat(MapWorld mapWorld) {
        this.plusHealth(LIFE_BONUS_FOR_FOOD);
        this.plusSatiety(SATIETY_BONUS_FOR_FOOD);
    }
}




