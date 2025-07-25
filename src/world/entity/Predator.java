package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.*;

public abstract class Predator extends Creature {
    private final int attackPower;

    public Predator(Coordinate position, int speed, int health, int attackPower,  int satiety, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position,speed, health, satiety, targetFinder, pathfinder);
        this.attackPower = attackPower;
    }

    @Override
    protected Class<? extends Entity> getTargetType(){
        return Herbivore.class;
    }


    @Override
    public void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target){
        if (!pathInTarget.isEmpty() && mapWorld.isWithinBounds(pathInTarget.getFirst())) {
            if (isEntityNearby(mapWorld, position, getTargetType())) {
                System.out.println("Волк атакует");
                attack(mapWorld);
                if(isTargetDied(mapWorld, target)){
                    eat(mapWorld);
                }
            }
            else if(pathInTarget.size() == 1){
                mapWorld.updatePosition(this, pathInTarget.getFirst());
                starve();
            }
            else {
                mapWorld.updatePosition(this, pathInTarget.get(getSpeed()));
                starve();
            }
        }
    }

    private void attack(MapWorld mapWorld) {
        Coordinate target = targetFinder.findNearestTarget(this.position,entity -> entity instanceof Herbivore);
        Creature creature = (Creature) mapWorld.getEntityPositionMap().get(target);
        creature.minusHealth(attackPower);
    }

    protected void eat(MapWorld mapWorld) {
        this.plusHealth(2);
        this.plusSatiety(2);

    }

    protected void starve(){
        this.minusSatiety(1);
    }

    private boolean isTargetDied(MapWorld mapWorld, Coordinate target){
        if(mapWorld.getEntityPositionMap().get(target) instanceof Herbivore herbivore){
            return herbivore.getSatiety() <= 0;
        }
        return false;
    }

}
