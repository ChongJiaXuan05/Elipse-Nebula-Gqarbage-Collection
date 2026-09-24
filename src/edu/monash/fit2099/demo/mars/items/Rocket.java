package src.edu.monash.fit2099.demo.mars.items;

import src.edu.monash.fit2099.demo.mars.DemoAbilities;
import src.edu.monash.fit2099.engine.actions.ActionList;
import src.edu.monash.fit2099.engine.actions.MoveActorAction;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.positions.Location;

public class Rocket extends MartianItem {

    private final Location destination;

    public Rocket(Location destination) {
        super("Rocket", '^');
        makeNonPortable();
        this.destination = destination;
    }

    private boolean canAnyItemTravel(Actor actor) {
        if (actor == null || !actor.hasAbility(DemoAbilities.SPACE_TRAVELLING)) {
            return false;
        }

        return actor.hasAbility(DemoAbilities.SPACE_TRAVELLING);
    }

    @Override
    public ActionList allowableActions(Location location) {
        // location.map().trackStatusEffect(new Burnable(this));
        ActionList actions = super.allowableActions(location);
        if(canAnyItemTravel(location.getActor())){
            actions.add(new MoveActorAction(destination, "to Mars!"));
        }
        return actions;
    }


}
