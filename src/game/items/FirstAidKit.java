package src.game.items;

import src.edu.monash.fit2099.engine.actions.ActionList;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.items.Item;
import src.edu.monash.fit2099.engine.positions.GameMap;
import src.edu.monash.fit2099.engine.positions.Location;
import src.edu.monash.fit2099.engine.statistics.BaseStatistic;
import src.game.actions.UseFirstAidKitAction;

/**
 * A consumable utility item that increases maximum health and then enters cooldown.
 */
public class FirstAidKit extends Item {
    private static final int COOLDOWN_TURNS = 20;
    private int cooldownRemaining = 0;

    /**
     * Creates a portable first aid kit.
     */
    public FirstAidKit() {
        super("First Aid Kit", '+');
        this.addNewStatistic(ItemStatistics.WEIGHT, new BaseStatistic(25));
        this.makePortable();
    }

    /**
     * Starts the cooldown timer after use.
     */
    public void startCooldown() {
        cooldownRemaining = COOLDOWN_TURNS;
    }

    /**
     * Advances cooldown by one turn while carried.
     *
     * @param currentLocation current location of the carrier
     * @param actor actor carrying the kit
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        if (cooldownRemaining > 0) {
            cooldownRemaining -= 1;
        }
    }

    /**
     * Returns the use action only when cooldown is complete.
     *
     * @param owner actor carrying the kit
     * @param map map containing the actor
     * @return available actions for this item
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();
        if (cooldownRemaining == 0) {
            actions.add(new UseFirstAidKitAction(this));
        }
        return actions;
    }
}
