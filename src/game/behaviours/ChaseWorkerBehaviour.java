package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.behaviours.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.ActorRole;
import game.alarm.AlarmService;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Behaviour that pathfinds toward the worker who triggered the alarm.
 */
public class ChaseWorkerBehaviour implements Behaviour<Actor, Action> {
    private final AlarmService alarmService;

    /**
     * Creates the chase behaviour using the shared alarm service.
     *
     * @param alarmService alarm state service
     */
    public ChaseWorkerBehaviour(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    /**
     * Selects a movement action that chases the worker who triggered the alarm.
     *
     * @param actor actor executing the behaviour
     * @param location actor's current location
     * @return chase move, do-nothing when alarm target is unavailable, or null when alarm inactive
     */
    @Override
    public Action operate(Actor actor, Location location) {
        if (!alarmService.isActive()) {
            return null;
        }

        Location targetWorkerLocation = findTriggeringWorkerLocation(location);
        if (targetWorkerLocation == null) {
            return new DoNothingAction();
        }

        Action pathMove = findPathMoveTowardTarget(actor, location, targetWorkerLocation);
        if (pathMove != null) {
            return pathMove;
        }

        // Alarm is active: do not fall back to wandering.
        return new DoNothingAction();
    }

    /**
     * Resolves the map location of the worker that triggered the alarm.
     *
     * @param origin location of the pursuing actor
     * @return target worker location, or null when unavailable
     */
    private Location findTriggeringWorkerLocation(Location origin) {
        Actor triggeringWorker = alarmService.getTriggeringWorker().orElse(null);
        if (triggeringWorker == null) {
            return null;
        }

        if (!triggeringWorker.hasAbility(ActorRole.WORKER)) {
            return null;
        }

        if (!origin.map().contains(triggeringWorker)) {
            return null;
        }

        return origin.map().locationOf(triggeringWorker);
    }

    /**
     * Finds the first movement step on a shortest path toward the target worker.
     *
     * @param actor actor moving toward the target
     * @param origin starting location
     * @param targetWorkerLocation destination to approach
     * @return first move action if a path exists, otherwise null
     */
    private Action findPathMoveTowardTarget(Actor actor, Location origin, Location targetWorkerLocation) {
        Queue<Location> queue = new ArrayDeque<>();
        Set<Location> visited = new HashSet<>();
        Map<Location, Action> firstMoves = new HashMap<>();

        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty()) {
            Location current = queue.remove();

            for (Exit exit : current.getExits()) {
                Location destination = exit.getDestination();
                if (visited.contains(destination) || !destination.canActorEnter(actor)) {
                    continue;
                }

                Action inheritedFirstMove = firstMoves.get(current);
                Action firstMove = inheritedFirstMove != null
                        ? inheritedFirstMove
                        : destination.getMoveAction(actor, exit.getName(), exit.getHotKey());

                firstMoves.put(destination, firstMove);
                visited.add(destination);

                if (isAdjacent(destination, targetWorkerLocation)) {
                    return firstMove;
                }

                queue.add(destination);
            }
        }

        return null;
    }

    /**
     * Checks whether two locations are adjacent on the map.
     *
     * @param from first location
     * @param to second location
     * @return true when adjacent, false otherwise
     */
    private boolean isAdjacent(Location from, Location to) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());
        return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
    }
}
