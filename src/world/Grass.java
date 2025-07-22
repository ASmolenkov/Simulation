package world;

public class Grass extends LandScape {

    public Grass(Coordinate position) {
        super(position);
    }

    @Override
    public String toString() {
        return "Grass{" +
                "position=" + position +
                '}';
    }
}
