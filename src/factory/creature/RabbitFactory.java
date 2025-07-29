package factory.creature;

import pathfinding.Pathfinder;
import pathfinding.TargetFinder;
import world.*;
import world.entity.Rabbit;

import java.util.Objects;

public class RabbitFactory implements CreatureFactory<Rabbit> {
    private final HerbivoreConfig defaultConfig;
    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HEALTH = 5;
    private static final int DEFAULT_SATIETY = 7;
    private static final int DEFAULT_MAX_SEARCH_DEPTH = 10;



    public RabbitFactory(HerbivoreConfig defaultConfig) {
        this.defaultConfig = Objects.requireNonNull(defaultConfig);
    }

    public static RabbitFactory withDefaultConfig(){
        return new RabbitFactory(new HerbivoreConfig.Builder().setSpeed(DEFAULT_SPEED).setHealth(DEFAULT_HEALTH).setSatiety(DEFAULT_SATIETY).setMaxSearchDepth(DEFAULT_MAX_SEARCH_DEPTH).build());
    }

    @Override
    public Rabbit createDefault(Coordinate position, TargetFinder targetExplorer, Pathfinder pathExplorer) {
        return instantiateRabbit(position,defaultConfig,targetExplorer, pathExplorer);
    }

    @Override
    public Rabbit create(Coordinate position, CreatureConfig config, TargetFinder targetFinder, Pathfinder pathfinder) {
        HerbivoreConfig userConfig = validateConfig(config);
        return instantiateRabbit(position,userConfig,targetFinder,pathfinder);
    }

    private Rabbit instantiateRabbit (Coordinate position, HerbivoreConfig config,TargetFinder targetFinder, Pathfinder pathfinder){
        return new Rabbit(position,config.getBaseSpeed(),config.getBaseHealth(), config.getBaseSatiety(), config.baseMaxSearchDepth, targetFinder, pathfinder);
    }

    private HerbivoreConfig validateConfig(CreatureConfig config) {
        if (!(config instanceof HerbivoreConfig)) {
            throw new IllegalArgumentException("Requires PredatorConfig");
        }
        return (HerbivoreConfig) config;
    }
}
