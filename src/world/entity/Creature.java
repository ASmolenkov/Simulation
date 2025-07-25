package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.List;

public abstract class Creature extends Entity {

    protected final TargetFinder targetFinder;
    protected final Pathfinder pathfinder;

    private final int speed;
    private int health;
    private int satiety;

    public Creature(Coordinate position, int speed, int health, int satiety, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position);
        this.speed = speed;
        this.health = health;
        this.satiety = satiety;
        this.targetFinder = targetFinder;
        this.pathfinder = pathfinder;
    }

    protected abstract void starve();

    protected abstract void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target);

    protected abstract Class<? extends Entity> getTargetType();

    public void findAndMoveToTarget(MapWorld mapWorld){
        Coordinate target = targetFinder.findNearestTarget(this.getPosition(), entity -> getTargetType().isInstance(entity));
        if(target == null || target.equals(this.getPosition())){
            return;
        }
        List<Coordinate> pathToTarget = pathfinder.findPathToTarget(this.getPosition(),target);
        makeMove(mapWorld, pathToTarget,target);
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setPosition(Coordinate newPosition){
        this.position = newPosition;
    }

    public void minusSatiety(int satiety) {
        this.satiety -= satiety;
    }

    public void plusSatiety(int satiety) {
        this.satiety += satiety;
    }

    protected abstract void eat(MapWorld mapWorld);



    public void minusHealth(int health) {
        this.health -= health;
    }

    public void plusHealth(int plusHealth) {
        if(this.health <= (this.health - 1)){
            this.health += 1;
        }
        this.health += plusHealth;

    }


    protected boolean isEntityNearby(MapWorld mapWorld, Coordinate pos, Class<?> entityType) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                Coordinate neighbor = new Coordinate(
                        pos.getWidth() + dx,
                        pos.getHeight() + dy
                );
                Entity entity = mapWorld.getEntityPositionMap().get(neighbor);
                if (entityType.isInstance(entity)) {
                    return true;
                }
            }
        }
        return false;
    }

}
