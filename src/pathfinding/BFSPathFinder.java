package pathfinding;

import world.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class BFSPathFinder extends BFSExplorer implements Pathfinder {


    public BFSPathFinder(WorldMap worldMap) {
        super(worldMap);
    }

    public List<Coordinate> findPathToTarget(Coordinate start, Coordinate target){
        Map<Coordinate,Coordinate> cameFrom = new HashMap<>();
        final AtomicBoolean found = new AtomicBoolean(false);

            bfsSearch(start,(current, cf) -> {
                cameFrom.putAll(cf);
                if(current.equals(target)){
                    found.set(true);
                    throw new BFSResultFoundException();
                }
                cameFrom.putAll(cf);
            }, Integer.MAX_VALUE);

        return found.get()?  reconstructPath(cameFrom, target) : Collections.emptyList();
    }

}
