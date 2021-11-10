Assumptions

Milestone 1 + 2:

Spider

- There is no maximum number of possilbe allowable spiders, this is more than the minimum of 4
- It is able to walk through anything but boulders and other moving entities (except character)
- Assume that the spider spawns every 10 ticks
- If a spider was to spawn on top of the boulder, it will just go in the reverse direction
- The Spider will break out of its cycle if the player is using the invincivility potion, and will resume the cycle once the invincivility potion buff ends.
- Spider cannot go through portals
- If a spider were to spawn on the same position as a boulder, it is able to move out of it on the next tick, however, will not be able to move back into it.
- If a spider walks into a portal, the spider will rotate around the position of the other portal translated by it's movement direction.
- If a spider's initial movement up is blocked by a boulder/entity, it will go down, then anticlockwise.

saveGame

- The user will get access to all the saves, so they can have access to previous saves which wont be overriden.

Zombie Toast Spawner

- A user cannot walk on top of the zombie toast spawner because that is where the zombies are being spawned and the zombie is being spawned
- The spawner will try to spawn in the following order: above, right, below, left.
- If no squares cardinally adjacent to the spawned are open, then nothing will spawn.

Zombie toast

- If a zombie toast were to spawn on the same position as a boulder, it is able to move out of it on the next tick, however, will not be able to move back into it.

Mercenary

- Uses Dijkstra's algorithm to determine the shortest path to the character and moves in that direction.
- If there are no path to the character or if the character has consumed the invisibility potion, the mercenary will not move.
- If a mercenary were to spawn on the same position as a boulder, it is able to move out of it on the next tick, however, will not be able to move back into it.
- Bribe radius is calculated by the sum of the x and y displacement.
- Spawn every 30 consecutive ticks with at least one enemy on field.

Bribed mercenary

- A bribed mercenary will 'follow' the character by trailing behind the player.
- After bribing a mercenary, it will recover to full hp.
- Bribed mercenary has a battle radius of 5.
- Radius is calculated by the sum of the x and y displacement.
- The mercenary will not take damage as it fights with the player, since it fights from a distance.

Assassin

- Follows same movement as mercenary.
- 20% chance to spawn instead of mercenary
- Bribe radius is same as mercenary.
- Bribable with a treasure and the one ring.

Hydra

- Health = 200, Attack Damage = 2
- Spawns at random location (like spider)
- Movement is random (up, right, down, left) like zombie toast
- If the hydra were to heal past it's max health, it will be restored to full health instead.
- The chances of Ally and Character to damage/heal the hydra are independent. For instance, an ally does not always have to damage the hydra if the character damages the hydra and vice versa.

Movement order

- Character moves first, then assassin, then merc, then zombie toast, then spider, then hydra.
- If character moves into boulder, then the boulder will move first.

Battle Order:

- The battle will use the first weapon in their inventory to attack (unless fighting a boss, where it will use Anduril if character has it).
- The player will wear the first armour and shield that they have in their inventory.
- They cant wear more than one of each armour.

Health, Damages:

Rationale: - Character health high enough to survive enemy hits + low enough to notice gradual decrease in damage as fights occur - Character attack low enough to not immediately kill some enemies (merc at full hp and zombie toast as hp goes down)
Character: Health: 120/100 (standard/hard mode), Attack Damage: 3
Spider: Health: 30, Attack Damage: 1
Zombie Toast: Health: 50, Attack Damage: 1
Mercenary: Health: 80, Attack Damage: 1
Assassin: Health: 80, Attack Damage: 4
Hydra: Health: 200, Attack Damage: 2

Sword: Durability: 4, Attack Damage: 1.5 x Character Attack Damage
Bow: Durability: 3, Attack Damage: 2 x Character Attack Damage
Armour: Durability: 4, Decrease Attack Damage of Enemy: 0.5
Shield: Durability: 3, Decrease Attack Damage of Enemy: 0.5
Anduril:

- Does 1.5x character damage (like normal sword) to non-boss enemies
- Durability = 4

Building

- Crafting shield will use whatever is first in inventory (treasure/key)

Portal

- Walking into a portal will teleport the player to the position of the other portal translated by the initial movement direction (and interact with any entities that are there, except portal).
- If the other portal translated by the initial movement direction is blocked, then the player will be teleported to the position of the other portal instead (if there is a boulder/locked door, then the player will try to move the boulder/unlock the door).
- If the player's movement is blocked whilst on the same position as a portal, it will trigger the portal and the player will end up on the position of the other portal.

Treasure

- If the player were to use a treasure (bribe/building material), the treasure goal cannot be fulfilled anymore unless another treasure spawns.

Bomb

- Bomb will not detonate if placed near an already active floor switch.

Invisibility Potion

- Have a 10 tick duration.
- Takes priority over invincibility potion (both in movement and in battle)

Invincibility Potion

- Have a 10 tick duration.
- Does not consume durability in battle
- One shots enemies when active and character does not take damage

Milestone 3:

Sun Stone
- Opening doors and bribing will first try to use sun stone before any other items

Midnight Armour
- If the player attemps to build midnight armour while a zombie exists, raise InvalidActionException error

Swamp Tile
- Character is able to push boulders if they are on swamp tile, although they do not move off of tile.

Sceptre
The Sceptre will be used either when the character interacts with the mercenary or interacts with the assassin. In either case, once used all the assassins and mercenary will be allies for 10 ticks. The interact will look for the sceptre first to bribe and then any other other additional material if there is no sceptre.
- After 10 ticks of holding the sceptre, the mercenary and assasins will no longer be allies, and the sceptre will be destroyed and removed from the inventory
  Swamp Tile

Characters
- Character is able to push boulders if they are on swamp tile, although they do not move off of tile.

Mobs
- Mobs can jump into portals if they are on swamp tile ignoring movement factor.
