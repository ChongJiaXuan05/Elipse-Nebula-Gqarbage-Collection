package src.game.statuses;

import src.edu.monash.fit2099.engine.GameEntity;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.capabilities.Status;
import src.edu.monash.fit2099.engine.positions.Location;

/**
 * Damage-over-time status effect applied by fire hazards.
 */
public class Burn implements Status {
    private int turnsRemaining;
    private int damage;

    /**
     * Creates a burn effect with a fixed duration.
     *
     * @param turns number of turns the effect should remain active
     */
    public Burn(int damage, int turns) {
        this.turnsRemaining = turns;
    }

    /**
     * Applies one turn of burn damage and decreases remaining duration.
     *
     * @param currEntity entity holding the status
     * @param location location of the entity
     */
    @Override
    public void tickStatus(GameEntity currEntity, Location location) {
        if (turnsRemaining > 0 && currEntity instanceof Actor actor) {
            actor.hurt(damage);
            turnsRemaining -= 1;
        }
    }

    /**
     * Indicates whether burn should continue affecting the entity.
     *
     * @return true while turns remain
     */
    @Override
    public boolean isStatusActive() {
        return turnsRemaining > 0;
    }
}
