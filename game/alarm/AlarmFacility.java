package game.alarm;

import edu.monash.fit2099.engine.actors.Actor;

import java.util.Optional;

/**
 * Shared alarm state for the facility.
 */
public final class AlarmFacility implements AlarmService {
    private int turnsRemaining;
    private Actor triggeringWorker;

    /**
     * Creates an inactive alarm facility state.
     */
    public AlarmFacility() {
        this.turnsRemaining = 0;
        this.triggeringWorker = null;
    }

    /**
     * Activates alarm for the requested number of turns if it extends current duration.
     *
     * @param turns requested active turns
     */
    @Override
    public void activateForTurns(int turns) {
        if (turns > turnsRemaining) {
            turnsRemaining = turns;
        }
    }

    /**
     * Activates alarm and records the triggering worker.
     *
     * @param triggeringWorker worker that triggered the alarm
     * @param turns requested active turns
     */
    @Override
    public void activateForTurns(Actor triggeringWorker, int turns) {
        if (!isActive() || turns > turnsRemaining) {
            this.triggeringWorker = triggeringWorker;
            activateForTurns(turns);
        }
    }

    /**
     * Advances alarm state by one turn and clears trigger source when alarm expires.
     */
    @Override
    public void tickTurn() {
        if (turnsRemaining > 0) {
            turnsRemaining -= 1;
            if (turnsRemaining == 0) {
                triggeringWorker = null;
            }
        }
    }

    /**
     * Indicates whether the alarm is currently active.
     *
     * @return true if active, false otherwise
     */
    @Override
    public boolean isActive() {
        return turnsRemaining > 0;
    }

    /**
     * Returns the current worker that triggered the alarm, if available.
     *
     * @return optional triggering worker
     */
    @Override
    public Optional<Actor> getTriggeringWorker() {
        return Optional.ofNullable(triggeringWorker);
    }
}
