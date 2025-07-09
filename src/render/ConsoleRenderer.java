package render;

import world.Coordinate;
import world.MapWorld;

public class ConsoleRenderer implements Renderer {

    @Override
    public void render(MapWorld mapWorld)  {
        for (int y = 0; y < mapWorld.getHeight(); y++) {
            for (int x = 0; x < mapWorld.getWidth(); x++) {
                Coordinate coordinate = new Coordinate(x,y);
                if(mapWorld.getEntityPositionMap().containsKey(coordinate)){
                    String sprite = mapWorld.getEntityPositionMap().get(coordinate).getSprite();
                    System.out.print(formatCell(sprite));
                }
            }
            System.out.println();
        }
    }

    private static String formatCell(String sprite) {
        // Форсируем ширину в 2 символа
        return sprite.length() >= 2 ? sprite : sprite + " ";
    }
}
