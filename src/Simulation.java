import actions.Action;
import actions.init.GenerateCreatureAction;
import actions.init.GenerateLandscapeAction;
import actions.turn.*;
import listener.ConsoleLogger;
import render.ConsoleRenderer;
import world.MapWorld;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Simulation {
    private static final String COMMAND_STOP = "stop";
    private static final String COMMAND_STEP = "step";
    private static final String COMMAND_CONTINUE = "continue";
    private static final String COMMAND_PAUSE = "pause";
    private static final String STOPPED_SIMULATION = "Simulation is stopped";
    private static final String PAUSED_SIMULATION_TEMPLATE = "Simulation paused. Enter '%s' or '%s\n";
    private static final String UNKNOWN_COMMAND_TEMPLATE = "Unknown command. Available: %s, %s, %s, %s \n";
    private static final int TIME_PAUSE = 3000;


    private final MapWorld mapWorld;
    private final SimulationSettings simulationSettings;
    private final ConsoleRenderer consoleRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    private volatile boolean isRunning = true;
    private volatile boolean isPaused = false;
    private volatile boolean stepRequested = false;

    public Simulation(MapWorld mapWorld) {
        this.mapWorld = mapWorld;
        mapWorld.addListener(new ConsoleLogger());
        this.simulationSettings = new SimulationSettings(mapWorld);
        this.initActions = new ArrayList<>();
        this.initActions.add(new GenerateLandscapeAction(mapWorld));
        this.initActions.add(new GenerateCreatureAction(simulationSettings.getCreatureSpawner()));
        this.turnActions = new ArrayList<>();
        this.turnActions.add(new AddingGrassAction(mapWorld));
        this.turnActions.add(new AddingCreatureAction(mapWorld));
        this.turnActions.add(new MoveCreaturesAction(mapWorld));
        this.turnActions.add(new DeletedDeadCreatureAction(mapWorld));
        this.turnActions.add(new HungerAction(mapWorld));
        this.consoleRenderer = new ConsoleRenderer();

    }

    public void starSimulation() throws InterruptedException {
        SimulationWelcomePrint.printWelcome();

        startCommandListener();
        init();
        consoleRenderer.render(mapWorld);

        while (isRunning){
            checkPauseState();
            turn();
            consoleRenderer.render(mapWorld);
            Thread.sleep(TIME_PAUSE);
        }
        System.out.println(STOPPED_SIMULATION);
    }

    private void init() {
        initActions.forEach(Action::perform);
    }

    private void turn(){
        turnActions.forEach(Action::perform);
    }

    private void startCommandListener(){
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (isRunning){
                String command = scanner.nextLine().trim().toLowerCase();
                switch (command){
                    case COMMAND_STOP:
                        isRunning = false;
                        break;
                    case COMMAND_PAUSE:
                        isPaused = true;
                        System.out.printf(PAUSED_SIMULATION_TEMPLATE, COMMAND_CONTINUE,COMMAND_STEP);
                        break;
                    case COMMAND_CONTINUE:
                        isPaused = false;
                        synchronized (this){
                            this.notify();
                        }
                        break;
                    case COMMAND_STEP:
                        stepRequested = true;
                        synchronized (this){
                            this.notify();
                        }
                        break;
                    default:
                        System.out.printf(UNKNOWN_COMMAND_TEMPLATE, COMMAND_STOP,COMMAND_PAUSE,COMMAND_CONTINUE,COMMAND_STEP);
                }
            }
            scanner.close();
        }).start();
    }

    private void checkPauseState(){
        if(isPaused && !stepRequested){
            synchronized (this){
                try {
                    this.wait();
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }
        if(stepRequested){
            stepRequested = false;
        }
    }

}
