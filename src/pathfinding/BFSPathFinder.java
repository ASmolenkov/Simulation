package pathfinding;

import world.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class BFSPathFinder extends BFSExplorer implements Pathfinder {


    public BFSPathFinder(MapWorld mapWorld) {
        super(mapWorld);
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
