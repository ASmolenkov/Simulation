package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.List;

public abstract class Creature extends Entity {
    public static final int MAX_SATIETY = 10;
    private static final int MIN_SATIETY = 0;
    protected static final int MIN_HEALTH = 0;

    protected final TargetFinder targetFinder;
    protected final Pathfinder pathfinder;

    protected final int maxSearchDepth;
    private int speed;
    protected int health;
    private int satiety;
    private boolean speedBoosted = false;


    public Creature(Coordinate position, int speed, int health, int satiety, int maxSearchDepth, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position);
        this.speed = speed;
        this.health = health;
        this.satiety = satiety;
        this.targetFinder = targetFinder;
        this.pathfinder = pathfinder;
        this.maxSearchDepth = maxSearchDepth;
    }

    public abstract void plusHealth(int plusHealth);

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getSatiety() {
        return satiety;
    }

    public int getMaxSearchDepth() {
        return maxSearchDepth;
    }

    public void setPosition(Coordinate newPosition){
        this.position = newPosition;
    }

    public void minusHealth(int health) {
        this.health -= health;
    }

    public void starve(){
        this.minusSatiety(1);
    }


    public void performMovementAction(MapWorld mapWorld){
        Coordinate target = findTarget(mapWorld);
        List<Coordinate> path = findPathToTarget(target, mapWorld);
        makeMove(mapWorld, path,target);
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

    public boolean isSpeedBoosted() {
        return speedBoosted;
    }

    public void setSpeedBoosted(boolean speedBoosted) {
        this.speedBoosted = speedBoosted;
    }

    public void setTemporarySpeed(int temporarySpeed){
        this.speed = temporarySpeed;
    }



    protected abstract void eat(MapWorld mapWorld);

    protected abstract void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target);

    protected abstract Class<? extends Entity> getTargetType();

    protected abstract Class<? extends Entity> getEnemyType();

    protected void movement(Creature creature, Coordinate newPosition ,MapWorld mapWorld){
        mapWorld.updatePosition(creature, newPosition);
    }

    protected boolean isTargetNearby(MapWorld mapWorld, Coordinate pos, Class<?> entityType) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                Coordinate neighbor = new Coordinate(
                        pos.width() + dx,
                        pos.height() + dy
                );
                Entity entity = mapWorld.getEntityPositionMap().get(neighbor);
                if (entityType.isInstance(entity)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Coordinate findTarget(MapWorld mapWorld) {
        Coordinate target = targetFinder.findNearestTarget(this, this.getPosition(), entity -> getTargetType().isInstance(entity));
        if(target == null || target.equals(this.getPosition())){
            return targetFinder.findFreeCell(this, mapWorld);
        }
        return target;
    }

    private List<Coordinate> findPathToTarget(Coordinate target, MapWorld mapWorld) {
        return pathfinder.findPathToTarget(this.getPosition(), target);
    }


}
