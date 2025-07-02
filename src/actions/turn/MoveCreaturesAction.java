package actions.turn;

import actions.Action;
import world.Creature;
import world.MapWorld;

import java.util.HashMap;

public class MoveCreaturesAction  implements Action {

    @Override
    public void perform(MapWorld mapWorld) {
      new HashMap<>(mapWorld.getEntityPositionMap()).forEach((coordinate, entity) -> {
            if (entity instanceof Creature creature){
                ((Creature) entity).findAndMoveToTarget(mapWorld);
            }
        });
    }
}
