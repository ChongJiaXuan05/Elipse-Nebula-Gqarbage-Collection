package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.behaviours.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Inventory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for autonomous NPCs that pick the first valid action from ordered behaviours.
 */
public abstract class AutonomousActor extends Actor {
    private final List<Behaviour<Actor, Action>> behaviours = new ArrayList<>();

    /**
     * Constructor for the AutonomousActor.
     *
     * @param name        The name of the actor (e.g., "Slime", "Undead").
     * @param displayChar The character that will represent the actor on the display.
     * @param hitPoints   The starting and maximum health points of the actor.
     * @param inventory   The inventory allocated to the actor for carrying items.
     */
    protected AutonomousActor(String name, char displayChar, int hitPoints, Inventory inventory) {
        super(name, displayChar, hitPoints, inventory);
    }

    /**
     * Registers a new behavior to the actor's AI logic.
     * Behaviors should be added in order of priority. For example, if an actor should
     * prioritize attacking over wandering, the attack behavior must be added before
     * the wander behavior.
     *
     * @param behaviour The behavior strategy to append to the decision-making list.
     */
    protected final void addBehaviour(Behaviour<Actor, Action> behaviour) {
        behaviours.add(behaviour);
    }

    /**
     * Determines the action the autonomous actor will perform during its turn.
     *
     * @param actions    A collection of possible Actions for this Actor (generally ignored by NPCs).
     * @param lastAction The Action this Actor took last turn.
     * @param map        The map containing the Actor.
     * @param display    The I/O object to which messages may be written.
     * @return The Action to be performed for the current turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        // Loop through all behaviors in order of priority
        for (Behaviour<Actor, Action> behaviour : behaviours) {
            Action selected = behaviour.operate(this, location);
            // If the behavior successfully decides on an action, perform it
            if (selected != null) {
                return selected;
            }
        }
        return new DoNothingAction();
    }
}
