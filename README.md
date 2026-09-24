```
 _______  _______  ______    _______  _______  _______  _______                          
|       ||   _   ||    _ |  |  _    ||   _   ||       ||       |                         
|    ___||  |_|  ||   | ||  | |_|   ||  |_|  ||    ___||    ___|                         
|   | __ |       ||   |_||_ |       ||       ||   | __ |   |___                          
|   ||  ||       ||    __  ||  _   | |       ||   ||  ||    ___|                         
|   |_| ||   _   ||   |  | || |_|   ||   _   ||   |_| ||   |___                          
|_______||__| |__||___|  |_||_______||__| |__||_______||_______|                         
 _______  _______  ___      ___      _______  _______  _______  ___   _______  __    _   
|       ||       ||   |    |   |    |       ||       ||       ||   | |       ||  |  | |  
|       ||   _   ||   |    |   |    |    ___||       ||_     _||   | |   _   ||   |_| |  
|       ||  | |  ||   |    |   |    |   |___ |       |  |   |  |   | |  | |  ||       |  
|      _||  |_|  ||   |___ |   |___ |    ___||      _|  |   |  |   | |  |_|  ||  _    |  
|     |_ |       ||       ||       ||   |___ |     |_   |   |  |   | |       || | |   |  
|_______||_______||_______||_______||_______||_______|  |___|  |___| |_______||_|  |__|  
 ___   __    _  _______                                                                  
|   | |  |  | ||       |                                                                 
|   | |   |_| ||       |                                                                 
|   | |       ||       |                                                                 
|   | |  _    ||      _| ___                                                             
|   | | | |   ||     |_ |   |                                                            
|___| |_|  |__||_______||___|                                                                                                                                                                                                                         
```
# Eclipse Nebula

A turn-based roguelike src.game played in the terminal, written in Java.

You play as a contracted worker exploring an abandoned moon facility. Scavenge food and tools, avoid fire and poison, and watch out for the alarm. If it goes off, the doors lock and every Undead in the facility comes hunting for you.

## Features

- **Enemy AI.** Enemies pick their action from a priority list of behaviours: attack, chase, eat, or wander. When the alarm is triggered, Undead use breadth-first-search pathfinding to track down the worker who set it off.
- **Alarm system.** Alarms lock doors and turn enemies hostile for a set number of turns.
- **Survival items.** Food, a flask, a first aid kit with a cooldown, a sterilisation box, a lantern that can leak and start fires, and an access card for locked doors.
- **Status effects.** Burn and Poison deal damage over several turns.
- **Weight-limited inventory.** Each worker can carry up to 50 units of weight.
- **Enemy spawning.** Holes in the ground spawn a random Slime or Undead every 20 turns.

## Map legend

| Symbol | Meaning | Symbol | Meaning |
|---|---|---|---|
| `ඞ` | Player (worker) | `#` | Wall |
| `⍾` | Slime | `=` | Door |
| `Ѫ` | Undead | `!` | Alarm |
| `o` | Spawn hole | `~` | Puddle |
| `^` | Fire | `.` / `_` | Dirt / Floor |

## Design

The src.game is built on an existing turn-based Java engine framework (the `src.edu.monash.fit2099.engine` package). All gameplay code is in `src/src.game`.

A few of the main design decisions:

- A shared `Consumable` interface means any new food or drink item works with the existing `ConsumeAction`, with no changes needed.
- An abstract `AutonomousActor` class runs enemies through a list of pluggable `Behaviour`s, so adding a new enemy only requires choosing its behaviours.
- An `AlarmService` interface is passed into doors, alarms, and enemies rather than being hard-coded, which keeps those classes loosely coupled.

UML class diagrams and the full design rationale are in `docs/design`.

## Project structure

```
src/src.game/
├── actions/      Player actions (attack, consume, unlock door, first aid)
├── actors/       Enemies and the AutonomousActor base class
├── alarm/        Alarm service and interfaces
├── behaviours/   Enemy AI behaviours
├── items/        Items and the Consumable interface
├── statuses/     Burn and Poison effects
└── structures/   Terrain types (walls, doors, fire, holes, etc.)
```
