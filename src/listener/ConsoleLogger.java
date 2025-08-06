package listener;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger implements SimulationListener{

    private static final String BLOOD_SPILLED = "⚔️ Blood was spilled!";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void onEvent(SimulationEvent event) {
        if(event.type() == EventType.ATTACK){
            System.out.println(BLOOD_SPILLED);
        }
        System.out.printf("[%s] %s\n", LocalTime.now().format(TIME_FORMATTER), event.message());
    }
}
