package world;

import listener.EventType;
import listener.SimulationEvent;
import listener.SimulationListener;
import world.entity.*;

import java.util.*;

public class WorldMap {
    private static final int MIN_SIZE_MAP = 10;
    private static final String EXCEPTION_SIZE_MAP = "The width and height of the map cannot be less than " + MIN_SIZE_MAP;
    private static final String EXCEPTION_NULL = "Creature and Coordinate cannot be null";
    private static final String EXCEPTION_COORDINATE_OCCUPIED_TEMPLATE = "Coordinate %s is already occupied";
    private static final String EXCEPTION_COORDINATE_GOES_BEYOND_MAP_TEMPLATE = "Coordinate %s goes beyond the map";

    private final int width;
    private final int height;
    private final Map <Coordinate, Entity> entityPosition;
    private final List<SimulationListener> listeners = new ArrayList<>();


    public WorldMap(int width, int height) {
        if (width < MIN_SIZE_MAP || height < MIN_SIZE_MAP) {
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
        validate(coordinate);
        Entity entity = entityPosition.get(coordinate);
       if(entity == null){
           throw new IllegalArgumentException("The creature was not found at the coordinate - " + coordinate);
       }
       return entity;
    }

    public void addEntity(Coordinate coordinate, Entity entity){
        entityPosition.put(coordinate,entity);
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


    public boolean isFreePosition(Coordinate position){
        validate(position);
        return !entityPosition.containsKey(position);
    }

    public boolean isWithinBounds(Coordinate coordinate){
        return coordinate.width() >= 0 && coordinate.width() < width &&
                coordinate.height() >= 0 && coordinate.height() < height;
    }

  //TODO Подумать над перенесением метода в Action или Creature

    public void updatePosition(Creature creature, Coordinate newPosition) {
        if (creature == null || newPosition == null) {
            throw new IllegalArgumentException(EXCEPTION_NULL);
        }
        if(!isWithinBounds(newPosition)){
            return;
        }
        entityPosition.remove(creature.getPosition());
        if (entityPosition.get(newPosition) instanceof Predator) {
            entityPosition.put(creature.getPosition(),creature);
        }
        entityPosition.put(newPosition, creature);
        creature.setPosition(newPosition);
        notifyOfMove(creature, newPosition);
    }

    public List<Coordinate> getAllCoordinates(){
        Set<Coordinate> coordinates = entityPosition.keySet();
        return new ArrayList<>(coordinates);
    }

    private void notifyOfMove(Creature creature, Coordinate newPosition){
        notifyListeners(new SimulationEvent(EventType.ENTITY_MOVED, String.format( "➡️ %s moved to %s", creature.getClass().getSimpleName(), newPosition), creature));
    }

    private void validate(Coordinate coordinate){
        if(!isWithinBounds(coordinate)){
            throw new IllegalArgumentException("Incorrect coordinate! - " + coordinate);
        }
    }

}

