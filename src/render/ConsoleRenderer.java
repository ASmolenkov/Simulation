package render;

import world.*;
import world.entity.*;

import java.util.Map;

public class ConsoleRenderer implements Renderer {

    private static final String WOLF_SPRITE = "🐺";
    private static final String RABBIT_SPRITE = "🐇";
    private static final String GRASS_SPRITE = "🌿";
    private static final String ROCK_SPRITE = "🗻";
    private static final String TREE_SPRITE = "🌳";
    private static final String EMPTY_AREA_SPRITE = "🟫";

    private static final Map<Class<? extends Entity>, String> SPRITES = Map.of(Wolf.class, WOLF_SPRITE, Rabbit.class,RABBIT_SPRITE,
            Grass.class,GRASS_SPRITE, Rock.class, ROCK_SPRITE, Tree.class, TREE_SPRITE);

    @Override
    public void render(WorldMap worldMap)  {
        for (int y = 0; y < worldMap.getHeight(); y++) {
            for (int x = 0; x < worldMap.getWidth(); x++) {
                Coordinate coordinate = new Coordinate(x,y);
                Entity entity = worldMap.getEntity(coordinate);
                printSprite(entity);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printSprite(Entity entity){
        switch (entity) {
            case null -> System.out.print(EMPTY_AREA_SPRITE);
            case Wolf wolf -> System.out.print(WOLF_SPRITE);
            case Rabbit rabbit -> System.out.print(RABBIT_SPRITE);
            case Rock rock -> System.out.print(ROCK_SPRITE);
            case Tree tree -> System.out.print(TREE_SPRITE);
            case Grass grass -> System.out.print(GRASS_SPRITE);
            default -> {
            }
        }
    }

}
