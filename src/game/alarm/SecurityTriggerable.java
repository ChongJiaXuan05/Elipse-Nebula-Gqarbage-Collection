package game.alarm;

/**
 * Contract for actors that can trigger the facility alarm.
 */
public interface SecurityTriggerable {
    /**
     * Triggers the alarm using the provided alarm service.
     *
     * @param alarmService alarm state service
     */
    void triggerAlarm(AlarmService alarmService);
}
