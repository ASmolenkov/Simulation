package actions.turn;

import actions.Action;
import world.MapWorld;
import world.entity.Creature;

public class HungerAction implements Action {
    private final MapWorld mapWorld;

    public HungerAction(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
    }

    @Override
    public void perform() {
        mapWorld.getEntityPositionMap().forEach((coordinate, entity) -> {
            if(entity instanceof Creature creature){
                creature.starve();
                if(creature.getSatiety() == 0){
                    creature.minusHealth(1);
                }
            }
        });
    }
}
