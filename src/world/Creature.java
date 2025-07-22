package world;

import java.util.List;

public abstract class Creature extends Entity {
    private final int speed;
    private int health;

    public Creature(Coordinate position, int speed, int health) {
        super(position);
        this.speed = speed;
        this.health = health;
    }

    public abstract void findAndMoveToTarget(MapWorld mapWorld);

    public abstract void makeMove(MapWorld mapWorld, List<Coordinate> pathInTarget, Coordinate target);

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public void minusHealth(int health) {
        this.health -= health;
    }

    public void plusHealth() {
        if(this.health <= 3){
            this.health += 2;
        }
        if(this.health == 4){
            this.health++;
        }

    }

    protected abstract Class<? extends Entity> getTargetType();

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
