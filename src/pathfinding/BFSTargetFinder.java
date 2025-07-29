package pathfinding;

import world.Coordinate;
import world.entity.Creature;
import world.entity.Entity;
import world.MapWorld;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class BFSTargetFinder extends BFSExplorer implements TargetFinder {

    public BFSTargetFinder(MapWorld mapWorld) {
        super(mapWorld);
    }

    @Override
    public Coordinate findNearestTarget(Creature move, Coordinate start, Predicate<Entity> targetCondition){
        AtomicReference<Coordinate> result = new AtomicReference<>(start);

        bfsSearch(start,(current, cameFrom) -> {
            Entity entity = mapWorld.getEntityPositionMap().get(current);
            if(targetCondition.test(entity)){
                result.set(current);
                throw new BFSExplorer.BFSResultFoundException();
            }
        }, move.getMaxSearchDepth());
        return result.get();
    }
}
