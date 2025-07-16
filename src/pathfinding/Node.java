package pathfinding;

import world.Coordinate;

import java.util.Objects;

public class Node {
    private final Coordinate coordinate;
    private double g;  // Стоимость пути от старта
    private double h;  // Эвристическая оценка до цели
    private double f;  // f = g + h
    private Node parent;

    public Node(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(coordinate, node.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinate);
    }
}
