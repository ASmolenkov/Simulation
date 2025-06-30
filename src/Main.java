import actions.GenerateLandscapeAction;
import actions.MoveCreaturesAction;
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
        MapWorld mapWorld = new MapWorld(10,10);
        BFSExplorer explorer = new BFSExplorer(mapWorld);
        Random random = new Random();
        ConsoleRenderer consoleRenderer = new ConsoleRenderer();
        System.out.println("Размер мапы: " + mapWorld.getEntityPositionMap().size());

        GenerateLandscapeAction generateLandscape = new GenerateLandscapeAction();
        generateLandscape.perform(mapWorld);

        WolfFactory wolfFactory = WolfFactory.withDefaultConfig();
        Coordinate wolfPosition = new Coordinate(5,5);
        Wolf wolf = wolfFactory.createDefault(wolfPosition,explorer);
        mapWorld.addEntity(wolf);
        System.out.println("Размер мапы: " + mapWorld.getEntityPositionMap().size());

        RabbitFactory rabbitFactory = RabbitFactory.withDefaultConfig();
        Coordinate rabbitPosition = new Coordinate(1,1);
        Rabbit rabbit = rabbitFactory.createDefault(rabbitPosition,explorer);
        mapWorld.addEntity(rabbit);
        System.out.println("Размер мапы: " + mapWorld.getEntityPositionMap().size());

        consoleRenderer.render(mapWorld);
        System.out.println("Координаты кролика: " + rabbit.getPosition());
        System.out.println("Координаты волка: " + wolf.getPosition());

        MoveCreaturesAction moveCreaturesAction = new MoveCreaturesAction();
        CreatureCountCalculator countCalculator = new CreatureCountCalculator(random, 0.1);
        countCalculator.addCreatureTypeAndProbabilities(CreatureType.PREDATOR, 0.3);
        countCalculator.addCreatureTypeAndProbabilities(CreatureType.HERBIVORE, 0.7);

        CreatureSpawner<Creature> creatureSpawner = new CreatureSpawner<Creature>(mapWorld, random,new EmptyCoordinateFinder(random,mapWorld),countCalculator,explorer);
        creatureSpawner.addFactory(CreatureType.HERBIVORE,rabbitFactory);
        creatureSpawner.addFactory(CreatureType.PREDATOR,wolfFactory);

        creatureSpawner.spawnCreatures();


       while (hasHerbivores(mapWorld)){

            moveCreaturesAction.perform(mapWorld);
            mapWorld.removeDeadCreatures();
            consoleRenderer.render(mapWorld);
            System.out.println("Жизни кролика: " + rabbit.getHealth());
            System.out.println("Координаты кролика: " + rabbit.getPosition());
            System.out.println("Координаты волка: " + wolf.getPosition());

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