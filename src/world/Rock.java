package world;

public class Rock extends LandScape {
    private static final String SPRITE = "🗻";

    public Rock(Coordinate position) {
        super(position);
    }

    @Override
    public String getSprite() {
        return SPRITE;
    }
}
