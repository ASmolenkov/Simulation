import actions.Action;
import actions.init.SpawnAction;
import actions.turn.*;
import listener.ConsoleLogger;
import listener.FinalInfo;
import pathfinding.AstarPathfinder;
import render.ConsoleRenderer;
import render.Renderer;
import render.SimulationConsolePrinter;
import world.WorldMap;
import java.util.ArrayList;
import java.util.List;


public class Simulation {
    private static final String COMMAND_STOP = "1";
    private static final String COMMAND_STEP = "4";
    private static final String COMMAND_CONTINUE = "2";
    private static final String COMMAND_PAUSE = "3";
    private static final String STOPPED_SIMULATION = "Simulation is stopped";
    private static final String QUESTION_TO_USER_TO_START  = "Start simulation? 1 - STOP, 2 - START";
    private static final String UNKNOWN_COMMAND = "Unknown command.";
    private static final String PAUSED_SIMULATION_TEMPLATE = "Simulation paused. Enter '%s' to continue or '%s' to take one step.\n";
    private static final String UNKNOWN_COMMAND_TEMPLATE = "Unknown command. Available: %s, %s, %s, %s \n";
    private static final int TIME_PAUSE = 3000;

    private final FinalInfo finalInfo = new FinalInfo();
    private final WorldMap worldMap;
    private final Renderer consoleRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    private volatile boolean isRunning = true;
    private volatile boolean isPaused = false;
    private volatile boolean stepRequested = false;

    public Simulation(WorldMap worldMap, Renderer renderer) {
        this.worldMap = worldMap;
        worldMap.addListener(new ConsoleLogger());
        worldMap.addListener(finalInfo);
        this.initActions = new ArrayList<>();
        this.initActions.add(new SpawnAction(worldMap, new AstarPathfinder(worldMap)));
        this.turnActions = new ArrayList<>();
        this.turnActions.add(new SpawnAction(worldMap,new AstarPathfinder(worldMap)));
        this.turnActions.add(new MoveCreaturesAction(worldMap));
        this.turnActions.add(new DeletedDeadCreatureAction(worldMap));
        this.turnActions.add(new HungerAction(worldMap));
        this.consoleRenderer = renderer;
    }

    public void starSimulation() throws InterruptedException {
        SimulationConsolePrinter.printWelcome();
        SimulationConsolePrinter.printInConsole(QUESTION_TO_USER_TO_START);
        while (true) {
            String inputUser = InputUser.nextLine();
            if (inputUser.equals(COMMAND_CONTINUE)) {
                startCommandListener();
                init();
                consoleRenderer.render(worldMap);

                while (isRunning) {
                    checkPauseState();
                    turn();
                    consoleRenderer.render(worldMap);
                    Thread.sleep(TIME_PAUSE);
                }
                System.out.println(STOPPED_SIMULATION);
            } else if (inputUser.equals(COMMAND_STOP)) {
                isRunning = false;
                break;
            }
            else {
                SimulationConsolePrinter.printInConsole(UNKNOWN_COMMAND);
                SimulationConsolePrinter.printInConsole(QUESTION_TO_USER_TO_START);
            }
        }
    }

    private void init() {
        initActions.forEach(Action::perform);
    }

    private void turn(){
        turnActions.forEach(Action::perform);
    }

    private void startCommandListener(){
        new Thread(()->{
            while (isRunning){
                String command = InputUser.nextLine().trim().toLowerCase();
                switch (command){
                    case COMMAND_STOP:
                        isRunning = false;
                        finalInfo.printFinalInfo();
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
