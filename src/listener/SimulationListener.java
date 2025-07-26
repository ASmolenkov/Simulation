package listener;

public interface SimulationListener {

    void onEvent(SimulationEvent event);
    default void onDayCycle(int day){

    }
}
