
import world.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MapWorld mapWorld = new MapWorld(15, 15);
        Simulation simulation = new Simulation(mapWorld);
        simulation.starSimulation();




    }

}