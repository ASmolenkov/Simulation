package pathfinding;

import world.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class BFSPathFinder implements Pathfinder {
    private final MapWorld mapWorld;
    private final int MAX_SEARCH_DEPTH = 100;

    public BFSPathFinder(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
    }

    public Coordinate findNearestTarget(Coordinate start, Predicate<Entity> targetCondition){
        AtomicReference<Coordinate> result = new AtomicReference<>(start);

        bfsSearch(start,(current, cameFrom) -> {
            Entity entity = mapWorld.getEntityPositionMap().get(current);
            if(targetCondition.test(entity)){
                result.set(current);
                throw new BFSResultFoundException();
            }
        }, MAX_SEARCH_DEPTH);
        return result.get();
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

        return found.get()? reconstructPath(cameFrom, target) : Collections.emptyList();
    }

    private void bfsSearch(Coordinate start, BiConsumer<Coordinate, Map<Coordinate, Coordinate>> processNode, int maxDepth){
        Queue<Coordinate> queue = new LinkedList<>();
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        Set<Coordinate> visited = new HashSet<>();
        int currentDepth = 0;

        queue.add(start);
        cameFrom.put(start, null);
        visited.add(start);

        while (!queue.isEmpty() && currentDepth < maxDepth) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Coordinate current = queue.poll();

                try {
                    processNode.accept(current, cameFrom);
                }catch (BFSResultFoundException e){
                    return;
                }
                for (Coordinate neighbor : getNeighbors(current)) {
                    if (!visited.contains(neighbor) && canPassThrough(neighbor)) {
                        visited.add(neighbor);
                        cameFrom.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        currentDepth++;
        }
    }

    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> cameFrom, Coordinate current){
        List <Coordinate> path = new ArrayList<>();
        while (current != null){
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path.subList(1,path.size());
    }

    private List<Coordinate> getNeighbors(Coordinate coordinate) {
        // Общая логика получения соседей
        int [][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Coordinate> neighbors = new ArrayList<>();

        for (int[] dir : directions){
            int newX = coordinate.getWidth() + dir[0];
            int newY = coordinate.getHeight() + dir[1];
            Coordinate neighbor = new Coordinate(newX, newY);

            if(mapWorld.isWithinBounds(neighbor)){
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private boolean canPassThrough(Coordinate coordinate) {
        // Общая проверка проходимости
        Entity entity = mapWorld.getEntityPositionMap().get(coordinate);
        return entity == null || entity instanceof EmptyArea || entity instanceof Grass || entity instanceof Herbivore;
    }

    private static class BFSResultFoundException extends RuntimeException {
        // Можно добавить сообщение для отладки
        public BFSResultFoundException() {
            super("BFS: Target found, early exit");
        }
    }
}
