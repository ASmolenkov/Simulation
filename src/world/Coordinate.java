package world;

public record Coordinate(int width, int height) {

    @Override
    public String toString() {
        return String.format("(%s, %s)", width, height);
    }
}
