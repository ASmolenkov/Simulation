package actions.turn;

import actions.Action;
import world.entity.Creature;
import world.MapWorld;

import java.util.HashMap;

public class MoveCreaturesAction  implements Action {
    private final MapWorld mapWorld;

    public MoveCreaturesAction(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
    }

    @Override
    public void perform() {
      new HashMap<>(mapWorld.getEntityPositionMap()).forEach((coordinate, entity) -> {
            if (entity instanceof Creature creature){
                creature.performMovementAction(mapWorld);
            }
        });
    }
}
