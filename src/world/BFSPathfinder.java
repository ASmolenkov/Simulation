package world;

import java.util.*;

public class BFSPathfinder {

    public List<Coordinate> findPath(MapWorld mapWorld, Coordinate start, Coordinate target){
        Queue<Coordinate> queue = new LinkedList<>();
        Map<Coordinate,Coordinate> cameFrom = new HashMap<>();
        Set<Coordinate> visited = new HashSet<>();

        queue.add(start);
        cameFrom.put(start,null);
        visited.add(start);

        while (!queue.isEmpty()){
            Coordinate current = queue.poll();

            if(current.equals(target)){
                return reconstructPath(cameFrom, current);
            }
            for (Coordinate neighbor: getNeighbors(current, mapWorld)){
                if(!visited.contains(neighbor) && mapWorld.isPositionAvailable(neighbor)){
                    visited.add(neighbor);
                    cameFrom.put(neighbor,current);
                    queue.add(neighbor);
                }
            }
        }
        return Collections.emptyList();

    }

    private static List<Coordinate> reconstructPath(Map<Coordinate,Coordinate> cameFrom, Coordinate current){
        List<Coordinate> path = new ArrayList<>();
        while (current != null){
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path.subList(1, path.size());
    }

    private static List<Coordinate> getNeighbors(Coordinate coordinate, MapWorld mapWorld){
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
}
