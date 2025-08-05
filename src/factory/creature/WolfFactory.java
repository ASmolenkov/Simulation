package factory.creature;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.Coordinate;
import world.entity.Wolf;

import java.util.Objects;

public class WolfFactory implements CreatureFactory<Wolf> {
    private final PredatorConfig defaultConfig;
    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HEALTH = 10;
    private static final int DEFAULT_ATTACK_POWER = 3;
    private static final int DEFAULT_SATIETY = 7;
    private static final int DEFAULT_MAX_SEARCH_DEPTH = 15;


    public WolfFactory(PredatorConfig defaultConfig) {
        this.defaultConfig = Objects.requireNonNull(defaultConfig);
    }

    public static WolfFactory withDefaultConfig(){
        return new WolfFactory(new PredatorConfig.Builder().setSpeed(DEFAULT_SPEED).setHealth(DEFAULT_HEALTH).setAttackPower(DEFAULT_ATTACK_POWER).setSatiety(DEFAULT_SATIETY).setMaxSearchDepth(DEFAULT_MAX_SEARCH_DEPTH).build());
    }

    @Override
    public Wolf createDefault(Coordinate position, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        return instantiateWolf(position,defaultConfig,targetExplorer, pathExplorer);
    }

    @Override
    public Wolf create(Coordinate position, CreatureConfig config, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        PredatorConfig userConfig = validateConfig(config);
        return instantiateWolf(position,userConfig,targetExplorer, pathExplorer);
    }

    private Wolf instantiateWolf (Coordinate position, PredatorConfig config, TargetFinder targetFinder, Pathfinder pathfinder){
        return new Wolf(position,config.getBaseSpeed(), config.getBaseHealth(), config.getAttackPower(), config.getBaseSatiety(), config.getBaseMaxSearchDepth(), targetFinder, pathfinder);
    }

    private PredatorConfig validateConfig(CreatureConfig config) {
        if (!(config instanceof PredatorConfig)) {
            throw new IllegalArgumentException("Requires PredatorConfig");
        }
        return (PredatorConfig) config;
    }
}
