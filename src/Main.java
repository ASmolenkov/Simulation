import actions.init.GenerateLandscapeAction;
import actions.turn.AddingGrassAction;
import actions.turn.AddingHerbivoreAction;
import actions.turn.MoveCreaturesAction;
import creature.generate.CreatureCountCalculator;
import creature.generate.CreatureSpawner;
import creature.generate.EmptyCoordinateFinder;
import factory.creature.RabbitFactory;
import factory.creature.WolfFactory;
import render.ConsoleRenderer;
import world.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MapWorld mapWorld = new MapWorld(13,13);
        BFSExplorer explorer = new BFSExplorer(mapWorld);
        Random random = new Random();
        ConsoleRenderer consoleRenderer = new ConsoleRenderer();
        System.out.println("Размер мапы: " + mapWorld.getEntityPositionMap().size());

        GenerateLandscapeAction generateLandscape = new GenerateLandscapeAction();
        generateLandscape.perform(mapWorld);

        WolfFactory wolfFactory = WolfFactory.withDefaultConfig();
        RabbitFactory rabbitFactory = RabbitFactory.withDefaultConfig();

        consoleRenderer.render(mapWorld);

        MoveCreaturesAction moveCreaturesAction = new MoveCreaturesAction();
        CreatureCountCalculator countCalculator = new CreatureCountCalculator(random, 0.1);
        countCalculator.addCreatureTypeAndProbabilities(CreatureType.PREDATOR, 0.3);
        countCalculator.addCreatureTypeAndProbabilities(CreatureType.HERBIVORE, 0.7);

        CreatureSpawner<Creature> creatureSpawner = new CreatureSpawner<Creature>(mapWorld, random,new EmptyCoordinateFinder(random,mapWorld),countCalculator,explorer);
        creatureSpawner.addFactory(CreatureType.HERBIVORE,rabbitFactory);
        creatureSpawner.addFactory(CreatureType.PREDATOR,wolfFactory);
        creatureSpawner.perform(mapWorld);




        AddingGrassAction addingGrassAction = new AddingGrassAction(mapWorld,0.125, 10);
        AddingHerbivoreAction addingHerbivoreAction = new AddingHerbivoreAction(1, mapWorld, 0.04);


       while (hasHerbivores(mapWorld)){

            moveCreaturesAction.perform(mapWorld);
            mapWorld.removeDeadCreatures();
            consoleRenderer.render(mapWorld);
            addingGrassAction.perform(mapWorld);
            addingHerbivoreAction.perform(mapWorld);

            Thread.sleep(2000);
        }

    }

    private static boolean hasHerbivores(MapWorld mapWorld){
         for(Entity entity: mapWorld.getEntityPositionMap().values()){
             if(entity instanceof  Herbivore){
                 return true;
             }
         }
         return false;
    }

    private static int countGrass(MapWorld mapWorld){
        int count = 0;
        for(Entity entity: mapWorld.getEntityPositionMap().values()){
            if(entity instanceof  Grass){
                count++;
            }
        }
        return count;
    }

    private static int countHerbivores(MapWorld mapWorld){
        int count = 0;
        for(Entity entity: mapWorld.getEntityPositionMap().values()){
            if(entity instanceof  Herbivore){
                count++;
            }
        }
        return count;
    }
}