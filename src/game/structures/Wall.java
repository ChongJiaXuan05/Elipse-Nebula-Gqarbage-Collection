package src.game.structures;

import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.positions.Ground;

/**
 * A class representing a solid wall. Yes, that's it.
 */
public class Wall extends Ground {
    /**
     * Creates a wall tile.
     */
    public Wall() {
        super('#', "Wall");
    }

    /**
     * Blocks all actor movement through walls.
     *
     * @param actor actor attempting to enter
     * @return always false
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
