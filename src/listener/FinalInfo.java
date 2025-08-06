package listener;

import world.entity.Rabbit;
import world.entity.Wolf;

public class FinalInfo implements SimulationListener {
    private static final String TOTAL_SIMULATION = "For the entire simulation:";
    private static final String DEATH_WOLF = "Wolves died: ";
    private static final String DEATH_RABBIT = "Rabbit died: ";
    private static final String BORN_WOLF = "Wolves were born: ";
    private static final String BORN_RABBIT = "Rabbit were born: ";
    private static final String GRASS_GROW = "Grass grew: ";
    private static final String GRASS_EAT = "Grass was eaten: ";

    private static int countDeathWolf;
    private static int countDeathRabbit;
    private static int countBornWolf;
    private static int countBornRabbit;
    private static int countGrassGrown;
    private static int countEatGrass;


    @Override
    public void onEvent(SimulationEvent event) {
        switch (event.type()){
            case ENTITY_DIED:
                if(event.entity() instanceof Wolf) countDeathWolf++;
                else if (event.entity() instanceof Rabbit) countDeathRabbit++;
                break;
            case ENTITY_SPAWNED:
                if (event.entity() instanceof Wolf) countBornWolf++;
                else if (event.entity() instanceof Rabbit) countBornRabbit++;
                break;
            case GRASS_GROWING:
                countGrassGrown++;
                break;
            case EAT:
                countEatGrass++;
                break;
        }
    }

    public int getCountDeathWolf() {
        return countDeathWolf;
    }


    public int getCountDeathRabbit() {
        return countDeathRabbit;
    }


    public int getCountBornWolf() {
        return countBornWolf;
    }


    public int getCountBornRabbit() {
        return countBornRabbit;
    }

    public int getCountGrassGrown() {
        return countGrassGrown;
    }

    public int getCountEatGrass() {
        return countEatGrass;
    }

    public void printFinalInfo(){
        System.out.println(TOTAL_SIMULATION);
        System.out.println(DEATH_WOLF + this.getCountDeathWolf());
        System.out.println(DEATH_RABBIT + this.getCountDeathRabbit());
        System.out.println(BORN_WOLF + this.getCountBornWolf());
        System.out.println(BORN_RABBIT + this.getCountBornRabbit());
        System.out.println(GRASS_GROW + this.getCountGrassGrown());
        System.out.println(GRASS_EAT + this.getCountEatGrass());
    }

}
