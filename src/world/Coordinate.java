package world;

import java.util.Objects;

public class Coordinate {
    private final int width;
    private final int height;

    public Coordinate(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return width == that.width && height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", width, height);
    }
}
