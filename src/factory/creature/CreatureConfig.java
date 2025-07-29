package factory.creature;

public class CreatureConfig {
    protected int baseSpeed;
    protected int baseHealth;
    protected int baseSatiety;
    protected int baseMaxSearchDepth;

    public CreatureConfig(int baseSpeed, int baseHealth, int baseSatiety, int baseMaxSearchDepth) {
        this.baseSpeed = baseSpeed;
        this.baseHealth = baseHealth;
        this.baseSatiety = baseSatiety;
        this.baseMaxSearchDepth = baseMaxSearchDepth;
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

    public int getBaseMaxSearchDepth(){
        return baseMaxSearchDepth;
    }

    public abstract static class Builder<T extends Builder<T>>{
        protected int speed;
        protected int health;
        protected int satiety;
        protected int maxSearchDepth;

        protected Builder(int defaultSpeed, int defaultHealth, int defaultSatiety, int defaultMaxSearchDepth) {
            this.speed = defaultSpeed;
            this.health = defaultHealth;
            this.satiety = defaultSatiety;
            this.maxSearchDepth = defaultMaxSearchDepth;
        }

        public T setSpeed(int speed){
            this.speed = speed;
            return self();
        }

        public T setHealth(int health){
            this.health = health;
            return self();
        }

        public T setSatiety(int satiety){
            this.satiety = satiety;
            return self();
        }

        public T setMaxSearchDepth (int maxSearchDepth){
            this.maxSearchDepth = maxSearchDepth;
            return self();
        }

        protected abstract T self();
        public abstract CreatureConfig build();
    }
}
