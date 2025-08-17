package pathfinding;

import world.*;
import world.entity.EmptyArea;
import world.entity.Entity;
import world.entity.Grass;
import world.entity.Herbivore;

import java.util.*;
import java.util.function.BiConsumer;

public class BFSExplorer {
    private static final String TARGET_FOUND = "BFS: Target found, early exit";

    protected WorldMap worldMap;

    public BFSExplorer(WorldMap worldMap) {
        this.worldMap = worldMap;

    }

    protected void bfsSearch(Coordinate start, BiConsumer<Coordinate, Map<Coordinate, Coordinate>> processNode, int maxDepth){
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

    protected List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> cameFrom, Coordinate current){
        List <Coordinate> path = new ArrayList<>();
        while (current != null){
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path.subList(1,path.size());
    }

    private List<Coordinate> getNeighbors(Coordinate coordinate) {
        int [][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Coordinate> neighbors = new ArrayList<>();

        for (int[] dir : directions){
            int newX = coordinate.width() + dir[0];
            int newY = coordinate.height() + dir[1];
            Coordinate neighbor = new Coordinate(newX, newY);

            if(worldMap.isWithinBounds(neighbor)){
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private boolean canPassThrough(Coordinate coordinate) {
        Entity entity = worldMap.getEntityPosition().get(coordinate);
        return entity == null || entity instanceof EmptyArea || entity instanceof Grass || entity instanceof Herbivore;
    }

    protected static class BFSResultFoundException extends RuntimeException {
        public BFSResultFoundException() {
            super(TARGET_FOUND);
        }
    }
}
