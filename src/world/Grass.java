package world;

public class Grass extends LandScape {
    private static final String ICON = "🌿";

    public Grass(Coordinate position) {
        super(position);
    }

    @Override
    public String getIcon() {
        return ICON;
    }

    @Override
    public String toString() {
        return "Grass{" +
                "position=" + position +
                '}';
    }
}
