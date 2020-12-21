package spaceraze.servlethelper.game;

import spaceraze.world.CounterType;
import spaceraze.world.Galaxy;
import spaceraze.world.UniqueIdCounter;

public class UniqueIdHandler {

    private UniqueIdHandler(){}

    public static UniqueIdCounter getUniqueIdCounter(Galaxy galaxy, CounterType type){
        return galaxy.getUniqueIdCounters().stream().filter(counter -> counter.getCounterType() == type).findFirst().orElse(createUniqueIdCounter(galaxy, type));
    }

    private static UniqueIdCounter createUniqueIdCounter(Galaxy galaxy,CounterType type){
        UniqueIdCounter counter = new UniqueIdCounter(type);
        galaxy.getUniqueIdCounters().add(counter);
        return counter;
    }
}
