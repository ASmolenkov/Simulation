package pathfinding;

import world.Coordinate;
import world.entity.Creature;
import world.entity.Entity;
import world.WorldMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class BFSTargetFinder extends BFSExplorer implements TargetFinder {

    public BFSTargetFinder(WorldMap worldMap) {
        super(worldMap);
    }

    @Override
    public Coordinate findNearestTarget(Creature move, Coordinate start, Predicate<Entity> targetCondition){
        AtomicReference<Coordinate> result = new AtomicReference<>(start);

        bfsSearch(start,(current, cameFrom) -> {
            Entity entity = worldMap.getEntity(current);
            if(targetCondition.test(entity)){
                result.set(current);
                throw new BFSExplorer.BFSResultFoundException();
            }
        }, move.getMaxSearchDepth());
        return result.get();
    }

    @Override
    public Coordinate findFreeCell(Creature move, WorldMap worldMap){
        Random random = new Random();
        List<Coordinate> freeCells = new ArrayList<>();
        for (int i = 0; i < move.getMaxSearchDepth(); i++) {
            for (int j = 0; j < move.getMaxSearchDepth(); j++) {
                Coordinate coordinate = new Coordinate(move.getPosition().width() + i, move.getPosition().height() + j);
                if(move.getPosition().equals(coordinate)){
                    continue;
                }
                if(worldMap.isWithinBounds(coordinate) && !worldMap.isFreePosition(coordinate)){
                    freeCells.add(coordinate);
                }
            }
        }
        if(freeCells.isEmpty()){
            return move.getPosition();
        }
        return freeCells.get(random.nextInt(freeCells.size()));
    }

}
