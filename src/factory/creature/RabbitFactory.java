package factory.creature;

import world.*;

import java.util.Objects;

public class RabbitFactory implements CreatureFactory<Rabbit> {
    private final HerbivoreConfig defaultConfig;
    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HEALTH = 4;


    public RabbitFactory(HerbivoreConfig defaultConfig) {
        this.defaultConfig = Objects.requireNonNull(defaultConfig);
    }

    public static RabbitFactory withDefaultConfig(){
        return new RabbitFactory(new HerbivoreConfig.Builder().setSpeed(DEFAULT_SPEED).setHealth(DEFAULT_HEALTH).build());
    }

    @Override
    public Rabbit createDefault(Coordinate position, BFSExplorer explorer) {
        return instantiateRabbit(position,defaultConfig,explorer);
    }

    @Override
    public Rabbit create(Coordinate position, CreatureConfig config, BFSExplorer explorer) {
        HerbivoreConfig userConfig = validateConfig(config);
        return instantiateRabbit(position,userConfig,explorer);
    }

    private Rabbit instantiateRabbit (Coordinate position, HerbivoreConfig config, BFSExplorer explorer){
        return new Rabbit(position,config.getBaseSpeed(),config.getBaseHealth(),explorer);
    }

    private HerbivoreConfig validateConfig(CreatureConfig config) {
        if (!(config instanceof HerbivoreConfig)) {
            throw new IllegalArgumentException("Requires PredatorConfig");
        }
        return (HerbivoreConfig) config;
    }
}
