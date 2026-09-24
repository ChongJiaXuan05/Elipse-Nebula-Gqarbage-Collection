package src.game.actions;

import src.edu.monash.fit2099.engine.actions.Action;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.actors.ActorStatistics;
import src.edu.monash.fit2099.engine.positions.GameMap;
import src.edu.monash.fit2099.engine.statistics.StatisticOperations;
import src.game.items.FirstAidKit;

/**
 * Action that applies the First Aid Kit effect and starts its cooldown.
 */
public class UseFirstAidKitAction extends Action {
    private final FirstAidKit firstAidKit;

    /**
     * Creates the action for a specific first aid kit.
     *
     * @param firstAidKit first aid kit to consume
     */
    public UseFirstAidKitAction(FirstAidKit firstAidKit) {
        this.firstAidKit = firstAidKit;
    }

    /**
     * Applies first aid kit effects to the actor and starts cooldown.
     *
     * @param actor actor using the first aid kit
     * @param map map where the action occurs
     * @return action result message
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.modifyStatisticMaximum(ActorStatistics.HEALTH, StatisticOperations.INCREASE, 1);

        int missingHealth = actor.getMaximumStatistic(ActorStatistics.HEALTH)
                - actor.getStatistic(ActorStatistics.HEALTH);
        if (missingHealth > 0) {
            actor.heal(missingHealth);
        }

        firstAidKit.startCooldown();
        return actor + " uses First Aid Kit. Max health increases by 1 and health is fully restored.";
    }

    /**
     * Returns menu text for using the first aid kit.
     *
     * @param actor actor viewing the menu
     * @return menu description string
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses First Aid Kit";
    }
}