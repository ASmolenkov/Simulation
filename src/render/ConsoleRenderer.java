package render;

import world.Coordinate;
import world.Entity;
import world.MapWorld;

import java.util.Map;

public class ConsoleRenderer implements Renderer {

    @Override
    public void render(MapWorld mapWorld)  {
        for (int y = 0; y < mapWorld.getHeight(); y++) {
            for (int x = 0; x < mapWorld.getWidth(); x++) {
                Coordinate coordinate = new Coordinate(x,y);
                if(mapWorld.getEntityPositionMap().containsKey(coordinate)){
                    //System.out.print("[" + x + "," + y + "]");
                    System.out.print((mapWorld.getEntityPositionMap().get(coordinate).getIcon()));
                }
            }
            System.out.println();
        }
    }
}
