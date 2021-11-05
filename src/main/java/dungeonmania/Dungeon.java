package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.Interactable;
import Entities.movingEntities.BribedMercenary;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;
import Entities.movingEntities.Spider;
import Entities.movingEntities.ZombieToast;
import Entities.staticEntities.Exit;
import Entities.staticEntities.FloorSwitch;
import Entities.staticEntities.ZombieToastSpawner;
import Items.InventoryItem;
import Items.TheOneRingItem;
import Items.ConsumableItem.Consumables;
import data.Data;
import data.DataSubgoal;
import dungeonmania.Buffs.Buffs;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.gamemodes.Hard;
import dungeonmania.gamemodes.Peaceful;
import dungeonmania.gamemodes.Standard;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Battle;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private String dungeonId;
    private String dungeonName;
    private ArrayList<Entities> entities;
    private ArrayList<String> buildables;
    private String goals;
    private String gameMode;
    private int ticksCounter;
    private int width;
    private int height;
    private int spawnRate;

    private Random random;

    // Map<String, EntityResponse> entitiesResponse = new ArrayList<>();
    // Map<ItemResponse> inventory = new ArrayList<>();
    // List<String> buildables = new ArrayList<>();

    public Dungeon(String dungeonId, Random random) {
        this.dungeonId = dungeonId;
        this.dungeonName = "";
        this.entities = new ArrayList<Entities>();
        this.buildables = new ArrayList<String>();
        this.goals = "";
        this.gameMode = "";
        ticksCounter = 0;
        this.width = 0;
        this.height = 0;
        this.random = random;
    }

    /**
     * @return Random
     */
    public Random getRandom() {
        return random;
    }

    /**
     * @return Character
     */
    public Character getCharacter() {
        for (Entities e : getEntities()) {
            if (e instanceof Character) {
                return (Character) e;
            }
        }
        return null;
    }

    /**
     * @return String
     */
    public String getDungeonId() {
        return this.dungeonId;
    }

    /**
     * @param dungeonId
     */
    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }

    /**
     * @return String
     */
    public String getDungeonName() {
        return this.dungeonName;
    }

    /**
     * @param dungeonName
     */
    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    /**
     * @return ArrayList<Entities>
     */
    public ArrayList<Entities> getEntities() {
        return entities;
    }

    /**
     * @param entities
     */
    public void setEntities(ArrayList<Entities> entities) {
        this.entities = entities;
    }

    /**
     * @param entity
     */
    public void addEntities(Entities entity) {
        this.entities.add(entity);

    }

    /**
     * @param entity
     */
    public void removeEntities(Entities entity) {
        this.entities.remove(entity);
    }

    /**
     * @return ArrayList<String>
     */
    public ArrayList<String> getBuildables() {
        return this.buildables;
    }

    /**
     * @param buildables
     */
    public void setBuildables(ArrayList<String> buildables) {
        this.buildables = buildables;
    }

    /**
     * @param buildable
     */
    public void addBuildables(String buildable) {
        this.buildables.add(buildable);
    }

    /**
     * @return String
     */
    // public List<Class<? extends BuildableItems>> getBuildableItems() {
    // return this.buildableItems;
    // }

    // public void setBuildableItems(List<Class<? extends BuildableItems>>
    // buildableItems) {
    // this.buildableItems = buildableItems;
    // }

    // public void addBuildablesItems(Class<? extends BuildableItems>
    // buildableItems) {
    // this.buildableItems.add(buildableItems);
    // }

    public int getSpawnRate() {
        return spawnRate;
    }

    public void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    public String getGoals() {
        return this.goals;
    }

    /**
     * @param goals
     */
    public void setGoals(String goals) {
        this.goals = goals;
    }

    /**
     * @return String
     */
    public String getGameMode() {
        return this.gameMode;
    }

    /**
     * @param gameMode
     */
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
        if (gameMode.equals("Hard")) {
            Hard.setGameMode(this);
        } else if (gameMode.equals("Standard")) {
            Standard.setGameMode(this);
        } else if (gameMode.equals("Peaceful")) {
            Peaceful.setGameMode(this);
        }
    }

    /**
     * @return int
     */
    public int getTicksCounter() {
        return this.ticksCounter;
    }

    public void setTicksCounter(int ticksCounter) {
        this.ticksCounter = ticksCounter;
    }

    /** 
     * 
     */
    public void incrementTicks() {
        this.ticksCounter++;
    }

    /**
     * @return int
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return int
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @param data
     */
    public void setAllGoals(Data data) {
        if (data.getGoalCondition().getGoal().equals("AND") || data.getGoalCondition().getGoal().equals("OR")) {
            String goal = "";
            List<DataSubgoal> subgoals = data.getGoalCondition().getSubgoals();
            for (int i = 0; i < subgoals.size() - 1; i++) {
                // If it is the last item dont append AND to it
                goal += ":" + subgoals.get(i).getGoal() + " " + data.getGoalCondition().getGoal() + " ";
            }
            goal += ":" + subgoals.get(subgoals.size() - 1).getGoal();

            this.setGoals(goal);

            // Need to see how to implement two goals in a string
        } else {
            this.setGoals(data.getGoalCondition().getGoal());
        }
    }

    /**
     * Gets position and returns the entities matching the x and y coordinate
     * (combines all layers)
     * 
     * @param position
     * @return Entities
     */
    public List<Entities> getEntitiesOnTile(Position position) {
        List<Entities> entitiesList = new ArrayList<>();

        entitiesList = getEntities().stream().filter(e -> Objects.nonNull(e))
                .filter(e -> e.getPosition().getX() == position.getX())
                .filter(e -> e.getPosition().getY() == position.getY()).collect(Collectors.toList());

        return entitiesList;
    }

    /**
     * @param itemUsed
     * @param movementDirection
     * @return DungeonResponse after a tick has passed
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse tick(String itemUsedId, Direction movementDirection)
            throws IllegalArgumentException, InvalidActionException {

        incrementTicks(); // This increments the number of ticks in this dungeon

        // Character character = getCharacter();
        if (itemUsedId != null && !itemUsedId.equals("")) {
            InventoryItem item = null;
            for (InventoryItem currItem : getCharacter().getInventory()) {
                if (currItem.getId().equals(itemUsedId)) {
                    item = currItem;
                }
            }

            // Checks for whether itemUsed is in inventory
            if (item == null) {
                throw new InvalidActionException(String.format("Character does not have %s in inventory", itemUsedId));
            }

            // Checks for valid itemUsedId
            if (!(item instanceof Consumables || item instanceof TheOneRingItem || itemUsedId == null)) {
                throw new IllegalArgumentException("itemUsedId provided does not correspond to a bomb or potion");
            }

            Consumables consumable = (Consumables) item;
            consumable.consume(this, getCharacter());
        }

        // Buffs
        List<Buffs> removeBuffs = new ArrayList<Buffs>();
        for (Buffs b : getCharacter().getBuffs()) {
            b.durationEnd(getTicksCounter(), removeBuffs);
        }
        for (Buffs b : removeBuffs) {
            getCharacter().removeBuff(b);
        }

        // Character movement
        /**
         * check movable then move char if char on entity -> pickup/interact if entity
         * on character -> fight check movable then move entities if entity on character
         * -> fight (if haven't fought yet)
         */
        // Process:
        // Use item
        // Move character
        // Move all movableEntities

        // Move character
        // - Calculate character's next move based on given direction
        // - Check if future position is on entity
        // - If so, check what type of entity
        // - If entity is static, behaviour depends
        // - If entity is wall, character position does not update
        // - If entity is exit, you win
        // - If entity is boulder, you push it
        // - If entity is switch, you can move onto it, nothing happens
        // - ..etc.
        // - If entity is moving, fight
        // - If entity is collectable, move entity to inventory
        // - Check if items in inventory can build buildables, if so, append to
        // buildables
        // - Update character movement

        // Move all movableEntities
        // - Calculate movableEntity's next move
        // - Check if future position is on entity
        // - If so, check what type of entity
        // - Update moveableEntity movement

        // Suggestion
        // - Each entity has a function for when character/entity moves onto itself
        // - For now, move character

        /**
         * Movement order: - Character - Mercenary - Zombie Toast - Spider
         */

        // Set character movement direction (needed for boulder movement)
        getCharacter().setMovementDirection(movementDirection);

        // Character movement (based on character's movement direction)
        getCharacter().makeMovement(this);

        // Mobs List
        List<Mercenary> mList = new ArrayList<Mercenary>();
        List<ZombieToast> zList = new ArrayList<ZombieToast>();
        List<Spider> sList = new ArrayList<Spider>();
        List<BribedMercenary> bList = new ArrayList<BribedMercenary>();

        for (Entities e : getEntities()) {
            if (e instanceof Mercenary) {
                Mercenary m = (Mercenary) e;
                mList.add(m);
            } else if (e instanceof ZombieToast) {
                ZombieToast z = (ZombieToast) e;
                zList.add(z);
            } else if (e instanceof Spider) {
                Spider s = (Spider) e;
                sList.add(s);
            } else if (e instanceof BribedMercenary) {
                BribedMercenary b = (BribedMercenary) e;
                bList.add(b);
            }
        }

        // Mercenary
        for (Mercenary m : mList) {
            if (getCharacter() == null)
                return newDungeonResponse();
            m.makeMovement(this);
        }
        // Zombie Toast
        for (ZombieToast z : zList) {
            if (getCharacter() == null)
                return newDungeonResponse();
            z.makeMovement(this);
        }
        // Spider
        for (Spider s : sList) {
            if (getCharacter() == null)
                return newDungeonResponse();
            s.makeMovement(this);
        }
        // Spider
        for (BribedMercenary b : bList) {
            if (getCharacter() == null)
                return newDungeonResponse();
            b.makeMovement(this);
        }

        if (getCharacter() == null)
            return newDungeonResponse();

        spawnEnemies(getGameMode(), getHeight(), getWidth()); // Spawn Enemies
        if (hasCompletedGoals()) {
            gameCompleted();
        }

        // clear list that stores who character have fought in the current tick
        Battle.clearBattleEnemies();

        // Temporary, store responses and change necessary responses only
        return newDungeonResponse();
    }

    /**
     * @param entityId
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Interactable i = null;
        // get entity if interactible
        for (Entities e : getEntities()) {
            if (e.getId().equals(entityId)) {
                if (e instanceof Interactable) {
                    i = (Interactable) e;
                }
                break;
            }
        }
        // if entity isn't interactable, throw error
        if (i == null) {
            throw new IllegalArgumentException("Object is not interactable!!");
        }
        // interact with interactable
        i.interact(this);
        return newDungeonResponse();
    }

    /**
     * @return DungeonResponse
     */
    public DungeonResponse newDungeonResponse() {
        List<EntityResponse> entitiesResponses = new ArrayList<>();
        List<ItemResponse> inventoryResponses = new ArrayList<>();
        List<String> buildablesResponses = new ArrayList<>();

        for (Entities entity : getEntities()) {
            if (entity != null) {
                entitiesResponses.add(new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(),
                        entity.isInteractable()));
            }

        }

        if (getCharacter() != null) {
            for (InventoryItem inventoryItem : getCharacter().getInventory()) {
                inventoryResponses.add(new ItemResponse(inventoryItem.getId(), inventoryItem.getType()));
            }
        }

        for (String builds : getBuildables()) {
            buildablesResponses.add(builds);
        }

        return new DungeonResponse(getDungeonId(), getDungeonName(), entitiesResponses, inventoryResponses,
                buildablesResponses, getGoals());
    }

    /**
     * @param gameMode
     * @param height
     * @param width
     */
    public void spawnEnemies(String gameMode, int height, int width) {
        if (getTicksCounter() == 0 || spawnRate == 0) {
            return;
        }

        if (getTicksCounter() % 25 == 0) {
            Entities spider = EntitiesFactory.createEntities("spider",
                    new Position(random.nextInt(width), random.nextInt(height), 2));
            addEntities(spider);
        }

        if (getTicksCounter() % spawnRate == 0) {
            for (Entities entity : getEntities()) {
                if (entity instanceof ZombieToastSpawner) {
                    ZombieToastSpawner zombieToastSpawner = (ZombieToastSpawner) entity;
                    Entities zombieToast = zombieToastSpawner.spawnZombies(this);
                    // zombieToast = null if no cardianlly adjacent open square
                    if (zombieToast != null)
                        addEntities(zombieToast);
                    break;
                }
            }
        }
    }

    public Boolean hasCompletedGoals() {

        List<String> inventoryTypes = getCharacter().getInventory().stream().map((item) -> item.getType())
                .collect(Collectors.toList());
        List<String> goalsList = new ArrayList<>();
        for (String goals : getGoals().split(" ")) {
            if (goals.contains(":")) {
                goalsList.add(goals.split(":")[1]);
            }

        }
        if (goalsList.isEmpty()) {
            goalsList.add(getGoals());
        }

        for (String goal : goalsList) {
            if (getGoals().contains("OR")) { // Check if the goal is OR or AND
                if (checkIndividualGoals(goal, inventoryTypes, goalsList))
                    return true;

            } else if (getGoals().contains("AND")) {
                if (!checkIndividualGoals(goal, inventoryTypes, goalsList)) {
                    return false;
                }

            } else {
                if (checkIndividualGoals(goal, inventoryTypes, goalsList))
                    return true;
            }

        }
        if (getGoals().contains("AND")) {
            return true;
        }

        return false;
    }

    /**
     * @param goal
     * @param inventoryTypes
     * @param goalsList
     * @return Boolean
     */
    public Boolean checkIndividualGoals(String goal, List<String> inventoryTypes, List<String> goalsList) {
        switch (goal.toLowerCase()) {
        case "exit":
            List<Entities> entitiesAtPosition = getEntitiesOnTile(getCharacter().getPosition());
            for (Entities entity : entitiesAtPosition) {
                if (entity instanceof Exit) {
                    return true;
                }
            }
            return false;
        case "enemies":
            List<Entities> zombies = getEntities().stream().filter(e -> Objects.nonNull(e))
                    .filter((entity) -> entity.getType().equals("zombie_toast")).collect(Collectors.toList());
            List<Entities> mercenary = getEntities().stream().filter(e -> Objects.nonNull(e))
                    .filter((entity) -> entity.getType().equals("mercenary")).collect(Collectors.toList());
            List<Entities> spiders = getEntities().stream().filter(e -> Objects.nonNull(e))
                    .filter((entity) -> entity.getType().equals("spider")).collect(Collectors.toList());
            List<Entities> zombiespawners = getEntities().stream().filter(e -> Objects.nonNull(e))
                    .filter((entity) -> entity.getType().equals("zombie_toast_spawner")).collect(Collectors.toList());

            if (zombies.isEmpty() && mercenary.isEmpty() && spiders.isEmpty() && zombiespawners.isEmpty()) {
                return true;
            }
            return false;
        case "boulders":
            // Check all switches have a boulder on it
            for (Entities entity : getEntities()) {
                if (entity instanceof FloorSwitch) {
                    List<Entities> entityAtPosition = getEntitiesOnTile(entity.getPosition());
                    List<Entities> bouldersOnSwitch = entityAtPosition.stream().filter(e -> Objects.nonNull(e))
                            .filter((entityOnTile) -> entityOnTile.getType().equals("boulder"))
                            .collect(Collectors.toList());
                    if (bouldersOnSwitch.isEmpty()) {
                        return false;
                    }
                }
            }
            return true;
        case "treasure":
            if (inventoryTypes.contains("treasure")) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void gameCompleted() {
        // If you stop returning any goals (i.e. empty string) it'll say the game has
        // been completed
        setGoals("");
    }
}
