package listener;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger implements SimulationListener{

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void onEvent(SimulationEvent event) {
        if(event.getType() == EventType.ATTACK){
            System.out.println("⚔️ Blood was spilled!");
        }
        System.out.printf("[%s] %s\n", LocalTime.now().format(TIME_FORMATTER), event.getMessage());
    }
}
