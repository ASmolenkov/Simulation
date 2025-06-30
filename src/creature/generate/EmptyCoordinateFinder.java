package creature.generate;

import world.Coordinate;
import world.EmptyArea;
import world.Entity;
import world.MapWorld;

import java.util.*;

public class EmptyCoordinateFinder implements CoordinateFinder{
    private final Random random;
    private final MapWorld mapWorld;
    private final Set<Coordinate> occupiedCache = new HashSet<>();

    public EmptyCoordinateFinder(Random random, MapWorld mapWorld) {
        this.random = random;
        this.mapWorld = mapWorld;
    }

    @Override
    public Coordinate findRandomEmptyCoordinate() throws IllegalStateException {
        List<Coordinate> emptyPosition = new ArrayList<>();

        mapWorld.getEntityPositionMap().forEach((coordinate, entity) -> {
            if(mapWorld.getEntityPositionMap().get(coordinate) instanceof EmptyArea && !occupiedCache.contains(coordinate)){
                emptyPosition.add(coordinate);
            }
        });

        if(emptyPosition.isEmpty()){
            throw new IllegalStateException("No free coordinates for spawn");
        }
        return emptyPosition.get(random.nextInt(emptyPosition.size()));
    }

    @Override
    public boolean hasEmptyCoordinates() {
        return mapWorld.getEntityPositionMap().values().stream().anyMatch(entity -> entity instanceof EmptyArea);
    }

    @Override
    public void markPositionAsOccupied(Coordinate coordinate) {
        occupiedCache.add(coordinate);
    }
}
