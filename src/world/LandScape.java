package world;

public abstract class LandScape extends Entity{
    protected LandScape(Coordinate position) {
        super(position);
    }

    public abstract String getSprite();
}
