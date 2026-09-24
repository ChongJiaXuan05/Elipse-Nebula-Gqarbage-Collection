package src.game;

import src.edu.monash.fit2099.engine.actions.Action;
import src.edu.monash.fit2099.engine.actions.ActionList;
import src.edu.monash.fit2099.engine.actions.DoNothingAction;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.displays.Display;
import src.edu.monash.fit2099.engine.displays.Menu;
import src.edu.monash.fit2099.engine.items.Inventory;
import src.edu.monash.fit2099.engine.positions.GameMap;
import src.game.actors.ActorRole;
import src.game.alarm.AlarmService;
import src.game.alarm.SecurityTriggerable;

/**
 * This brave soul is capable of performing complex tasks such as picking up trash
 * off the floor, swiping plastic cards at stubborn doors, and drinking mystery
 * fluids to stay alive.
 */
public class ContractedWorker extends  Actor implements SecurityTriggerable {
    /**
     * Creates a worker controlled by the player.
     *
     * @param name worker display name
     * @param displayChar character rendered on the map
     * @param hitPoints starting health
     * @param inventory starting inventory implementation
     */
    public ContractedWorker(String name, char displayChar, int hitPoints, Inventory inventory) {
        super(name, displayChar, hitPoints, inventory);
        this.enableAbility(ActorRole.WORKER);
    }

    /**
     * The playTurn method checks whether the current actor is unconscious due to environmental hazards.
     * It will generate a pick up action for each item found on the ground so that the player can pick up items
     * from the ground.
     * Additionally, ut will also handle multi-turn actions by getting the subsequent action returned by the previous action.
     * Finally, it adds all possible actions that the actor can perform in the current turn and show it on the
     * console menu for the player to choose.
     * @param actions collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do
     * interesting things in conjunction with Action.getNextAction()
     * @param map the map containing the Actor
     * @param display the I/O object to which messages may be written
     * @return the action that is chosen in the current turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (!this.isConscious()) {
            this.unconscious(map);
            return new DoNothingAction();
        }

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        System.out.println(actions);
        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    /**
     * Triggers facility alarm and marks this worker as the source.
     *
     * @param alarmService shared alarm state service
     */
    @Override
    public void triggerAlarm(AlarmService alarmService) {
        alarmService.activateForTurns(this, 10);

        System.out.println("ALERT: Facility Alarm Triggered by " + this + "! Lockdown initiated");
    }
}
