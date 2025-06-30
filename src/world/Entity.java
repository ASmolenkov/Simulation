package world;

public abstract class Entity {
    protected Coordinate position;
    protected Entity(Coordinate position) {
        this.position = position;
    }

    public final Coordinate getPosition() {
        return position;
    }

    protected void setPosition(Coordinate newPosition){
        this.position = newPosition;
    }

    public abstract String getIcon();
}
