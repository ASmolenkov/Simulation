package world;

public class Tree extends LandScape {
    private static final String SPRITE = "🌳";

    public Tree(Coordinate position) {
        super(position);
    }

    @Override
    public String getSprite() {
        return SPRITE;
    }
}
