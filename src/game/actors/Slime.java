package src.game.actors;

import src.game.BasicInventory;
import src.game.behaviours.ConsumeBehaviour;
import src.game.behaviours.WanderBehaviour;

/**
 * A basic autonomous creature that consumes nearby items and wanders.
 */
public class Slime extends AutonomousActor {

    /**
     * Creates a slime with default inventory and behaviours.
     */
    public Slime() {
        super("Slime", '⍾', 25, new BasicInventory());
        addBehaviour(new ConsumeBehaviour());
        addBehaviour(new WanderBehaviour());
    }
}