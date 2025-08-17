package creature.generate;

import util.WorldMapUtils;
import world.Coordinate;
import world.WorldMap;

import java.util.*;

public class EmptyCoordinateFinder implements CoordinateFinder{
    private static final String NO_FREE_COORDINATES = "No free coordinates for spawn";

    private final Random random;
    private final WorldMap worldMap;
    private final Set<Coordinate> occupiedCache = new HashSet<>();

    public EmptyCoordinateFinder(Random random, WorldMap worldMap) {
        this.random = random;
        this.worldMap = worldMap;
    }

    @Override
    public Coordinate findRandomEmptyCoordinate() throws IllegalStateException {
        List<Coordinate> emptyPosition = WorldMapUtils.getEmptyCoordinates(worldMap);
        if(emptyPosition.isEmpty()){
            throw new IllegalStateException(NO_FREE_COORDINATES);
        }
        return emptyPosition.get(random.nextInt(emptyPosition.size()));
    }

    @Override
    public boolean hasEmptyCoordinates() {
        List<Coordinate> emptyCoordinates = WorldMapUtils.getEmptyCoordinates(worldMap);
        return emptyCoordinates.isEmpty();
    }

    @Override
    public void markPositionAsOccupied(Coordinate coordinate) {
        occupiedCache.add(coordinate);
    }
}
