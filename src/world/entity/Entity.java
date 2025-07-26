package world.entity;

import world.Coordinate;

public abstract class Entity {
    protected Coordinate position;
    protected Entity(Coordinate position) {
        this.position = position;
    }

    public final Coordinate getPosition() {
        return position;
    }



}
