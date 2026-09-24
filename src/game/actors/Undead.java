package game.actors;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.BasicInventory;
import game.alarm.AlarmService;
import game.behaviours.AttackWorkerBehaviour;
import game.behaviours.ChaseWorkerBehaviour;
import game.behaviours.WanderBehaviour;

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