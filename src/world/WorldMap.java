package world;

import listener.EventType;
import listener.SimulationEvent;
import listener.SimulationListener;
import world.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMap {
    private static final String EXCEPTION_SIZE_MAP = "The width and height of the map cannot be less than 10";
    private static final String EXCEPTION_NULL = "Creature and Coordinate cannot be null";
    private static final String EXCEPTION_COORDINATE_OCCUPIED_TEMPLATE = "Coordinate %s is already occupied";
    private static final String EXCEPTION_COORDINATE_GOES_BEYOND_MAP_TEMPLATE = "Coordinate %s goes beyond the map";

    private final int width;
    private final int height;
    private final Map <Coordinate, Entity> entityPosition;
    private final List<SimulationListener> listeners = new ArrayList<>();


    public WorldMap(int width, int height) {
        if (width < 10 || height < 10) {
            throw new IllegalArgumentException(EXCEPTION_SIZE_MAP);
        }
        this.width = width;
        this.height = height;
        entityPosition = new HashMap<>();
    }

    public void addListener(SimulationListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(SimulationEvent event){
        listeners.forEach(l -> l.onEvent(event));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Entity getEntity(Coordinate coordinate){
       return entityPosition.get(coordinate);
    }

    public void addEntity(Entity entity){
        entityPosition.put(entity.getPosition(),entity);
    }
    public void subEntity(Coordinate coordinate){
        if(!isWithinBounds(coordinate)){
            throw new IllegalArgumentException(String.format(EXCEPTION_COORDINATE_GOES_BEYOND_MAP_TEMPLATE, coordinate));
        }
        entityPosition.remove(coordinate);
    }

    public List<Entity> getAllEntity(){
        return new ArrayList<>(entityPosition.values());
    }


    public boolean isPositionAvailable(Coordinate position){
        if(!isWithinBounds(position)){
            throw new IllegalArgumentException(String.format(EXCEPTION_COORDINATE_GOES_BEYOND_MAP_TEMPLATE, position));
        }
        return !entityPosition.containsKey(position);
    }

    public boolean isWithinBounds(Coordinate coordinate){
        return coordinate.width() >= 0 && coordinate.width() <= width - 1 &&
                coordinate.height() >= 0 && coordinate.height() <= height - 1;
    }

    public void updatePosition(Creature creature, Coordinate newPosition) {
        if (creature == null || newPosition == null) {
            throw new IllegalArgumentException(EXCEPTION_NULL);
        }
        if(!isWithinBounds(newPosition)){
            return;
        }
        entityPosition.remove(creature.getPosition());
        if (entityPosition.get(newPosition) instanceof Predator) {
            throw new IllegalStateException(String.format(EXCEPTION_COORDINATE_OCCUPIED_TEMPLATE, newPosition) );
        }
        entityPosition.put(newPosition, creature);
        creature.setPosition(newPosition);
        notifyOfMove(creature, newPosition);
    }

    public List<Coordinate> getAllCoordinates(){
        List<Coordinate> coordinates = new ArrayList<>();
        for (Entity entity: entityPosition.values()){
            coordinates.add(entity.getPosition());
        }
        return coordinates;
    }

    private void notifyOfMove(Creature creature, Coordinate newPosition){
        notifyListeners(new SimulationEvent(EventType.ENTITY_MOVED, String.format( "➡️ %s moved to %s", creature.getClass().getSimpleName(), newPosition), creature));
    }

}

