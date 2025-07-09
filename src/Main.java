
import world.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MapWorld mapWorld = new MapWorld(13, 13);
        Simulation simulation = new Simulation(mapWorld,15);
        simulation.starSimulation();




    }

}