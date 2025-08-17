package actions.turn;

import actions.Action;
import listener.EventType;
import listener.SimulationEvent;
import world.*;
import world.entity.Creature;
import world.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeletedDeadCreatureAction implements Action {

    private final WorldMap worldMap;

    public DeletedDeadCreatureAction(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void perform() {
        List<Coordinate> deadCreature = new ArrayList<>();
        Map<Coordinate, Entity> world = worldMap.getEntityPosition();
        world.forEach((coordinate, entity) -> {
            if(entity instanceof Creature creature){
                if(creature.getHealth() <= 0){
                    deadCreature.add(coordinate);
                    notifyDead(creature);
                }
            }
        });
        deadCreature.forEach(worldMap::subEntity);
    }

    private void notifyDead(Creature creature){
        worldMap.notifyListeners(new SimulationEvent(EventType.ENTITY_DIED, String.format("☠️ %s is died " , creature.getClass().getSimpleName()),creature));
    }
}
