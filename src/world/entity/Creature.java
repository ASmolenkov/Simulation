package world.entity;

import pathfinding.AStarPathfinder;
import pathfinding.NewPathfinder;
import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.WorldMap;

import java.util.List;
import java.util.Optional;

public abstract class Creature extends Entity {
    public static final int MAX_SATIETY = 10;
    private static final int MIN_SATIETY = 0;
    protected static final int MIN_HEALTH = 0;

    protected Coordinate position;
    protected final NewPathfinder pathfinder;
    protected final int maxSearchDepth;
    private int speed;
    protected int health;
    private int satiety;
    private boolean speedBoosted = false;


    public Creature(Coordinate position, int speed, int health, int satiety, int maxSearchDepth, NewPathfinder pathfinder) {
        this.position = position;
        this.speed = speed;
        this.health = health;
        this.satiety = satiety;
        this.pathfinder = pathfinder;
        this.maxSearchDepth = maxSearchDepth;
    }

    public Coordinate getPosition() {
        return position;
    }

    public  void addHealth(int health){
        this.health += health;
        if(this.health > getMaxHealth()){
            this.health = getMaxHealth();
        }
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

    public void subHealth(int health) {
        this.health -= health;
    }

    public void starve(){
        this.subSatiety(1);
    }


    public abstract void performMovementAction(WorldMap worldMap);
    protected abstract int getMaxHealth();



    public void subSatiety(int satiety) {
        this.satiety -= satiety;
        if(this.satiety < MIN_SATIETY){
            this.satiety = MIN_SATIETY;
        }
    }

    public void addSatiety(int satiety) {
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



    protected abstract void eat(WorldMap worldMap);

    protected abstract void makeMove(WorldMap worldMap, List<Coordinate> pathInTarget, Coordinate target);

    protected abstract Class<? extends Entity> getTargetType();

    protected abstract Class<? extends Entity> getEnemyType();

    protected void movement(Creature creature, Coordinate newPosition , WorldMap worldMap){
        worldMap.updatePosition(creature, newPosition);
    }

    protected boolean isTargetNearby(WorldMap worldMap, Coordinate pos, Class<?> entityType) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0){
                    continue;
                }
                Coordinate neighbor = new Coordinate(pos.width() + dx, pos.height() + dy);
                if(!worldMap.isWithinBounds(neighbor)){
                    continue;
                }
                if(worldMap.isFreePosition(neighbor)){
                    continue;
                }
                else {
                    Entity entity = worldMap.getEntity(neighbor);
                    if (entityType.isInstance(entity)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    protected Coordinate findTarget(Coordinate start, Class<? extends Entity> targetClass){
        Optional<Coordinate> target = pathfinder.findNearestTarget(start, targetClass);
        return target.orElse(start);

    }

    protected List<Coordinate> findPathToTarget(Class<? extends Entity> targetClass) {
        return pathfinder.findPathToTarget(this.getPosition(), targetClass);
    }


}
