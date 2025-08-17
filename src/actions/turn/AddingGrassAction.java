package actions.turn;

import actions.Action;
import listener.EventType;
import listener.SimulationEvent;
import util.FindListEmptyCoordinates;
import world.*;
import world.entity.Entity;
import world.entity.Grass;
import java.util.List;


public class AddingGrassAction implements Action {
    private final WorldMap worldMap;
    private static final double TARGET_GRASS_PERCENTAGE = 0.05;
    private static final int MAX_REGROWTH_PER_TURN = 5;

    public AddingGrassAction(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void perform() {
        regrowGrass();

    }

    private void regrowGrass(){
        int currentGrassCount = currentAmountGrassToMapWorld();
        int totalCells = worldMap.getSize();
        int targetGrassCount = (int) (totalCells * TARGET_GRASS_PERCENTAGE);

        if(currentGrassCount < targetGrassCount){
            int grassToAdd = Math.min(targetGrassCount - currentGrassCount,MAX_REGROWTH_PER_TURN);
            addGrassInRandomEmptySpots(grassToAdd);
        }
    }

    private int currentAmountGrassToMapWorld(){
        int count = 0;
        for (Entity entity: worldMap.getAllEntity()){
            if(entity instanceof Grass){
                count++;
            }
        }
        return count;
    }

    private void addGrassInRandomEmptySpots(int amount){
        List<Coordinate> emptySpots = FindListEmptyCoordinates.getEmptyCoordinates(worldMap);
        int added = 0;
        for (Coordinate spot: emptySpots){
            if(added >= amount){
                break;
            }
            worldMap.addEntity(new Grass(spot));
            worldMap.notifyListeners(new SimulationEvent(EventType.GRASS_GROWING, String.format("🌿 the grass is growing %s", spot)));
            added++;
        }
    }
}
