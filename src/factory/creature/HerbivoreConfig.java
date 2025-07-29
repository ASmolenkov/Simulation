package factory.creature;

public class HerbivoreConfig extends CreatureConfig {

    public HerbivoreConfig(Builder builder) {
        super(builder.speed, builder.health, builder.satiety, builder.maxSearchDepth);
    }

    public static class Builder extends CreatureConfig.Builder<HerbivoreConfig.Builder>{

        public Builder(){
            super(2,5, 5, 7);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public HerbivoreConfig build() {
            return new HerbivoreConfig(this);
        }
    }
}
