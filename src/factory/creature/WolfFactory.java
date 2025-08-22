package factory.creature;

import pathfinding.Pathfinder;
import world.Coordinate;
import world.entity.Wolf;

import java.util.Objects;

public class WolfFactory implements CreatureFactory<Wolf> {
    private static final String REQUIRES_PREDATOR_CONFIG = "Requires PredatorConfig";


    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HEALTH = 5;
    private static final int DEFAULT_ATTACK_POWER = 3;
    private static final int DEFAULT_SATIETY = 7;
    private static final int DEFAULT_MAX_SEARCH_DEPTH = 15;
    private static final PredatorConfig DEFAULT_CONFIG = new PredatorConfig.Builder().setSpeed(DEFAULT_SPEED).setHealth(DEFAULT_HEALTH)
            .setSatiety(DEFAULT_SATIETY).setMaxSearchDepth(DEFAULT_MAX_SEARCH_DEPTH).setAttackPower(DEFAULT_ATTACK_POWER).build();
    private final PredatorConfig defaultConfig;


    public WolfFactory(PredatorConfig defaultConfig) {
        this.defaultConfig = Objects.requireNonNull(defaultConfig);
    }

    public WolfFactory() {
        this(DEFAULT_CONFIG);
    }

    @Override
    public Wolf createDefault(Coordinate position, Pathfinder pathfinder) {
        return instantiateWolf(position,defaultConfig,pathfinder);
    }

    @Override
    public Wolf create(Coordinate position, CreatureConfig config, Pathfinder pathfinder) {
        PredatorConfig userConfig = validateConfig(config);
        return instantiateWolf(position,userConfig,pathfinder);
    }

    private Wolf instantiateWolf (Coordinate position, PredatorConfig config, Pathfinder pathfinder){
        return new Wolf(position,config.getBaseSpeed(), config.getBaseHealth(), config.getAttackPower(), config.getBaseSatiety(), config.getBaseMaxSearchDepth(),pathfinder);
    }

    private PredatorConfig validateConfig(CreatureConfig config) {
        if (!(config instanceof PredatorConfig)) {
            throw new IllegalArgumentException(REQUIRES_PREDATOR_CONFIG);
        }
        return (PredatorConfig) config;
    }
}
