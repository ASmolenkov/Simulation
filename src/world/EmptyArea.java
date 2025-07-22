package world;

public class EmptyArea extends LandScape{

    public EmptyArea(Coordinate position) {
        super(position);
    }

    @Override
    public String toString() {
        return "EmptyArea{" +
                "position=" + position +
                '}';
    }
}
