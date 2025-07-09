import actions.Action;
import actions.init.GenerateCreatureAction;
import actions.init.GenerateLandscapeAction;
import actions.turn.AddingGrassAction;
import actions.turn.AddingHerbivoreAction;
import actions.turn.MoveCreaturesAction;
import render.ConsoleRenderer;
import world.MapWorld;
import java.util.ArrayList;
import java.util.List;


public class Simulation {
    private final MapWorld mapWorld;
    private final SimulationSettings simulationSettings;
    private int moveCounter;
    private ConsoleRenderer consoleRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    public Simulation(MapWorld mapWorld, int moveCounter) {
        this.mapWorld = mapWorld;
        this.simulationSettings = new SimulationSettings(mapWorld);
        this.initActions = new ArrayList<>();
        this.initActions.add(new GenerateLandscapeAction());
        this.initActions.add(new GenerateCreatureAction(simulationSettings.getCreatureSpawner(),
                mapWorld,simulationSettings.getCreatureCountCalculator(),simulationSettings.getEmptyCoordinateFinder()));


        this.turnActions = new ArrayList<>();
        this.turnActions.add(new AddingGrassAction(mapWorld));
        this.turnActions.add(new AddingHerbivoreAction(mapWorld));
        this.turnActions.add(new MoveCreaturesAction());
        this.moveCounter = moveCounter;

        this.consoleRenderer = new ConsoleRenderer();

    }

    public void starSimulation() throws InterruptedException {
            init();
            consoleRenderer.render(mapWorld);
        System.out.println();
        while (moveCounter > 5){
            turn();
            consoleRenderer.render(mapWorld);
            moveCounter--;
            System.out.println();
            Thread.sleep(3000);
        }


    }

    private void init() {
        initActions.forEach(action -> action.perform(mapWorld));
    }

    private void turn(){
        turnActions.forEach(action -> action.perform(mapWorld));
    }

}
