package factory.creature;

import pathfinding.Pathfinder;
import world.Coordinate;
import world.Wolf;

import java.util.Objects;

public class WolfFactory implements CreatureFactory<Wolf> {
    private final PredatorConfig defaultConfig;
    private static final int DEFAULT_SPEED = 0;
    private static final int DEFAULT_HEALTH = 10;
    private static final int DEFAULT_ATTACK_POWER = 3;

    public WolfFactory(PredatorConfig defaultConfig) {
        this.defaultConfig = Objects.requireNonNull(defaultConfig);
    }

    public static WolfFactory withDefaultConfig(){
        return new WolfFactory(new PredatorConfig.Builder().setSpeed(DEFAULT_SPEED).setHealth(DEFAULT_HEALTH).setAttackPower(DEFAULT_ATTACK_POWER).build());
    }


    @Override
    public Wolf createDefault(Coordinate position, Pathfinder explorer) {
        return instantiateWolf(position,defaultConfig,explorer);
    }

    @Override
    public Wolf create(Coordinate position, CreatureConfig config, Pathfinder explorer) {
        PredatorConfig userConfig = validateConfig(config);
        return instantiateWolf(position,userConfig,explorer);
    }

    private Wolf instantiateWolf (Coordinate position, PredatorConfig config, Pathfinder explorer){
        return new Wolf(position,config.getBaseSpeed(),config.getBaseHealth(),config.getAttackPower(),explorer);
    }

    private PredatorConfig validateConfig(CreatureConfig config) {
        if (!(config instanceof PredatorConfig)) {
            throw new IllegalArgumentException("Requires PredatorConfig");
        }
        return (PredatorConfig) config;
    }
}
