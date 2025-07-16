package pathfinding;

import world.*;

import java.util.*;
import java.util.function.Predicate;

public class AStarPathfinder implements Pathfinder {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private final MapWorld mapWorld;

    public AStarPathfinder(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
    }


    @Override
    public Coordinate findNearestTarget(Coordinate start, Predicate<Entity> targetCondition) {
        return null;
    }

    @Override
    public List<Coordinate> findPathToTarget(Coordinate start, Coordinate target) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        Set<Node> closetSet = new HashSet<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> gScore = new HashMap<>();

        Node startNode = new Node(start);
        Node targetNode = new Node(target);

        startNode.setG(0);
        startNode.setH(heuristic(start, target));
        startNode.setF(startNode.getG() + startNode.getH());

        openSet.add(startNode);
        gScore.put(startNode, 0.0);

        while (!openSet.isEmpty()){
            Node current = openSet.poll();
            if(current.equals(targetNode)){
                return reconstructPath(cameFrom, current);
            }
            closetSet.add(current);

            for (Node neighbor: getNeighbors(current)){
                if(closetSet.contains(neighbor) || !canPassThrough(neighbor.getCoordinate())){
                    continue;
                }

                double tentativeG = gScore.get(current) + 1;

                if(!gScore.containsKey(neighbor) || tentativeG < gScore.get(neighbor)){
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    neighbor.setG(tentativeG);
                    neighbor.setH(heuristic(neighbor.getCoordinate(), target));
                    neighbor.setF(neighbor.getG() + neighbor.getH());

                    if(!openSet.contains(neighbor)){
                        openSet.add(neighbor);
                    }
                }

            }
        }
        return Collections.emptyList();
    }

    private double heuristic (Coordinate a, Coordinate b){
        return Math.abs(a.getWidth() - b.getWidth()) + Math.abs(a.getHeight() - b.getHeight());
    }

    private List<Node> getNeighbors(Node node){
        List<Node> neighbors = new ArrayList<>();
        for (int dir[]: DIRECTIONS){
            int newX = node.getCoordinate().getWidth() + dir[0];
            int newY = node.getCoordinate().getHeight() + dir[1];
            Coordinate newCoordinate = new Coordinate(newX, newY);
            if(mapWorld.isWithinBounds(newCoordinate)){
                neighbors.add(new Node(newCoordinate));
            }
        }
        return neighbors;
    }

    private List<Coordinate> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Coordinate> path = new ArrayList<>();
        path.add(current.getCoordinate());

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current.getCoordinate()); // Добавляем в начало
        }

        return path;
    }

    private boolean canPassThrough(Coordinate coordinate) {
        // Общая проверка проходимости
        Entity entity = mapWorld.getEntityPositionMap().get(coordinate);
        return entity == null || entity instanceof EmptyArea || entity instanceof Grass || entity instanceof Herbivore;
    }



}
