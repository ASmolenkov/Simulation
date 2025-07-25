package factory.creature;

public class CreatureConfig {
    protected int baseSpeed;
    protected int baseHealth;
    protected int baseSatiety;

    public CreatureConfig(int baseSpeed, int baseHealth, int baseSatiety) {
        this.baseSpeed = baseSpeed;
        this.baseHealth = baseHealth;
        this.baseSatiety = baseSatiety;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getBaseSatiety(){
        return baseSatiety;
    }

    public abstract static class Builder<T extends Builder<T>>{
        protected int speed;
        protected int health;
        protected int satiety;

        protected Builder(int defaultSpeed, int defaultHealth, int defaultSatiety) {
            this.speed = defaultSpeed;
            this.health = defaultHealth;
            this.satiety = defaultHealth;
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
