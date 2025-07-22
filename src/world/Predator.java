package world;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;

import java.util.*;

public abstract class Predator extends Creature {
    private final int attackPower;

    public Predator(Coordinate position, int speed, int health, int attackPower, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        super(position,targetExplorer, pathExplorer,speed, health);
        this.attackPower = attackPower;
    }

    @Override
    protected Class<? extends Entity> getTargetType(){
        return Herbivore.class;
    }


    @Override
    public void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target){
        if (!pathInTarget.isEmpty() && mapWorld.isWithinBounds(pathInTarget.getFirst())) {
            if (isEntityNearby(mapWorld, position,getTargetType())) {
                System.out.println("Волк атакует");
                attack(mapWorld);
            }
            else if(pathInTarget.size() == 1){
                mapWorld.updatePosition(this, pathInTarget.getFirst());
            }
            else {
                mapWorld.updatePosition(this, pathInTarget.get(getSpeed()));
            }
        }
    }

    private void attack(MapWorld mapWorld) {
        Coordinate target = targetFinder.findNearestTarget(this.position,entity -> entity instanceof Herbivore);
        Creature creature = (Creature) mapWorld.getEntityPositionMap().get(target);
        creature.minusHealth(attackPower);
    }

}
