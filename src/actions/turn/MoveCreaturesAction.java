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
      worldMap.getAllEntity().forEach(( entity) -> {
            if (entity instanceof Creature creature){
                creature.performMovementAction(worldMap);
            }
        });
    }
}
