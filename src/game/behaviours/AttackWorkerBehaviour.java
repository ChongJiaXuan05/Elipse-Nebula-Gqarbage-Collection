package src.game.behaviours;

import src.edu.monash.fit2099.engine.actions.Action;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.behaviours.Behaviour;
import src.edu.monash.fit2099.engine.positions.Exit;
import src.edu.monash.fit2099.engine.positions.Location;
import src.edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import src.game.actions.AttackActorAction;
import src.game.actors.ActorRole;
import src.game.alarm.AlarmService;

/**
 * Behaviour that attacks adjacent workers, prioritising the alarm trigger when active.
 */
public class AttackWorkerBehaviour implements Behaviour<Actor, Action> {
    private final AlarmService alarmService;

    /**
     * Creates the behaviour with access to global alarm state.
     *
     * @param alarmService alarm state service
     */
    public AttackWorkerBehaviour(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    /**
     * Selects an attack action against an adjacent worker target.
     *
     * @param actor actor executing the behaviour
     * @param location actor's current location
     * @return attack action when a valid target exists, otherwise null
     */
    @Override
    public Action operate(Actor actor, Location location) {
        IntrinsicWeapon weapon = actor.getIntrinsicWeapon();
        if (weapon == null) {
            return null;
        }

        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (!destination.containsAnActor()) {
                continue;
            }

            Actor target = destination.getActor();
            if (target.hasAbility(ActorRole.WORKER)) {
                if (alarmService.isActive()) {
                    Actor triggeringWorker = alarmService.getTriggeringWorker().orElse(null);
                    if (target != triggeringWorker) {
                        continue;
                    }
                }
                return new AttackActorAction(target, exit.getName(), weapon);
            }
        }

        return null;
    }
}
