package world;

public class Rock extends LandScape {
    private static final String ICON = "🗻" ;

    public Rock(Coordinate position) {
        super(position);
    }

    @Override
    public String getIcon() {
        return ICON;
    }
}
