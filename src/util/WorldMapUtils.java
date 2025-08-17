package util;

import world.Coordinate;
import world.WorldMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldMapUtils {

    public static List<Coordinate> getEmptyCoordinates(WorldMap worldMap){
        List<Coordinate> emptyCoordinates = new ArrayList<>();
        for (int i = 0; i < worldMap.getHeight(); i++) {
            for (int j = 0; j < worldMap.getWidth(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                if(worldMap.isPositionAvailable(coordinate)){
                    emptyCoordinates.add(coordinate);
                }
            }
        }
        Collections.shuffle(emptyCoordinates);
        return emptyCoordinates;
    }

    public static int getSizeMap(WorldMap worldMap){
        return worldMap.getHeight() * worldMap.getWidth();
    }
}


