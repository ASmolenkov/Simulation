package world.entity;

import world.Coordinate;

public abstract class Entity {
    public final Coordinate getPosition() {
        return position;
    }
    protected Coordinate position;
    protected Entity(Coordinate position) {
        this.position = position;
    }

}
