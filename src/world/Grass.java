package world;

public class Grass extends LandScape {
    private static final String SPRITE = "🌿";

    public Grass(Coordinate position) {
        super(position);
    }

    @Override
    public String getSprite() {
        return SPRITE;
    }

    @Override
    public String toString() {
        return "Grass{" +
                "position=" + position +
                '}';
    }
}
