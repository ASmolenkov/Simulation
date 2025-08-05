package world.entity;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.MapWorld;

import java.util.List;

public abstract class Creature extends Entity {
    private static final int MAX_SATIETY = 10;
    private static final int MIN_SATIETY = 0;
    protected static final int MIN_HEALTH = 0;

    protected final TargetFinder targetFinder;
    protected final Pathfinder pathfinder;

    protected final int maxSearchDepth;
    private final int speed;
    protected int health;
    private int satiety;


    public Creature(Coordinate position, int speed, int health, int satiety, int maxSearchDepth, TargetFinder targetFinder, Pathfinder pathfinder) {
        super(position);
        this.speed = speed;
        this.health = health;
        this.satiety = satiety;
        this.targetFinder = targetFinder;
        this.pathfinder = pathfinder;
        this.maxSearchDepth = maxSearchDepth;
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

    public int getMaxSearchDepth() {
        return maxSearchDepth;
    }

    public void setPosition(Coordinate newPosition){
        this.position = newPosition;
    }

    public abstract void plusHealth(int plusHealth);

    public void minusHealth(int health) {
        this.health -= health;
    }

    public void starve(){
        this.minusSatiety(1);
    }


    public void findAndMoveToTarget(MapWorld mapWorld){
        Coordinate target = targetFinder.findNearestTarget(this, this.getPosition(), entity -> getTargetType().isInstance(entity));

        if(target == null || target.equals(this.getPosition())){
            Coordinate freeCell = targetFinder.findFreeCell(this, mapWorld);
            List<Coordinate> pathToFreeCell = pathfinder.findPathToTarget(this.getPosition(), freeCell);
            makeMove(mapWorld, pathToFreeCell, freeCell);
            return;
        }
        List<Coordinate> pathToTarget = pathfinder.findPathToTarget(this.getPosition(),target);
        makeMove(mapWorld, pathToTarget,target);
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
