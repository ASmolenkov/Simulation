package world;

import listener.EventType;
import listener.SimulationEvent;
import listener.SimulationListener;
import world.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapWorld {
    private final int width;
    private final int height;
    private final Map <Coordinate, Entity> entityPositionMap;
    private final List<SimulationListener> listeners = new ArrayList<>();


    public MapWorld(int width, int height) {
        if (width < 10 || height < 10) {
            throw new IllegalArgumentException("The width and height of the map cannot be less than 10");
        }
        this.width = width;
        this.height = height;
        entityPositionMap = new HashMap<>();
    }

    public void addListener(SimulationListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(SimulationEvent event){
        listeners.forEach(l -> l.onEvent(event));
    }


    public Map<Coordinate, Entity> getEntityPositionMap() {
        return entityPositionMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize(){
        return width * height;
    }

    public void addEntity(Entity entity){
        entityPositionMap.put(entity.getPosition(),entity);
    }


    public boolean isPositionAvailable(Coordinate position){
        return !entityPositionMap.containsKey(position);
    }

    public boolean isWithinBounds(Coordinate coordinate){
        return coordinate.getWidth() >= 0 && coordinate.getWidth() <= width &&
                coordinate.getHeight() >= 0 && coordinate.getHeight() <= height;
    }

    public void updatePosition(Creature creature, Coordinate newPosition) {
        if (creature == null || newPosition == null) {
            throw new IllegalArgumentException("Creature and Coordinate cannot be null");
        }
        if(!isWithinBounds(newPosition)){
            return;
        }
        entityPositionMap.put(creature.getPosition(), new EmptyArea(creature.getPosition()));
        if (entityPositionMap.get(newPosition) instanceof Predator) {
            throw new IllegalStateException("Coordinate " + newPosition + " is already occupied");
        }
        entityPositionMap.put(newPosition, creature);
        creature.setPosition(newPosition);
        notifyOfMove(creature, newPosition);
    }

    public int getCountRabbet(){
        int countRabbit = 0;
        for (Entity entity: entityPositionMap.values()){
            if(entity instanceof Rabbit){
                countRabbit++;
            }
        }
        return countRabbit;
    }

    private void notifyOfMove(Creature creature, Coordinate newPosition){
        notifyListeners(new SimulationEvent(EventType.ENTITY_MOVED, String.format( "➡️ %s moved to %s", creature.getClass().getSimpleName(), newPosition), creature));
    }

}

