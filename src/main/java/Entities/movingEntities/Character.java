package Entities.movingEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Entities.BeforeWalkedOn;
import Entities.Entities;
import Entities.WalkedOn;
import Entities.movingEntities.Buildable.NodeFactory;
import Items.InventoryItem;
import Items.ItemsFactory;
import Items.TheOneRingItem;
import Items.Equipments.Armours.Armours;
import Items.Equipments.Shields.Shields;
import Items.Equipments.Weapons.AndurilItem;
import Items.Equipments.Weapons.Weapons;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.Buffs;
import dungeonmania.Buffs.Invincible;
import dungeonmania.util.Battle;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Character extends Mobs implements WalkedOn, Portalable {

    /**
     * inventory = [ {item1}, {item2}... ]
     */
    private static int MAX_HEALTH;
    public static Object getInventory;
    private static final int ATTACK_DAMAGE = 3;
    private ArrayList<InventoryItem> inventory;
    private Fightable inBattleWith = null;
    private Position prevPosition;
    private List<Buffs> buffs = new ArrayList<Buffs>();
    private Position spawnPos;

    public Character(String id, Position position) {
        super(id, "player", position, false, true, Character.MAX_HEALTH, ATTACK_DAMAGE);
        setPrevPosition(getPosition());
        this.spawnPos = getPosition();
        inventory = new ArrayList<InventoryItem>();
    }

    /**
     * 
     * @return spawnPos
     */
    public Position getSpawnPos() {
        return spawnPos;
    }

    /**
     * 
     * @param spawnPos
     */
    public void setSpawnPos(Position spawnPos) {
        this.spawnPos = spawnPos;
    }

    /**
     * @return Buffs
     */
    public Buffs getBuffs(Class<?> cls) {
        for (Buffs buff : getBuffs()) {
            if (cls.isInstance(buff)) {
                return buff;
            }
        }
        return null;
    }

    /**
     * @param b
     */
    public void addBuff(Buffs b) {
        for (Buffs buff : getBuffs()) {
            if (buff.getClass() == b.getClass()) {
                getBuffs().remove(buff);
                break;
            }
        }
        buffs.add(b);
    }

    /**
     * @param b
     */
    public void removeBuff(Buffs b) {
        buffs.remove(b);

    }

    /**
     * @return List<Buffs>
     */
    public List<Buffs> getBuffs() {
        return buffs;
    }

    public static int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public static void setMAX_HEALTH(int MAX_HEALTH) {
        Character.MAX_HEALTH = MAX_HEALTH;
    }

    /**
     * @return InventoryItem
     */
    public InventoryItem getInventoryItem(Class<?> cls) {
        for (InventoryItem item : getInventory()) {
            if (cls.isInstance(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * @return ArrayList<InventoryItem>
     */
    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    /**
     * @param inventory
     */
    public void setInventory(ArrayList<InventoryItem> inventory) {
        this.inventory = inventory;
    }

    /**
     * @param item
     */
    public void addInventory(InventoryItem item) {
        inventory.add(item);
    }

    /**
     * @param item
     */
    public void removeInventory(InventoryItem item) {
        inventory.remove(item);
    }

    /**
     * @param collectable
     * @param dungeon
     */
    public Map<String, List<InventoryItem>> checkForBuildables(Dungeon dungeon, String buildable) {
        dungeon.setBuildables(new ArrayList<String>());
        Map<String, List<InventoryItem>> buildables = new HashMap<>();
        
        List<String> buildableItems = new ArrayList<>();
        if (buildable == null) {
            buildableItems.add("bow");
            buildableItems.add("shield");
            buildableItems.add("sceptre");
            buildableItems.add("midnight_armour");
        } else {
            buildableItems.add(buildable);
        }

        for (String buildableItem : buildableItems) {
            List<InventoryItem> materials = new ArrayList<>();
            List<InventoryItem> inventoryCopy = new ArrayList<>();
            Iterator<InventoryItem> iterator = inventory.iterator();
            while(iterator.hasNext()) {
                //Add the object clones
                InventoryItem item = iterator.next();
                inventoryCopy.add(new InventoryItem(item.getId(), item.getType()));  
            }
            if (NodeFactory.makeTree(buildableItem).evaluate(inventoryCopy, materials)) {
                buildables.put(buildableItem, materials);
                dungeon.addBuildables(buildableItem);
            }
        }
        
       
        return buildables;
    }

    /**
     * @param buildable
     * @return boolean
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public boolean build(String buildable, Dungeon dungeon) throws IllegalArgumentException, InvalidActionException {
        List<String> buildableItems = new ArrayList<>();
        buildableItems.add("bow");
        buildableItems.add("shield");
        buildableItems.add("sceptre");
        buildableItems.add("midnight_armour");

        if (!buildableItems.contains(buildable)) {
            throw new IllegalArgumentException("Buildable given is not bow, shield, sceptre or midnight armour!!!");
        }

        Map<String, List<InventoryItem>> buildables = checkForBuildables(dungeon, buildable);

        if (!buildables.containsKey(buildable)) {
            throw new InvalidActionException("You do not have the materials to build that!!!");
        }

        if (buildable.equals("midnight_armour")) {
            for (Entities entity : dungeon.getEntities()) {
                if (entity.getType().equals("zombie_toast")) {
                    throw new InvalidActionException("You cannot build Midnight Armour with zombies around!!!");
                }
            }
        }

        inventory.removeAll(buildables.get(buildable));
        InventoryItem item = ItemsFactory.createItem(buildable);
        inventory.add(item);
        return true;
    }

    /**
     * @param dungeon
     */
    @Override
    public void makeMovement(Dungeon dungeon) {
        setInBattleWith(null);
        Position newPosition = getPosition().translateBy(getMovementDirection());
        if (checkMovable(newPosition, dungeon)) {
            walkOn(newPosition, dungeon);
            setPrevPosition(getPosition());

            // If position changed after walking on newPosition
            // (e.g. walking into portal)
            if (!getPosition().translateBy(getMovementDirection()).equals(newPosition)) {
                Position newerPosition = getPosition().translateBy(getMovementDirection());
                if (checkMovable(newerPosition, dungeon)) {
                    walkOn(newerPosition, dungeon);
                    setPosition(newerPosition, dungeon);
                }
            } else {
                setPosition(newPosition, dungeon);
            }
        } else {
            for (Entities e : dungeon.getEntitiesOnTile(getPosition())) {
                // Walking on spot, call walkedOn for entities on current position
                if (e instanceof WalkedOn) {
                    WalkedOn w = (WalkedOn) e;
                    w.walkedOn(dungeon, this);
                }
            }
        }
    }

    /**
     * @param position
     * @param dungeon
     * @return boolean
     */
    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            // Calls walkedOn for entities which should do something if a character wishes
            // to walk on it (e.g. boulder movement, door unlocking)
            if (e instanceof BeforeWalkedOn) {
                BeforeWalkedOn b = (BeforeWalkedOn) e;
                b.walkedOn(dungeon, this);
            }
        }
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (!e.isWalkable()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return double
     */
    @Override
    public double calculateDamage(Fightable enemy) {
        double damage = getAttackDamage();
        // One shot enemy if invincible. Weapon durability is not lowered when
        // invincible.
        if (getBuffs(Invincible.class) != null) {
            return getHealth() * 1000;
        }
        // If fighting boss, check for anduril first and use if so
        if (enemy instanceof Boss) {
            for (InventoryItem item : getInventory()) {
                if (item instanceof AndurilItem) {
                    AndurilItem a = (AndurilItem) item;
                    damage = a.calculateBossDamage(this, damage);
                    return getHealth() * damage;
                }
            }
        }
        // Either fighting boss without anduril, or fighting non-boss
        for (InventoryItem item : getInventory()) {
            // get first weapon in inventory
            if (item instanceof Weapons) {
                Weapons weapon = (Weapons) item;
                // increase damage
                damage = weapon.calculateDamage(this, damage);
                break;
            }
        }
        return getHealth() * damage;
    }

    /**
     * @param dungeon
     * @param damage
     */
    @Override
    public void takeDamage(Dungeon dungeon, double damage) {
        // No damage taken when invincible. Equipment durability not lowered.
        if (getBuffs(Invincible.class) != null) {
            return;
        }
        boolean armourChecked = false;
        boolean shieldChecked = false;
        Armours armour = null;
        Shields shield = null;
        for (InventoryItem item : getInventory()) {
            // get first armour
            if (item instanceof Armours && !armourChecked) {
                armour = (Armours) item;
                armourChecked = true;
            }
            // get first shield
            if (item instanceof Shields && !shieldChecked) {
                shield = (Shields) item;
                shieldChecked = true;
            }
            if (armourChecked && shieldChecked)
                break;
        }
        // reduce damage if character has armour
        if (armour != null) {
            damage = armour.calculateDamage(this, damage);
        }
        // reduce damage if character has shield
        if (shield != null) {
            damage = shield.calculateDamage(this, damage);
        }
        // lower health
        setHealth(getHealth() - (damage / 10));

        // if character is killed
        if (isKilled()) {
            InventoryItem i = getInventoryItem(TheOneRingItem.class);
            // character revives to full hp if has one ring
            if (i != null) {
                removeInventory(i);
                setHealth(getMaxHealth());
            } else {
                // character dies
                dungeon.getEntities().remove(this);
            }
        }
    }

    /**
     * @param dungeon
     * @param walker
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Enemy) {
            Battle.battle(this, (Enemy) walker, dungeon);
        }
        return;
    }

    /**
     * @return Position
     */
    public Position getPrevPosition() {
        return prevPosition;
    }

    /**
     * @param prevPosition
     */
    public void setPrevPosition(Position prevPosition) {
        this.prevPosition = prevPosition;
    }

    /**
     * @return Fightable
     */
    public Fightable getInBattleWith() {
        return inBattleWith;
    }

    /**
     * @param inBattleWith
     */
    public void setInBattleWith(Fightable inBattleWith) {
        this.inBattleWith = inBattleWith;
    }
}
