package world;

public class Rabbit extends Herbivore{
    private static final String SPRITE = "🐇";

    public Rabbit(Coordinate position, int speed, int health, BFSExplorer explorer) {
        super(position, speed, health, explorer);
    }

    @Override
    public String getSprite() {
        return SPRITE;
    }

    @Override
    public String toString() {
        return "Rabbit{" +
                "position=" + position +
                '}';
    }

}
