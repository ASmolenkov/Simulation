package pathfinding;

import world.Coordinate;
import world.WorldMap;
import world.entity.Entity;

import java.util.*;

public class AstarPathfinder implements Pathfinder {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private final WorldMap worldMap;

    public AstarPathfinder(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public List<Coordinate> findPathToTarget(Coordinate start, Class<? extends Entity> targetClass) {
        Optional<Coordinate> nearestTarget = findNearestTarget(start, targetClass);
        if(nearestTarget.isEmpty()){
            return Collections.emptyList();
        }
        return findPath(start, nearestTarget.get());
    }

    //TODO сделать метод поиска цели приватным, переработать класс Creature


    private Optional<Coordinate> findNearestTarget(Coordinate start, Class<? extends Entity> targetClass){
        return worldMap.getAllCoordinates().stream().filter(coordinate -> {
            Entity entity = worldMap.getEntity(coordinate);
            return targetClass.isInstance(entity);
        }).min(Comparator.comparingInt(coordinate -> heuristic(start, coordinate)));

    }

    private List<Coordinate> findPath(Coordinate start, Coordinate target){
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

            for(Node neighbor:getNeighbors(current, target)){
                if(closetSet.contains(neighbor) || !worldMap.isWithinBounds(neighbor.getCoordinate())){
                    continue;
                }

                double tentativeG = gScore.get(current) + 1;

                if(!gScore.containsKey(neighbor) || tentativeG < gScore.get(neighbor)){
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    neighbor.setG(tentativeG);
                    neighbor.setH(heuristic(neighbor.getCoordinate(),target));
                    neighbor.setF(neighbor.getG() + neighbor.getH());

                    if(!openSet.contains(neighbor)){
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private int heuristic (Coordinate a, Coordinate b){
        return Math.abs(a.width() - b.width()) + Math.abs(a.height() - b.height());
    }

    private List<Coordinate> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Coordinate> path = new ArrayList<>();
        path.add(current.getCoordinate());

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.addFirst(current.getCoordinate());
        }
        if (!path.isEmpty()) {
            path.removeFirst();
        }
        return path;
    }

    private List<Node> getNeighbors(Node node, Coordinate target) {
        List<Node> neighbors = new ArrayList<>();
        for (int dir[] : DIRECTIONS) {
            int newX = node.getCoordinate().width() + dir[0];
            int newY = node.getCoordinate().height() + dir[1];
            Coordinate newCoordinate = new Coordinate(newX, newY);
            if (worldMap.isWithinBounds(newCoordinate)) {
                if(newCoordinate.equals(target) || worldMap.isFreePosition(newCoordinate)) {
                    neighbors.add(new Node(newCoordinate));
                }
            }
        }
        return neighbors;
    }

}
