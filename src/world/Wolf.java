package world;

public class Wolf extends Predator{
    private static final String ICON = "🐺";

    public Wolf(Coordinate position, int speed, int health, int attackPower, BFSExplorer explorer) {
        super(position, speed, health, attackPower, explorer);
    }

    @Override
    public String getIcon() {
        return ICON;
    }
}
