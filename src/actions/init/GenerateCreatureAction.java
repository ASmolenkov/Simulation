package actions.init;

import actions.Action;
import creature.generate.CoordinateFinder;
import creature.generate.CreatureCountCalculator;
import creature.generate.CreatureSpawner;

import world.MapWorld;

public class GenerateCreatureAction implements Action {
    private CreatureSpawner creatureSpawner;
    private MapWorld mapWorld;
    private CreatureCountCalculator countCalculator;
    private CoordinateFinder emptyCoordinateFinder;

    public GenerateCreatureAction(CreatureSpawner creatureSpawner, MapWorld mapWorld, CreatureCountCalculator countCalculator, CoordinateFinder emptyCoordinateFinder) {
        this.creatureSpawner = creatureSpawner;
        this.mapWorld = mapWorld;
        this.countCalculator = countCalculator;
        this.emptyCoordinateFinder = emptyCoordinateFinder;
    }

    @Override
    public void perform(MapWorld mapWorld) {
        creatureSpawner.spawnCreatures();
    }
}
