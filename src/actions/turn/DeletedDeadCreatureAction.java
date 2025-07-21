package actions.turn;

import actions.Action;
import world.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeletedDeadCreatureAction implements Action {

    private final MapWorld mapWorld;

    public DeletedDeadCreatureAction(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
    }

    @Override
    public void perform(MapWorld mapWorld) {
        List<Coordinate> deadCreature = new ArrayList<>();
        Map<Coordinate, Entity> world = mapWorld.getEntityPositionMap();
        world.forEach((coordinate, entity) -> {
            if(entity instanceof Creature){
                Creature creature =(Creature) entity;
                if(creature.getHealth() <= 0){
                    deadCreature.add(coordinate);
                }
            }
        });
        deadCreature.forEach(coordinate -> world.put(coordinate, new EmptyArea(coordinate)));
    }
}
