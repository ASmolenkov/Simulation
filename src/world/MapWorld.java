package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapWorld {
    private final int width;
    private final int height;
    private final Map <Coordinate, Entity> entityPositionMap;



    public MapWorld(int width, int height) {
        this.width = width;
        this.height = height;
        entityPositionMap = new HashMap<>();
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

        entityPositionMap.put(creature.getPosition(), new EmptyArea(creature.getPosition()));


        if (entityPositionMap.get(newPosition) instanceof Predator) {
            throw new IllegalStateException("Coordinate " + newPosition + " is already occupied");
        }

        entityPositionMap.put(newPosition, creature);

        creature.setPosition(newPosition);
    }

    public boolean isNotHerbivore(Coordinate coordinate){
        return !(entityPositionMap.get(coordinate) instanceof Grass);
    }

    public void removeDeadCreatures(){
        List<Coordinate> deadCreature = new ArrayList<>();
          (entityPositionMap).forEach((coordinate, entity) -> {
            if(entity instanceof Creature){
                Creature creature =(Creature) entity;
                if(creature.getHealth() <= 0){
                    deadCreature.add(coordinate);
                }
            }
        });
          deadCreature.forEach(coordinate -> entityPositionMap.put(coordinate, new EmptyArea(coordinate)));
    }
}

