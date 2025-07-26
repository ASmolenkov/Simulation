package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.List;
import java.util.Map;

public abstract class Creature extends Entity {
    private static final int MAX_SATIETY = 10;
    private static final int MIN_SATIETY = 0;
    protected static final int MIN_HEALTH = 0;

    protected final TargetFinder targetFinder;
    protected final Pathfinder pathfinder;

    private final int speed;
    protected int health;
    private int satiety;


    public Creature(Coordinate position, int speed, int health, int satiety, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position);
        this.speed = speed;
        this.health = health;
        this.satiety = satiety;
        this.targetFinder = targetFinder;
        this.pathfinder = pathfinder;
    }

    public abstract void plusHealth(int plusHealth);

    protected abstract void eat(MapWorld mapWorld);

    protected abstract void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target);

    protected abstract Class<? extends Entity> getTargetType();

    public void starve(){
        this.minusSatiety(1);
    }


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
        if(this.satiety < MIN_SATIETY){
            this.satiety = MIN_SATIETY;
        }
    }

    public void plusSatiety(int satiety) {
        this.satiety += satiety;
        if(this.satiety > MAX_SATIETY){
            this.satiety = MAX_SATIETY;
        }
    }

    public void minusHealth(int health) {
        this.health -= health;
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
