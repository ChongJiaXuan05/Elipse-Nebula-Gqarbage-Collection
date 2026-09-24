package src.game.structures;

import src.edu.monash.fit2099.engine.GameEngineException;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.positions.Ground;
import src.edu.monash.fit2099.engine.positions.Location;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * A spawning ground tile that periodically creates hostile actors.
 */
public class Hole extends Ground {
    private static final int SPAWN_INTERVAL = 20;
    private final List<Supplier<Actor>> spawnables;
    private final Random random = new Random();
    private int turnsSinceSpawn = 0;

    /**
     * Creates a hole with the list of actor factories it may spawn.
     *
     * @param spawnables suppliers used to create random actors
     */
    public Hole(List<Supplier<Actor>> spawnables) {
        super('o', "Hole");
        this.spawnables = spawnables;
    }

    /**
     * Progresses spawn timer and creates a random actor when interval is reached.
     *
     * @param location location of this hole tile
     */
    @Override
    public void tick(Location location) {
        turnsSinceSpawn += 1;
        if (turnsSinceSpawn >= SPAWN_INTERVAL && !location.containsAnActor()) {
            turnsSinceSpawn = 0;
            Supplier<Actor> randomSpawner = spawnables.get(random.nextInt(spawnables.size()));
            try {
                location.addActor(randomSpawner.get());
            }catch (GameEngineException ignored){}
        }
    }
}