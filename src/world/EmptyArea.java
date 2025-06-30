package world;

public class EmptyArea extends LandScape{
    private static final String ICON = "🟫";

    public EmptyArea(Coordinate position) {
        super(position);
    }

    @Override
    public String getIcon() {
        return ICON;
    }

    @Override
    public String toString() {
        return "EmptyArea{" +
                "position=" + position +
                '}';
    }
}
