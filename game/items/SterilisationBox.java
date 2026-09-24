package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.statistics.BaseStatistic;

/**
 * Portable item that grants sterilisation ability while carried.
 */
public class SterilisationBox extends Item {
    /**
     * Creates a portable sterilisation box.
     */
    public SterilisationBox() {
        super("Sterilisation Box", '▣');
        this.addNewStatistic(ItemStatistics.WEIGHT,new BaseStatistic(7));
        this.enableAbility(Ability.STERILISE);
        this.makePortable();
    }
}
