package render;

public class SimulationWelcomePrinter {
    private static final String WELCOME = "Welcome to the \"Simulation\"!";
    private static final String CONTROL_SIMULATION = "To control the simulation, use the";
    private static final String COMMANDS = "commands:";
    private static final String COMMAND_STOP = "1 - stop";
    private static final String COMMAND_PAUSE = "3 - pause";
    private static final String COMMAND_CONTINUE = "2 - continue";
    private static final String COMMAND_STEP = "4 - step";
    private static final int BOX_WIDTH = 36;

    private SimulationWelcomePrinter() {
    }

    public static void printWelcome() {
        String reset = "\u001B[0m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";

        // Верхняя граница
        printBorder(cyan, "╔", "╗", reset);

        // Заголовок
        printCenteredLine(cyan, WELCOME, reset);

        // Разделитель
        printBorder(cyan, "╠", "╣", reset);

        // Основной текст
        printCenteredLine(cyan, CONTROL_SIMULATION, reset);
        printCenteredLine(cyan, COMMANDS, reset);

        // Команды
        printCommandLine(yellow, COMMAND_STOP, reset);
        printCommandLine(yellow, COMMAND_CONTINUE, reset);
        printCommandLine(yellow, COMMAND_PAUSE, reset);
        printCommandLine(yellow, COMMAND_STEP, reset);

        // Нижняя граница
        printBorder(cyan, "╚", "╝", reset);
    }

    private static void printBorder(String color, String left, String right, String reset) {
        System.out.println(color + left + "═".repeat(BOX_WIDTH) + right + reset);
    }

    private static void printCenteredLine(String color, String text, String reset) {
        int totalSpaces = BOX_WIDTH - text.length();
        int leftSpaces = totalSpaces / 2;
        int rightSpaces = totalSpaces - leftSpaces;

        System.out.println(color + "║" + " ".repeat(leftSpaces) + text + " ".repeat(rightSpaces) + "║" + reset);
    }

    private static void printCommandLine(String color, String command, String reset) {
        System.out.println("\u001B[36m║" + color + "   " + command + " ".repeat(BOX_WIDTH - command.length() - 3) + "\u001B[36m║" + reset);
    }
}
