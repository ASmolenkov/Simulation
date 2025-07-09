package world;

public class EmptyArea extends LandScape{
    private static final String SPRITE = "🟫";

    public EmptyArea(Coordinate position) {
        super(position);
    }

    @Override
    public String getSprite() {
        return SPRITE;
    }

    @Override
    public String toString() {
        return "EmptyArea{" +
                "position=" + position +
                '}';
    }
}
