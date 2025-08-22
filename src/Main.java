
import render.ConsoleRenderer;
import render.Renderer;
import world.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        WorldMap worldMap = new WorldMap(10, 10);
        Renderer consoleRender = new ConsoleRenderer();
        Simulation simulation = new Simulation(worldMap, consoleRender);
        simulation.starSimulation();

    }

}