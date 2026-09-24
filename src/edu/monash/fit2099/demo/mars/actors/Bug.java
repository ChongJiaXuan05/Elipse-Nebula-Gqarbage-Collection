package src.edu.monash.fit2099.demo.mars.actors;

import src.edu.monash.fit2099.demo.forest.BasicInventory;
import src.edu.monash.fit2099.demo.mars.actions.KickAction;
import src.edu.monash.fit2099.engine.actions.Action;
import src.edu.monash.fit2099.engine.actions.ActionList;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.behaviours.Behaviour;
import src.edu.monash.fit2099.engine.displays.Display;
import src.edu.monash.fit2099.engine.positions.GameMap;

import java.util.*;

public class Bug extends Actor {

    private final Random rand = new Random();
    private final Map<Integer, Behaviour<Actor, Action>> behaviours = new TreeMap<>();

    public Bug() {
        super("Feature", 'x', 1, new BasicInventory());
    }

    public void addNewBehaviour(Integer priority, Behaviour behaviour) {
        this.behaviours.put(priority, behaviour);
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour<Actor, Action> behaviour : behaviours.values()) {
            Action action = behaviour.operate(this, map.locationOf(this));
            if (action != null) {
                return action;
            }
        }
        return actions.get(rand.nextInt(actions.size()));
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList list = super.allowableActions(otherActor, direction, map);
        list.add(new KickAction(this));
        return list;
    }
}
