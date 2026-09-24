package src.game.actions;

import src.edu.monash.fit2099.engine.actions.Action;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.positions.Exit;
import src.edu.monash.fit2099.engine.positions.GameMap;
import src.edu.monash.fit2099.engine.positions.Ground;
import src.edu.monash.fit2099.engine.positions.Location;
import src.game.alarm.AlarmService;
import src.game.structures.Door;

/**
 * The bureaucratic process of asking a piece of the environment for permission to pass.
 */
public class UnlockDoorAction extends Action {
    private final Location targetDoorLocation;
    private final String direction;
    private final AlarmService alarmService;

    /**
     * Creates an action to unlock a specific adjacent door.
     *
     * @param targetDoorLocation location of the target door
     * @param direction direction label for menu output
     * @param alarmService shared alarm service
     */
    public UnlockDoorAction(Location targetDoorLocation, String direction, AlarmService alarmService) {
        this.targetDoorLocation = targetDoorLocation;
        this.direction = direction;
        this.alarmService = alarmService;
    }

    /**
     * When executed, it unlocks a specific adjacent door.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the description of the result of the action of opening a door
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (alarmService.isActive()) {
            return "Facility alarm lockdown is active. Doors cannot be unlocked.";
        }

        Location currentLocation = map.locationOf(actor);
        boolean isSurroundingDoor = false;
        for (Exit exit : currentLocation.getExits()) {
            if (exit.getDestination() == targetDoorLocation) {
                isSurroundingDoor = true;
                break;
            }
        }
        if (!isSurroundingDoor) {
            return "The target door is no longer around you.";
        }

        Ground targetGround = targetDoorLocation.getGround();
        if (targetGround instanceof Door door) {
            door.unlock();
            return actor + " unlocked " + door + " to the " + direction + ".";
        }

        return "There is no door to unlock in that direction.";
    }

    /**
     * Returns the menu text for this unlock action.
     *
     * @param actor actor viewing the menu
     * @return menu description string
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " unlocks door to the " + direction;
    }
}
