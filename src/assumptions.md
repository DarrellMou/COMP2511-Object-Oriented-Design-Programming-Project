Assumptions

Character:

- Only characters can go through portals

Spider

- There is no maximum number of possilbe allowable spiders, this is more than the minimum of 4
- It is able to walk through anything but boulders and other moving entities (except character)
- Assume that the spider spawns every 10 ticks
- If a spider was to spawn on top of the boulder, it will just go in the reverse direction
- The Spider will break out of its cycle if the player is using the invincivility potion, and will resume the cycle once the invincivility potion is used
- Spider cannot go through portals
- If a spider were to spawn on the same position as a boulder, it is able to move out of it on the next tick, however, will not be able to move back into it.

loadGame

- The itemResponse doesnt store the type of the key and door so we wont be able to match the type of keys and doors for games that are loaded.

saveGame

- The user will get access to all the saves, so they can have access to previous saves which wont be overriden.

Zombie Toast Spawner

- A user cannot walk on top of the zombie toast spawner because that is where the zombies are being spawned and the zombie is being spawned

Zombie toast
- If a zombie toast were to spawn on the same position as a boulder, it is able to move out of it on the next tick, however, will not be able to move back into it.

Mercenary
- It will normally move in the direction of the player
- If the path is blocked it will not move
- If a mercenary were to spawn on the same position as a boulder, it is able to move out of it on the next tick, however, will not be able to move back into it.
- Bribe radius is determined by adding the x and y displacement.

Bribed mercenary

- A bribed mercenary will 'follow' the character by trailing behind the player.
- After bribing a mercenary, it will recover to full hp.
- Bribed mercenary has a battle radius of 5.
- Radius is calculated by the sum of the x and y displacement. (NOT PYTHAGORAS)
- The mercenary will not take damage as it fights with the player, since it fights from a distance.

Movement order

- Character moves first, then merc, then zombie toast, then spider.
- If character moves into boulder, then the boulder will move first.

Iteration 2

Battle

Battle Order:

The battle will use the first weapon in their inventory to attack.
The player will wear the first armour and shield that they have in their inventory. They cant wear more than one of each armour

Health, Damages
Rationale: - Character health high enough to survive enemy hits + low enough to notice gradual decrease in damage as fights occur - Character attack low enough to not immediately kill some enemies (merc at full hp and zombie toast as hp goes down)
Character: Health: 120/100 (standard/hard mode), Attack Damage: 3
Spider: Health: 30, Attack Damage: 1
Zombie Toast: Health: 50, Attack Damage: 1
Mercenary: Health: 80, Attack Damage: 1

Sword: Durability: 4, Attack Damage: 1.5 x Character Attack Damage
Bow: Durability: 3, Attack Damage: 2 x Character Attack Damage
Armour: Durability: 4, Decrease Attack Damage of Enemy: 0.5
Shield: Durability: 3, Decrease Attack Damage of Enemy: 0.5

Building
- Crafting shield will use whatever is first in inventory (treasure/key)

Portal
- Walking into a portal will teleport the player to the position of the other portal translated by the movement direction.

Treasure
- If the player were to use a treasure (bribe/building material), the treasure goal will still be fulfilled.

Bomb
- Bomb will not detonate if placed near an already active floor switch.