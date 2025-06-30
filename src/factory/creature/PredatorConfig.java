package factory.creature;

public class PredatorConfig extends CreatureConfig {
    private int attackPower;

    public PredatorConfig(Builder builder) {
        super(builder.speed, builder.health);
        this.attackPower = builder.attackPower;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public static class Builder extends CreatureConfig.Builder<Builder>{
        private int attackPower = 2;

        public Builder(){
            super(1, 10);
        }

        public Builder setAttackPower(int attackPower){
            this.attackPower = attackPower;
            return this;
        }
        @Override
        protected Builder self(){ return  this; }

        @Override
        public PredatorConfig build(){
            return new PredatorConfig(this);
        }
    }
}
