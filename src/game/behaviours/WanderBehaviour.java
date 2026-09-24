package src.game.behaviours;

import src.edu.monash.fit2099.engine.actions.Action;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.behaviours.Behaviour;
import src.edu.monash.fit2099.engine.positions.Exit;
import src.edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.Random;

/**
 * Behaviour that moves an actor to a random reachable adjacent tile.
 */
public class WanderBehaviour implements Behaviour<Actor, Action> {
    private final Random random = new Random();

    /**
     * Selects a random valid adjacent move.
     *
     * @param actor actor executing the behaviour
     * @param location actor's current location
     * @return random move action, or null when no moves are available
     */
    @Override
    public Action operate(Actor actor, Location location) {
        ArrayList<Action> wanderActions = new ArrayList<>();

        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                wanderActions.add(destination.getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

        if (wanderActions.isEmpty()) {
            return null;
        }

        return wanderActions.get(random.nextInt(wanderActions.size()));
    }
}
