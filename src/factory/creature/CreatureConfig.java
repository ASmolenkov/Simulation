package factory.creature;

public class CreatureConfig {
    protected int baseSpeed;
    protected int baseHealth;

    public CreatureConfig(int baseSpeed, int baseHealth) {
        this.baseSpeed = baseSpeed;
        this.baseHealth = baseHealth;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public abstract static class Builder<T extends Builder<T>>{
        protected int speed;
        protected int health;

        protected Builder(int defaultSpeed, int defaultHealth) {
            this.speed = defaultSpeed;
            this.health = defaultHealth;
        }

        public T setSpeed(int speed){
            this.speed = speed;
            return self();
        }

        public T setHealth(int health){
            this.health = health;
            return self();
        }

        protected abstract T self();
        public abstract CreatureConfig build();
    }
}
