package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;
import Entities.movingEntities.Spider;
import Entities.movingEntities.ZombieToast;
import Entities.staticEntities.ZombieToastSpawner;
import Items.InventoryItem;
import app.data.Data;
import app.data.DataSubgoal;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
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

    private Random random;
    private Character character;
    private List<InventoryItem> inventory;

    // Map<String, EntityResponse> entitiesResponse = new ArrayList<>();
    // Map<ItemResponse> inventory = new ArrayList<>();
    // List<String> buildables = new ArrayList<>();

    public Dungeon(String dungeonId, Random random) {
        this.dungeonId = dungeonId;
        this.dungeonName = "";
        this.entities = new ArrayList<Entities>();
        this.buildables = new ArrayList<String>();
        this.goals = goals;
        this.gameMode = gameMode;
        ticksCounter = 0;
        this.width = 0;
        this.height = 0;
        this.random = random;
        this.inventory = new ArrayList<InventoryItem>();
        this.character = getCharacter();
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Character getCharacter() {
        for (Entities e : getEntities()) {
            if (e instanceof Character) {
                return (Character) e;
            }
        }
        return null;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getDungeonId() {
        return this.dungeonId;
    }

    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }

    public String getDungeonName() {
        return this.dungeonName;
    }

    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    public ArrayList<Entities> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entities> entities) {
        this.entities = entities;
    }

    public void addEntities(Entities entity) {
        this.entities.add(entity);

    }

    public void removeEntities(Entities entity) {
        this.entities.remove(entity);
    }

    public ArrayList<String> getBuildables() {
        return this.buildables;
    }

    public void setBuildables(ArrayList<String> buildables) {
        this.buildables = buildables;
    }

    public void addBuildables(String buildable) {
        this.buildables.add(buildable);
    }

    public String getGoals() {
        return this.goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getGameMode() {
        return this.gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * @return int
     */
    public int getTicksCounter() {
        return this.ticksCounter;
    }

    /** 
     * 
     */
    public void incrementTicks() {
        this.ticksCounter++;
    }

    public void setTicksCounter(int ticksCounter) {
        this.ticksCounter = ticksCounter;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAllGoals(Data data) {
        if (data.getGoalCondition().getGoal().equals("AND")) {
            String goal = "";
            List<DataSubgoal> subgoals = data.getGoalCondition().getSubgoals();
            for (int i = 0; i < subgoals.size() - 1; i++) {
                // If it is the last item dont append AND to it
                goal += subgoals.get(i).getGoal() + " AND ";
            }
            goal += subgoals.get(subgoals.size() - 1).getGoal();

            this.setGoals(goal);

            // Need to see how to implement two goals in a string
        } else {
            this.setGoals(data.getGoalCondition().getGoal());
        }
    }

    /**
     * <<<<<<< HEAD Gets position and returns the entities matching the x and y
     * coordinate (combines all layers)
     * 
     * ======= Gets position and returns the entities matching the x and y
     * coordinate (combines all layers) >>>>>>> master
     * 
     * @param position
     * @return Entities
     */
    public List<Entities> getEntitiesOnTile(Position position) {
        List<Entities> entitiesList = new ArrayList<>();
        entitiesList = getEntities().stream().filter(e -> e.getPosition().getX() == position.getX())
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
        // Checks for valid argument
        // if (itemUsedId == null) {
        // throw new IllegalArgumentException("itemUsedId provided is null");
        // }

        // if (itemUsedId.equals("")) {
        // throw new IllegalArgumentException("itemUsedId provided is an empty string");
        // }

        incrementTicks(); // This increments the number of ticks in this dungeon

        // Character character = getCharacter();
        if (itemUsedId != null && !itemUsedId.equals("")) {
            InventoryItem item = null;
            for (InventoryItem currItem : getCharacter().getInventory()) {
                if (currItem.getId().equals(itemUsedId)) {
                    item = currItem;
                }
            }

            if (item.equals(null)) {
                throw new InvalidActionException(String.format("Character does not have %s in inventory", itemUsedId));
            }

            // Move this function somewehre else
            List<String> legalItems = new ArrayList<>();
            legalItems.add("bomb");
            legalItems.add("health_potion");
            legalItems.add("invincibility_potion");
            legalItems.add("invisibility_potion");
            if (!legalItems.contains((item.getType()))) {
                throw new IllegalArgumentException("itemUsedId provided does not correspond to a bomb or potion");
            }

            // Consumes item, needs to be implemented
            // item.consumeItem();
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

        // Mercenary
        for (Entities e : getEntities()) {
            if (e instanceof Mercenary) {
                Mercenary m = (Mercenary) e;
                m.makeMovement(this);
            }
        }

        // Zombie Toast
        for (Entities e : getEntities()) {
            if (e instanceof ZombieToast) {
                ZombieToast z = (ZombieToast) e;
                z.makeMovement(this);
            }
        }

        // Spider
        for (Entities e : getEntities()) {
            if (e instanceof Spider) {
                Spider s = (Spider) e;
                s.makeMovement(this);
            }
        }
        // List<Entities> newPositionEntities = dungeon.getEntitiesOnTile(newPosition);
        // for (Entities newPositionEntity : newPositionEntities) {
        // // Boulder movement
        // if (newPositionEntity instanceof Boulder) {
        // Boulder b = (Boulder) newPositionEntity;
        // Position newBoulderPosition = b.getPosition().translateBy(movementDirection);
        // if (b.checkMovable(newBoulderPosition, dungeon)) {
        // b.setPosition(newBoulderPosition);
        // }
        // }
        // if (dungeon.getCharacter().checkMovable(newPosition, getEntities())) {
        // Entities entity = getEntityFromPosition(newPosition);
        // if (entity instanceof Triggerable) {
        // // something happens when you try to walk onto it
        // Triggerable triggerable = (Triggerable) entity;
        // triggerable.trigger(getDungeon(), dungeon.getCharacter());
        // } else if (entity instanceof CollectableEntity) {
        // CollectableEntity collectable = (CollectableEntity) entity;
        // collectable.pickup(dungeon, dungeon.getCharacter());
        // dungeon.getCharacter().checkForBuildables(dungeon);
        // }
        // dungeon.getCharacter().setPosition(newPosition);
        // }
        // }

        spawnEnemies(getGameMode(), getHeight(), getWidth()); // Spawn Enemies
        // for (Entities entity : dungeon.getEntities()) {
        // if (entity instanceof SpawningEntities) {
        // SpawningEntities spawningEntities = (SpawningEntities) entity;
        // spawningEntities.makeMovement(spawningEntities.getSpawnPosition(), dungeon);
        // }
        // }

        // Temporary, store responses and change necessary responses only
        List<EntityResponse> entitiesResponses = new ArrayList<>();
        List<ItemResponse> inventoryResponses = new ArrayList<>();
        List<String> buildablesResponses = new ArrayList<>();

        for (Entities entity : getEntities()) {
            entitiesResponses.add(new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(),
                    entity.isInteractable()));
        }

        for (InventoryItem inventoryItem : getCharacter().getInventory()) {
            inventoryResponses.add(new ItemResponse(inventoryItem.getId(), inventoryItem.getType()));
        }

        for (String builds : getBuildables()) {
            buildablesResponses.add(builds);
        }

        return new DungeonResponse(getDungeonId(), getDungeonName(), entitiesResponses, inventoryResponses,
                buildablesResponses, getGoals());
    }

    public void spawnEnemies(String gameMode, int height, int width) {
        // TODO For SpawnableEntites ... spawn (timer + spawn position
        // should be in class)
        if (getTicksCounter() % 10 == 0) {
            Entities spider = EntitiesFactory.createEntities("spider",
                    new Position(random.nextInt(width), random.nextInt(height), 2));
            addEntities(spider);
        }

        if (getTicksCounter() % 20 == 0) {
            for (Entities entity : getEntities()) {
                if (entity instanceof ZombieToastSpawner) {
                    ZombieToastSpawner zombieToastSpawner = (ZombieToastSpawner) entity;
                    Entities zombieToast = zombieToastSpawner.spawnZombies();
                    addEntities(zombieToast);

                }
            }
        }

    }
}
