package src.game.items;

import src.edu.monash.fit2099.engine.actions.ActionList;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.items.Item;
import src.edu.monash.fit2099.engine.positions.GameMap;
import src.edu.monash.fit2099.engine.statistics.BaseStatistic;
import src.game.actions.ConsumeAction;

/**
 * Due to severe budget cuts, the flask is only permitted to hold five (5)
 * mouthfuls of liquid per deployment. Employees are reminded not to consume
 * all five charges in a panic during a single encounter.
 */
public class Flask extends Item implements Consumable {
    int totalUsable = 5;

    /**
     * Creates a flask with five uses and fixed carry weight.
     */
    public Flask() {
        super("Flask", 'u');
        this.addNewStatistic(ItemStatistics.WEIGHT,new BaseStatistic(3));
    }

    /**
     * Drinks from flask and heals actor when charges remain.
     *
     * @param actor actor consuming from the flask
     * @return outcome message
     */
    @Override
    public String consumeBy(Actor actor) {
        if (totalUsable > 0){
            actor.heal(1);
            totalUsable -= 1;
            return actor + " drink flask, which heals them by 1 point of health.";
        } else {
            return "Flask is empty";
        }
    }

    /**
     * Returns consume action for the flask.
     *
     * @param owner actor carrying the flask
     * @param map map containing the actor
     * @return available actions for this item
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new ConsumeAction(this));
        return actions;
    }
}
