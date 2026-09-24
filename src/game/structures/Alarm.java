package game.structures;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.alarm.AlarmService;
import game.alarm.SecurityTriggerable;

/**
 * A pressure-plate-like ground tile that triggers the alarm on entry.
 */
public class Alarm extends Ground {
    private final AlarmService alarmService;
    private SecurityTriggerable lastActorOnTile;

    /**
     * Creates an alarm tile backed by the shared alarm service.
     *
     * @param alarmService alarm state service
     */
    public Alarm(AlarmService alarmService) {
        super('!', "Alarm");
        this.alarmService = alarmService;
        this.lastActorOnTile = null;
    }

    /**
     * Triggers alarm when a new triggerable actor steps onto this tile.
     *
     * @param location location of this alarm tile
     */
    @Override
    public void tick(Location location) {
        SecurityTriggerable currentTriggerable = location.getActorAs(SecurityTriggerable.class);

        if (currentTriggerable != null && currentTriggerable != lastActorOnTile) {
            currentTriggerable.triggerAlarm(alarmService);
        }

        lastActorOnTile = currentTriggerable;
    }
}
