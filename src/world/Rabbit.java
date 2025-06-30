package world;

import java.util.*;

public class Rabbit extends Herbivore{
    private static final String ICON = "🐇";

    public Rabbit(Coordinate position, int speed, int health, BFSExplorer explorer) {
        super(position, speed, health, explorer);
    }

    @Override
    public String getIcon() {
        return ICON;
    }

    @Override
    public String toString() {
        return "Rabbit{" +
                "position=" + position +
                '}';
    }

}
