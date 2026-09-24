package src.game.structures;

import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.positions.Ground;
import src.edu.monash.fit2099.engine.positions.Location;
import src.game.alarm.AlarmService;

/**
 * Its primary purpose in the universe is to halt the progress of underpaid
 * {@code ContractedWorker}s until they can produce the correct rectangular
 * piece of plastic.
 */
public class Door extends Ground {
    private boolean isUnlocked = false;
    private final AlarmService alarmService;

    /**
     * Creates a locked door controlled by alarm state.
     *
     * @param alarmService shared alarm service
     */
    public Door(AlarmService alarmService) {
        super('=', "Door");
        this.alarmService = alarmService;
    }

    /**
     * if the door is unlocked, any actor can step into the door
     * @param actor the Actor to check
     * @return true if the door is unlocked, false otherwise.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return isUnlocked() && !alarmService.isActive();
    }

    /**
     * Re-locks the door while the alarm is active.
     *
     * @param location location of this door
     */
    @Override
    public void tick(Location location) {
        if (alarmService.isActive()) {
            lock();
        }
    }

    /**
     * Indicates whether this door is currently unlocked.
     *
     * @return true when unlocked
     */
    public boolean isUnlocked() {
        return isUnlocked;
    }

    /**
     * Unlocks this door.
     */
    public void unlock() {
        this.isUnlocked = true;
    }

    /**
     * Locks this door.
     */
    public void lock() {
        this.isUnlocked = false;
    }
}
