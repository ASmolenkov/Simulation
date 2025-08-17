package actions.turn;

import actions.Action;
import world.entity.Creature;
import world.WorldMap;

import java.util.HashMap;

public class MoveCreaturesAction  implements Action {
    private final WorldMap worldMap;

    public MoveCreaturesAction(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void perform() {
      new HashMap<>(worldMap.getEntityPosition()).forEach((coordinate, entity) -> {
            if (entity instanceof Creature creature){
                creature.performMovementAction(worldMap);
            }
        });
    }
}
