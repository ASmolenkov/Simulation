package actions.turn;

import actions.Action;
import listener.EventType;
import listener.SimulationEvent;
import world.MapWorld;
import world.entity.Creature;

public class HungerAction implements Action {
    private static final int HUNGRY = Creature.MAX_SATIETY / 2;
    private static final int CHANGE_OF_SPEED = 1;
    private final MapWorld mapWorld;

    public HungerAction(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
    }

    @Override
    public void perform() {
        mapWorld.getEntityPositionMap().forEach((coordinate, entity) -> {
            if(entity instanceof Creature creature){
                applyHungerEffects(creature);
            }
        });
    }
    private void applyHungerEffects(Creature creature){
        int originalSpeed = creature.getSpeed();
        creature.starve();
        if(creature.getSatiety() == 0){
            creature.minusHealth(1);
        }
        else if(creature.getSatiety() < (HUNGRY)){
            if(!creature.isSpeedBoosted()){
                creature.setTemporarySpeed(originalSpeed + CHANGE_OF_SPEED);
                creature.setSpeedBoosted(true);
                notifyHungry(creature);
            }
        }
        else if(creature.isSpeedBoosted()){
            creature.setTemporarySpeed(originalSpeed - CHANGE_OF_SPEED);
            notifyEat(creature);
            creature.setSpeedBoosted(false);
        }
    }

    private void notifyHungry(Creature creature){
        mapWorld.notifyListeners(new SimulationEvent(EventType.HUNGER,String.format("😧" + creature.getClass().getSimpleName() + " hungry and walks faster!"),creature));
    }

    private void notifyEat(Creature creature){
        mapWorld.notifyListeners(new SimulationEvent(EventType.FULL,String.format("😌" + creature.getClass().getSimpleName() + " ate his fill and calmed down"),creature));
    }
}
