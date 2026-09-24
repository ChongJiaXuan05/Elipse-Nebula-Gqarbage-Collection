package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.statistics.BaseStatistic;
import game.actions.UnlockDoorAction;
import game.alarm.AlarmService;
import game.structures.Door;

/**
 * A class representing a small rectangular piece of plastic that holds entirely
 * too much power over your ability to walk through doors.
 * Its primary function is to beep happily when the player has clearance, and beep
 * angrily when they don't.
 * Essential for progressing the plot,
 *
 * @author Adrian Kristanto
 */
public class AccessCard extends Item {
    private final AlarmService alarmService;

    /**
     * Creates a portable access card tied to shared alarm state.
     *
     * @param alarmService alarm state service
     */
    public AccessCard(AlarmService alarmService) {
        super("Access Card", '▤');
        this.alarmService = alarmService;
        this.addNewStatistic(ItemStatistics.WEIGHT,new BaseStatistic(1));
        this.makePortable();
    }

    /**
     * Returns unlock actions for adjacent locked doors when alarm is inactive.
     *
     * @param owner actor carrying the card
     * @param map map containing the owner
     * @return list of unlock actions available this turn
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        if (alarmService.isActive()) {
            return new ActionList();
        }

        Location location = map.locationOf(owner);
        ActionList actions = new ActionList();
        for (Exit exit : location.getExits()) {
            Location surroundingLocation = exit.getDestination();
            Ground surroundingGround = surroundingLocation.getGround();
            if (surroundingGround instanceof Door door && !door.isUnlocked()) {
                actions.add(new UnlockDoorAction(surroundingLocation, exit.getName(), alarmService));
            }
        }
        return actions;
    }
}
