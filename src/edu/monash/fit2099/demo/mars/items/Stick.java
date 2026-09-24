package src.edu.monash.fit2099.demo.mars.items;

import src.edu.monash.fit2099.engine.items.Item;
import src.edu.monash.fit2099.engine.positions.Location;

public class Stick extends Item {

    public Stick() {
        super("stick", '/');
        makePortable();
    }

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
    }

}
