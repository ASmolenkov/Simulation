package actions.init;

import actions.Action;
import creature.generate.CreatureSpawner;
import world.entity.Creature;

public class GenerateCreatureAction implements Action {
    private final CreatureSpawner<Creature> creatureSpawner;


    public GenerateCreatureAction(CreatureSpawner<Creature> creatureSpawner) {
        this.creatureSpawner = creatureSpawner;

    }

    @Override
    public void perform() {
        creatureSpawner.spawnCreatures();
    }
}
