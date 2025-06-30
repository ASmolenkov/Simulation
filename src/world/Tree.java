package world;

public class Tree extends LandScape {
    private static final String ICON = "🌲";

    public Tree(Coordinate position) {
        super(position);
    }

    @Override
    public String getIcon() {
        return ICON;
    }
}
