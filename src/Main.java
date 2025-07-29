
import listener.ConsoleLogger;
import world.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MapWorld mapWorld = new MapWorld(12, 12);
        Simulation simulation = new Simulation(mapWorld);
        simulation.starSimulation();




    }

}