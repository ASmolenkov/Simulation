package world;

import java.util.*;

public abstract class Herbivore extends Creature {
    private final BFSExplorer explorer;


    public Herbivore(Coordinate position, int speed, int health, BFSExplorer explorer) {
        super(position, speed, health);
        this.explorer = explorer;
    }

    @Override
    public void findAndMoveToTarget(MapWorld mapWorld){
        Coordinate target = explorer.findNearestTarget(this.getPosition(), entity -> entity instanceof Grass);
        if(target == null || target.equals(this.getPosition())){
            return;
        }
        List<Coordinate> pathInTarget = explorer.findPathToCoordinate(this.getPosition(), target);
        if(!pathInTarget.isEmpty()){

            if(isHerbivoreNearby(mapWorld, this.position)){
                attack(mapWorld);
                mapWorld.getEntityPositionMap().put(target, new EmptyArea(target));
            }
            else {
                mapWorld.updatePosition(this, pathInTarget.get(getSpeed()));
            }
        }
    }

    @Override
    public Coordinate findTarget(MapWorld mapWorld, Coordinate start) {
        return explorer.findNearestTarget(start, entity -> entity instanceof Grass);
    }

    private void attack(MapWorld mapWorld) {
        Coordinate target = explorer.findNearestTarget(this.getPosition(), entity -> entity instanceof Herbivore);
        Creature creature = (Creature) mapWorld.getEntityPositionMap().get(target);
        creature.plusHealth();
    }

    private boolean isHerbivoreNearby(MapWorld mapWorld, Coordinate currentPosition) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                Coordinate neighbor = new Coordinate(currentPosition.getWidth() + dx, currentPosition.getHeight() + dy);
                Entity entity = mapWorld.getEntityPositionMap().get(neighbor);
                if (entity instanceof Grass) {
                    return true;
                }
            }
        }
        return false;
    }
}




