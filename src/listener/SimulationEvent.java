package listener;

import world.entity.Entity;

public class SimulationEvent {
    private final EventType type;
    private final String message;
    private final Entity entity;

    public SimulationEvent(EventType type, String message, Entity entity) {
        this.type = type;
        this.message = message;
        this.entity = entity;
    }

    public EventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Entity getEntity() {
        return entity;
    }
}
