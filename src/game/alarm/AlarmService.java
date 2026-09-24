package src.game.alarm;

import src.edu.monash.fit2099.engine.actors.Actor;

import java.util.Optional;

/**
 * Service contract for managing global alarm state.
 */
public interface AlarmService {
    /**
     * Activates the alarm for a fixed number of turns.
     *
     * @param turns number of turns to remain active
     */
    void activateForTurns(int turns);

    /**
     * Activates the alarm and records which worker triggered it.
     *
     * @param triggeringWorker worker that caused alarm activation
     * @param turns number of turns to remain active
     */
    void activateForTurns(Actor triggeringWorker, int turns);

    /**
     * Advances alarm timers by one src.game turn.
     */
    void tickTurn();

    /**
     * Indicates whether the alarm is currently active.
     *
     * @return true if active, false otherwise
     */
    boolean isActive();

    /**
     * Returns the worker that triggered the current alarm, if any.
     *
     * @return optional triggering worker
     */
    Optional<Actor> getTriggeringWorker();
}
