package render;

import world.*;

public class ConsoleRenderer implements Renderer {

    private static final String WOLF_SPRITE = "🐺";
    private static final String RABBIT_SPRITE = "🐇";
    private static final String GRASS_SPRITE = "🌱";
    private static final String ROCK_SPRITE = "🗻";
    private static final String TREE_SPRITE = "🌳";
    private static final String EMPTY_AREA_SPRITE = "🟫";

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
        if(entity instanceof Wolf){
            System.out.print(WOLF_SPRITE);
        }

        if(entity instanceof Rabbit){
            System.out.print(RABBIT_SPRITE);
        }

        if(entity instanceof Grass){
            System.out.print(GRASS_SPRITE);
        }

        if(entity instanceof Rock){
            System.out.print(ROCK_SPRITE);
        }

        if(entity instanceof Tree){
            System.out.print(TREE_SPRITE);
        }

        if(entity instanceof EmptyArea){
            System.out.print(EMPTY_AREA_SPRITE);
        }
    }


}
