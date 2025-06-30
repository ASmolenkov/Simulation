package creature.generate;

import world.Coordinate;

public interface CoordinateFinder {
    Coordinate findRandomEmptyCoordinate() throws IllegalStateException;
    boolean hasEmptyCoordinates();
    void markPositionAsOccupied(Coordinate coordinate);
}
