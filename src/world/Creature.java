package world;

public abstract class Creature extends Entity {
    private final int speed;
    private int health;

    public Creature(Coordinate position, int speed, int health) {
        super(position);
        this.speed = speed;
        this.health = health;
    }

    public abstract void findAndMoveToTarget(MapWorld mapWorld);

    public abstract Coordinate findTarget(MapWorld mapWorld, Coordinate start);

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

}
