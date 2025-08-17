package actions.turn;

import actions.Action;
import listener.EventType;
import listener.SimulationEvent;
import world.WorldMap;
import world.entity.Creature;

public class HungerAction implements Action {
    private static final int HUNGRY = Creature.MAX_SATIETY / 2;
    private static final int CHANGE_OF_SPEED = 1;
    private static final String CREATURE_HUNGRY_TEMPLATE = "😧 %s hungry and walks faster!";
    private static final String CREATURE_EAT_FILL_TEMPLATE = "😌 %s ate his fill and calmed down";
    private final WorldMap worldMap;

    public HungerAction(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void perform() {
        worldMap.getAllEntity().forEach(( entity) -> {
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
        worldMap.notifyListeners(new SimulationEvent(EventType.HUNGER,String.format(CREATURE_HUNGRY_TEMPLATE, creature.getClass().getSimpleName()),creature));
    }

    private void notifyEat(Creature creature){
        worldMap.notifyListeners(new SimulationEvent(EventType.FULL_EAT,String.format(CREATURE_EAT_FILL_TEMPLATE, creature.getClass().getSimpleName()),creature));
    }
}
