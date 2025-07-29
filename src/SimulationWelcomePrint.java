public class SimulationWelcomePrint {
    private static final String WELCOME = "    Welcome to the \"Simulation\"!    ";
    private static final String CONTROL_SIMULATION = " To control the simulation, use the:";
    private static final String COMMANDS = "  commands:";
    private static final String COMMAND_STOP = "stop";
    private static final String COMMAND_PAUSE = "pause";
    private static final String COMMAND_CONTINUE = "continue";
    private static final String COMMAND_STEP= "step";

    private SimulationWelcomePrint() {
    }
    public static void printWelcome(){
        String reset = "\u001B[0m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";

        System.out.println(cyan + "╔════════════════════════════════════╗" + reset);
        System.out.println(cyan + "║" + reset + WELCOME + cyan + "║" + reset);
        System.out.println(cyan + "╠════════════════════════════════════╣" + reset);
        System.out.println(cyan + "║" + reset + CONTROL_SIMULATION + cyan + "║" + reset);
        System.out.println(cyan + "║" + reset + COMMANDS + yellow + COMMAND_STOP + reset + "," + yellow + COMMAND_CONTINUE + reset +
                "," + yellow + COMMAND_PAUSE + reset + "," + yellow + COMMAND_STEP + reset + " " + cyan + "║" + reset);
        System.out.println(cyan + "╚════════════════════════════════════╝" + reset);
    }
}
