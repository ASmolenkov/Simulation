package pathfinding;

import world.Coordinate;
import world.Entity;
import world.MapWorld;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class BFSTargetFinder extends BFSExplorer implements TargetFinder {

    public BFSTargetFinder(MapWorld mapWorld) {
        super(mapWorld);
    }

    @Override
    public Coordinate findNearestTarget(Coordinate start, Predicate<Entity> targetCondition){
        AtomicReference<Coordinate> result = new AtomicReference<>(start);

        bfsSearch(start,(current, cameFrom) -> {
            Entity entity = mapWorld.getEntityPositionMap().get(current);
            if(targetCondition.test(entity)){
                result.set(current);
                throw new BFSExplorer.BFSResultFoundException();
            }
        }, MAX_SEARCH_DEPTH);
        return result.get();
    }
}
