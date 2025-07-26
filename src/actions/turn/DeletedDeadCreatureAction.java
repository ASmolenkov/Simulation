package actions.turn;

import actions.Action;
import listener.EventType;
import listener.SimulationEvent;
import world.*;
import world.entity.Creature;
import world.entity.EmptyArea;
import world.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeletedDeadCreatureAction implements Action {

    private final MapWorld mapWorld;

    public DeletedDeadCreatureAction(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
    }

    @Override
    public void perform() {
        List<Coordinate> deadCreature = new ArrayList<>();
        Map<Coordinate, Entity> world = mapWorld.getEntityPositionMap();
        world.forEach((coordinate, entity) -> {
            if(entity instanceof Creature creature){
                if(creature.getHealth() <= 0){
                    deadCreature.add(coordinate);
                    mapWorld.notifyListeners(new SimulationEvent(EventType.ENTITY_DIED, String.format("%s is died ☠️" , creature.getClass().getSimpleName()),creature));
                }
            }
        });
        deadCreature.forEach(coordinate -> mapWorld.getEntityPositionMap().put(coordinate, new EmptyArea(coordinate)));
    }
}
