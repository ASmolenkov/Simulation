
import listener.ConsoleLogger;
import world.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MapWorld mapWorld = new MapWorld(10, 10);
        Simulation simulation = new Simulation(mapWorld);
        simulation.starSimulation();




    }

}