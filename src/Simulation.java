import actions.Action;
import render.ConsoleRenderer;
import world.MapWorld;
import java.util.List;

public class Simulation {
    private final MapWorld mapWorld;
    private int moveCounter;
    private ConsoleRenderer consoleRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    public Simulation(MapWorld mapWorld, List<Action> initActions, List<Action> turnActions) {
        this.mapWorld = mapWorld;
        this.initActions = initActions;
        this.turnActions = turnActions;
        this.consoleRenderer = new ConsoleRenderer();
    }

    public void starSimulation(){
            init();
            consoleRenderer.render(mapWorld);
        System.out.println();
        while (moveCounter < 2){
            turn();
            consoleRenderer.render(mapWorld);
            moveCounter++;
            System.out.println();
        }


    }

    private void init() {
        initActions.forEach(action -> action.perform(mapWorld));
    }

    private void turn(){
        turnActions.forEach(action -> action.perform(mapWorld));
    }
}
