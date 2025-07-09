package world;

public class Wolf extends Predator{
    private static final String SPRITE = "🐺";

    public Wolf(Coordinate position, int speed, int health, int attackPower, BFSExplorer explorer) {
        super(position, speed, health, attackPower, explorer);
    }

    @Override
    public String getSprite() {
        return SPRITE;
    }
}
