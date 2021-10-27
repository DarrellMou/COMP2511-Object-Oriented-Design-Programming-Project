Assumptions

Character:

- Only characters can go through portals

Spider

- The maximum number of possilbe allowable spiders to be spawned is 7, this is more than the minimum of 4, and less than the clutter of the screen
- It is able to walk through anything but boulders and other moving entities (except character)
- If a spider was to spawn on top of the boulder, it will just go in the reverse direction
- The Spider will break out of its cycle if the player is using the invincivility potion, and will resume the cycle once the invincivility potion is used
- Spider cannot go through portals

saveGame

- The user will get access to all the saves, so they can have access to previous saves which wont be overriden.

Zombie Toast Spawner

- A user cannot walk on top of the zombie toast spawner because that is where the zombies are being spawned and the zombie is being spawned

Zombie

Mercernary

- The mercernary will not move if there is no path to the player

Bribed mercenary

- A bribed mercenary will 'follow' the character on the same position using BFS

Movement order

- Character moves first.
- If character moves into boulder, then the boulder will move, then character, then merc, then spider, then zombie toast

Iteration 2

Battle

Battle Order:

The battle will use the first weapon in their inventory to attack.
The player will wear the first armour and shield that they have in their inventory. They cant wear more than one of each armour

Assume there is only one treasure on the map, with more than one we can bribe more than one mercenary

Health, Damages
Character: Full Health: 100, Attack Damage: 30
Spider: Full Health: 30, Attack Damage: 5
Zombie Toast: Full Health: 50, Attack Damage: 10
Mercenary: Full Health: 80, Attack Damage: 20

Sword: Durability: 4, Attack Damage: 1.5 x Character Attack Damage
Bow: Durability: 3, Attack Damage: 2 x Character Attack Damage
Armour: Durability: 4, Decrease Attack Damage of Enemy: 0.5
Shield: Durability: 3, Decrease Attack Damage of Enemy: 0.5
