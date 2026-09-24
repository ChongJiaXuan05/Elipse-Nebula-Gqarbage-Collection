package src.edu.monash.fit2099.demo.mars.items;


import src.edu.monash.fit2099.demo.mars.DemoAbilities;

public class SpaceSuit extends MartianItem {

    public SpaceSuit() {
        super("Space suit", '[');
        makePortable();
        this.enableAbility(DemoAbilities.SPACE_TRAVELLING);
    }
}
