package listener;

import world.entity.Entity;

public record SimulationEvent(EventType type, String message, Entity entity) {
    public SimulationEvent(EventType type, String message){
        this(type,message,null);
    }
}
