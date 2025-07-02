package creature.generate;

import actions.Action;
import factory.creature.CreatureFactory;
import factory.creature.RabbitFactory;
import factory.creature.WolfFactory;
import world.*;

import java.util.*;

public class CreatureSpawner<T extends Creature> implements Action {
    private final MapWorld mapWorld;
    private final Random random;
    private final CoordinateFinder emptyCoordinatesFinder;
    private final CreatureCountCalculator countCalculator;
    private final Map<CreatureType, CreatureFactory<? extends T>> factories;
    private final BFSExplorer bfsExplorer;


    public CreatureSpawner(MapWorld mapWorld, Random random, CoordinateFinder emptyCoordinatesFinder, CreatureCountCalculator countCalculator, BFSExplorer explorer) {
        this.mapWorld = mapWorld ;
        this.emptyCoordinatesFinder = emptyCoordinatesFinder;
        this.countCalculator = countCalculator;
        this.factories = new HashMap<>();
        this.bfsExplorer = explorer;
        this.random = random;
    }

    @Override
    public void perform(MapWorld mapWorld) {
        spawnCreatures();
    }

    public void addFactory(CreatureType creatureType, CreatureFactory<? extends T> factory){
        factories.put(creatureType,factory);
    }

    private void spawnCreatures(){
        int mapSize = mapWorld.getSize();
        Map<CreatureType, Integer> countCreature = countCalculator.calculateCounts(mapSize);

        if(!emptyCoordinatesFinder.hasEmptyCoordinates()){
            throw new IllegalStateException("There are no free places for creatures to spawn.");
        }
        countCreature.forEach((type, count) -> {
            CreatureFactory<? extends T> factory = factories.get(type);
            if(factories == null){
                throw new IllegalStateException("No factory registered for type: " + type);
            }
            for (int i = 0; i < count; i++) {
                Coordinate position = emptyCoordinatesFinder.findRandomEmptyCoordinate();
                T creature = factory.createDefault(position, bfsExplorer);
                mapWorld.addEntity(creature);
                emptyCoordinatesFinder.markPositionAsOccupied(position);
            }
        } );
    }


}
