package Entities.movingEntities;

import java.util.Random;

import Entities.Entities;
import Entities.EntitiesFactory;

public interface Spawnable {

    public Entities spawn(int ticksCounter, String gameMode, EntitiesFactory entitiesFactory, Random random);
    
}
