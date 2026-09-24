package src.game;

import src.edu.monash.fit2099.engine.displays.Display;
import src.edu.monash.fit2099.engine.items.Inventory;
import src.edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import src.edu.monash.fit2099.engine.positions.GameMap;
import src.edu.monash.fit2099.engine.GameEngineException;
import src.edu.monash.fit2099.engine.positions.World;
import src.game.actors.Slime;
import src.game.actors.Undead;
import src.game.alarm.AlarmFacility;
import src.game.alarm.AlarmService;
import src.game.items.*;
import src.game.structures.*;

import java.util.Arrays;
import java.util.List;

/**
 * This class handles the miracle of creation, translating a bunch of periods
 * and hashtags into a sprawling, functional sci-fi facility.
 */
public class EclipseNebula extends World {
    private AlarmService alarmService;

    /**
     * Creates the src.game world wrapper with a display.
     *
     * @param display display used by the world
     */
    public EclipseNebula(Display display) {
        super(display);
    }

    /**
     * Initialise maps, actors, items, and grounds of the src.game world.
     * @throws Exception in case if anything goes wrong...
     */
    public void initialise() throws Exception {
        this.alarmService = new AlarmFacility();

        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Dirt::new);
        groundCreator.registerGround('#', Wall::new);
        groundCreator.registerGround('~', Puddle::new);
        groundCreator.registerGround('_', Floor::new);
        groundCreator.registerGround('!', () -> new Alarm(alarmService));
        groundCreator.registerGround('=', () -> new Door(alarmService));
        groundCreator.registerGround('o', () -> new Hole(List.of(Slime::new, () -> new Undead(alarmService))));

        List<String> moon99Deprecated = Arrays.asList(
                "....................########################################",
                "...#######..........#__________________#________________o__#",
                "...#_____#..........=__________________=______!____________#",
                "...#_____=...~......#__________________#___________________#",
                "...#_____#..~~~.....########=#####=#####___#############___#",
                "...#######.~~~~.....#______#_#_________#___#___________#___#",
                ".........~~~~.......#______#_#_________#####___________#####",
                "....................#______=_#_________#___________________#",
                "......~.............#______#_#_________#___________________#",
                ".....~~~............#______#_###########___#############___#",
                ".....~..............#______#___________#___#___________#___#",
                "....................=______#___________=___=_____o_____=___#",
                "....................#______#############___#############___#",
                ".........~~~~.......#______#___________#####################",
                "........~~~~~~......#______#___________=___________________#",
                ".........~~~~.......#______#___________#___________________#",
                "....................#______#############___#############___#",
                "....................#______#_____o_____#___#___________#_o_#",
                "..~.................#______=___________=___=___________=___#",
                "....................########################################"
        );

        GameMap moon99DeprecatedMap = new GameMap("99-Deprecated", groundCreator, moon99Deprecated);
        this.addGameMap(moon99DeprecatedMap);

        moon99DeprecatedMap.at(7, 2).addItem(new AccessCard(alarmService));
        moon99DeprecatedMap.at(7,3).addItem(new FirstAidKit());
        moon99DeprecatedMap.at(8,3).addItem(new SterilisationBox());
        moon99DeprecatedMap.at(6,2).addItem(new Apple());
        moon99DeprecatedMap.at(5,2).addItem(new Cookies());

        moon99DeprecatedMap.at(24,2).addItem(new Apple());
        moon99DeprecatedMap.at(34,3).addItem(new Cookies());

        moon99DeprecatedMap.at(42,1).addItem(new Apple());
        moon99DeprecatedMap.at(50,2).addItem(new Cookies());

        moon99DeprecatedMap.at(23,15).addItem(new Apple());
        moon99DeprecatedMap.at(26,10).addItem(new Cookies());

        moon99DeprecatedMap.at(48,10).addItem(new Apple());
        moon99DeprecatedMap.at(50,17).addItem(new Cookies());

        moon99DeprecatedMap.at(24,3).addItem(new Lantern());
        moon99DeprecatedMap.at(35,1).addItem(new FloppyDisk());
        moon99DeprecatedMap.at(42,1).addItem(new CRTMonitor());

        Inventory inventory1 = new WeightLimitedInventory(50);
        inventory1.add(new Flask());
        Inventory inventory2 = new WeightLimitedInventory(50);
        inventory2.add(new Flask());
//        Inventory inventory3 = new WeightLimitedInventory(50);
//        inventory3.add(new Flask());
//        Inventory inventory4 = new WeightLimitedInventory(50);
//        inventory4.add(new Flask());
//        Inventory inventory5 = new WeightLimitedInventory(50);
//        inventory5.add(new Flask());
        // BEHOLD, LOCAL MULTIPLAYER!!!
        ContractedWorker contractedWorker1 = new ContractedWorker("#1 Bob", 'ඞ', 10, inventory1);
        ContractedWorker contractedWorker2 = new ContractedWorker("#2 Tom", 'ඞ', 10, inventory2);
//        ContractedWorker contractedWorker3 = new ContractedWorker("#3 Sarah", 'ඞ', 10, inventory3);
//        ContractedWorker contractedWorker4 = new ContractedWorker("#4 Julie", 'ඞ', 10, inventory4);
//        ContractedWorker contractedWorker5 = new ContractedWorker("#5 Rick", 'ඞ', 10, inventory5);
        this.addPlayer(contractedWorker1, moon99DeprecatedMap.at(6, 2));
//        this.addPlayer(contractedWorker2, moon99DeprecatedMap.at(7, 2));
//        this.addPlayer(contractedWorker3, moon99DeprecatedMap.at(8, 2));
//        this.addPlayer(contractedWorker4, moon99DeprecatedMap.at(6, 4));
//        this.addPlayer(contractedWorker5, moon99DeprecatedMap.at(8, 4));
    }

    /**
     * Runs one src.game-loop cycle and advances global alarm state.
     *
     * @throws GameEngineException if the engine encounters a loop error
     */
    @Override
    protected void gameLoop() throws GameEngineException {
        super.gameLoop();
        if (alarmService != null) {
            if (alarmService.isActive()) {
                display.println("Facility Alarm is Active! Door are locked and enemies are hostile");
            }
            alarmService.tickTurn();
        }
    }
}
