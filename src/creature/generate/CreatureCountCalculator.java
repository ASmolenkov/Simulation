package creature.generate;

import world.CreatureType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreatureCountCalculator {
    private final Random random;
    private final double percentageFillMapWorld;
    private final Map<CreatureType, Double> spawnProbabilities;

    public CreatureCountCalculator(Random random, double percentageFillMapWorld) {
        this.random = random;
        this.percentageFillMapWorld = percentageFillMapWorld;
        this.spawnProbabilities = new HashMap<>();
    }

    public void addCreatureTypeAndProbabilities(CreatureType type, double probabilities){
        spawnProbabilities.put(type,probabilities);
    }

    public Map<CreatureType, Integer> calculateCounts(int mapSize){
        int maxCreatures = (int) (mapSize * percentageFillMapWorld);
        int totalCreatures = random.nextInt(maxCreatures / 2) + maxCreatures / 2;
        Map<CreatureType, Integer> counts = new HashMap<>();
        spawnProbabilities.forEach((type, aDouble) ->{
            counts.put(type, (int) (totalCreatures * aDouble));
        } );
        return counts;
    }
}
