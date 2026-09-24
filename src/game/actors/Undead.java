package src.game.actors;

import src.edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import src.game.BasicInventory;
import src.game.alarm.AlarmService;
import src.game.behaviours.AttackWorkerBehaviour;
import src.game.behaviours.ChaseWorkerBehaviour;
import src.game.behaviours.WanderBehaviour;

/**
 * A hostile autonomous enemy that hunts workers, especially during alarms.
 */
public class  Undead extends AutonomousActor {

    /**
     * Creates an undead with combat and movement behaviours.
     *
     * @param alarmService alarm service used to pick chase/attack targets
     */
    public Undead(AlarmService alarmService) {
        super("Undead", 'Ѫ', 15, new BasicInventory());
        setIntrinsicWeapon(new IntrinsicWeapon(1, "punches", 10, "bare fist") { });
        this.enableAbility(ActorRole.HOSTILE);
        addBehaviour(new AttackWorkerBehaviour(alarmService));
        addBehaviour(new ChaseWorkerBehaviour(alarmService));
        addBehaviour(new WanderBehaviour());
    }
}