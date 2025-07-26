package render;

import world.*;
import world.entity.*;

import java.util.Map;

public class ConsoleRenderer implements Renderer {

    private static final String WOLF_SPRITE = "🐺";
    private static final String RABBIT_SPRITE = "🐇";
    private static final String GRASS_SPRITE = "🌱";
    private static final String ROCK_SPRITE = "🗻";
    private static final String TREE_SPRITE = "🌳";
    private static final String EMPTY_AREA_SPRITE = "🟫";

    private static final Map<Class<? extends Entity>, String> SPRITES = Map.of(Wolf.class, WOLF_SPRITE, Rabbit.class,RABBIT_SPRITE,
            Grass.class,GRASS_SPRITE, Rock.class, ROCK_SPRITE, Tree.class, TREE_SPRITE, EmptyArea.class, EMPTY_AREA_SPRITE);

    @Override
    public void render(MapWorld mapWorld)  {
        for (int y = 0; y < mapWorld.getHeight(); y++) {
            for (int x = 0; x < mapWorld.getWidth(); x++) {
                Coordinate coordinate = new Coordinate(x,y);
                Entity entity = mapWorld.getEntityPositionMap().get(coordinate);
                printSprite(entity);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printSprite(Entity entity){
        String sprite = SPRITES.getOrDefault(entity.getClass(),EMPTY_AREA_SPRITE);
        System.out.print(sprite);
    }


}
