Assumptions

Spider

- The maximum number of possilbe allowable spiders to be spawned is 7, this is more than the minimum of 4, and less than the clutter of the screen
- It is able to walk through anything but boulders and other moving entities (except character)
- If a spider was to spawn on top of the boulder, it will just go in the reverse direction

saveGame

- The user will get access to all the saves, so they can have access to previous saves which wont be overriden.

Zombie Toast Spawner

- A user cannot walk on top of the zombie toast spawner because that is where the zombies are being spawned

Zombie

Mercenary
- It will normally move in the direction of the player
- If the path is blocked it will not move

Bribed mercenary

- A bribed mercenary will 'follow' the character on the same position

Movement order

- Character moves first.
- If character moves into boulder, then the boulder will move, then character, then merc, then spider, then zombie toast

Iteration 2

Health, Damages
Rationale:
    - Character health high enough to survive enemy hits + low enough to notice gradual decrease in damage as fights occur
    - Character attack low enough to not immediately kill some enemies (merc at full hp and zombie toast as hp goes down)
Character: Health: 120/100 (standard/hard mode), Attack Damage: 3
Spider: Health: 30, Attack Damage: 1
Zombie Toast: Health: 50, Attack Damage: 1
Mercenary: Health: 80, Attack Damage: 1

Sword: Durability: 4, Attack Damage: 1.5 x Character Attack Damage
Bow: Durability: 3, Attack Damage: 2 x Character Attack Damage
Armour: Durability: 4, Decrease Attack Damage of Enemy: 0.5
Shield: Durability: 3, Decrease Attack Damage of Enemy: 0.5
