package world;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;

import java.util.*;

public abstract class Herbivore extends Creature {

    public Herbivore(Coordinate position, int speed, int health, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position, targetExplorer, pathExplorer, speed, health);

    }
    @Override
    protected Class<? extends Entity> getTargetType(){
        return Grass.class;
    }

    @Override
    public void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target){
        if(!pathInTarget.isEmpty() && mapWorld.isWithinBounds(pathInTarget.getFirst())){
            if(isEntityNearby(mapWorld, position,getTargetType())){
                eatGrass(mapWorld);
                System.out.println("Кролик съел траву");
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

    private void eatGrass(MapWorld mapWorld) {
        this.plusHealth();
    }

}




