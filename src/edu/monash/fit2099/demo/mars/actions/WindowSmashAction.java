package src.edu.monash.fit2099.demo.mars.actions;

import src.edu.monash.fit2099.demo.mars.grounds.Floor;
import src.edu.monash.fit2099.engine.actions.Action;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.positions.GameMap;
import src.edu.monash.fit2099.engine.positions.Location;

import java.util.Random;


public class WindowSmashAction extends Action {

	private final String direction;
	private final Location windowLocation;
	private final Random rand = new Random();
	
	public WindowSmashAction(String direction, Location windowLocation) {
		this.direction = direction;
		this.windowLocation = windowLocation;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		if(rand.nextBoolean()) {
			return actor + " hurts their foot.";
		}
		else {
			windowLocation.setGround(new Floor());
			return "The window is smashed";
		}
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " smashes the window to the " + direction;
	}
}
