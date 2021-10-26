package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import org.json.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.*;
import Entities.movingEntities.Character;
import Entities.staticEntities.Wall;
import app.data.Data;
import app.data.DataEntities;
import app.data.DataSubgoal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import Entities.Entities;
import Entities.InventoryItem;

public class DungeonManiaController {
    private int numCreatedDungeons;
    private Dungeon dungeon;
    private EntitiesFactory entitiesFactory;
    private Random random;


    
    public DungeonManiaController() {
        numCreatedDungeons = 0;
        dungeon = new Dungeon(getDungeonId(), "", "", ""); // TODO fix this
        entitiesFactory = new EntitiesFactory(); // instantiating this to grab
        random = new Random(System.currentTimeMillis()); // Seed is the time
        
    }
    
    /**
     * @return int
     */
    public int getNumCreatedDungeons() {
        return this.numCreatedDungeons;
    }
    
    /**
     * @param numCreatedDungeons
     */
    public void setNumCreatedDungeons(int numCreatedDungeons) {
        this.numCreatedDungeons = numCreatedDungeons;
    }
    
    /**
     * 
     * @return Dungeon
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * @return String
     */
    public String getSkin() {
        return "default";
    }

    /**
     * @return String
     */
    public String getLocalisation() {
        return "en_US";
    }

    /**
     * @return List<String>
     */
    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Generates a new dungeonID, given the number of created dungeons, do dungeonId
     * would always be unique e.g. dungeonID = dungeon1, for the first dungeon
     * 
     * @return String
     */
    public String getDungeonId() {
        int currentDungeonNo = getNumCreatedDungeons();
        setNumCreatedDungeons(numCreatedDungeons + 1); // Increment the next id of dungeon
        return "dungeon" + String.valueOf(currentDungeonNo);
    }

      /** 
     * This generates the position that the spider will spawn in
     * 
     * @return Position
     */
    public Position getRandomPosition(int xBound, int yBound) {
        int x = random.nextInt(xBound);
        int y = random.nextInt(yBound);
        return new Position(x, y);

    }

    /**
     * This clears out the json file - all the saved games are removed
     * 
     * @return String
     */
    public void clear() {
        try {
            FileWriter writer = new FileWriter("data.json");
            new Gson().toJson(new JsonArray(), writer);
            writer.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    /**
     * @param dungeonName
     * @param gameMode
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        if (!getGameModes().contains(gameMode)) {
            throw new IllegalArgumentException("Game mode is not a valid game mode");
        }

        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException("dungeon name is not a dungeon that exists");
        }

        dungeon.setDungeonName(dungeonName);
        dungeon.setGameMode(gameMode);
        List<EntityResponse> entitiesResponses = new ArrayList<>();
        List<ItemResponse> inventoryResponses = new ArrayList<>();
        List<String> buildableResponses = new ArrayList<>();

        newGameCreateMap(entitiesResponses, dungeonName);

        return new DungeonResponse(dungeon.getDungeonId(), dungeonName, entitiesResponses, inventoryResponses,
                buildableResponses, dungeon.getGoals());
    }

    public void newGameCreateMap(List<EntityResponse> entitiesResponses, String dungeonName) {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("src/main/resources/dungeons/" + dungeonName + ".json"));
            Data data = new Gson().fromJson(br, Data.class);
            if (data.getGoalCondition() != null) {
                dungeon.setAllGoals(data);  // Set the goals given by the map only if there is a goal condition

            }
           
            for (DataEntities entity : data.getEntities()) {

                Entities newEntity = entitiesFactory.creatingEntitiesFactory(entity);
                dungeon.addEntities(newEntity); // Adding it to the entities of dungeon

                EntityResponse item = new EntityResponse(newEntity.getId(), newEntity.getType(),
                        newEntity.getPosition(), newEntity.isInteractable());
                entitiesResponses.add(item); // Adding it to the responses

                // Need to somehow separate items from entities

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * @param name
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        List<EntityResponse> entitiesResponses = new ArrayList<>();
        List<ItemResponse> inventoryResponses = new ArrayList<>();
        List<String> buildablesResponses = new ArrayList<>();

        for (Entities entitiy : getEntities()) {
                entitiesResponses.add(new EntityResponse(entitiy.getId(), entitiy.getType(), entitiy.getPosition(),
                        entitiy.isInteractable()));

        }

        for (InventoryItem inventoryItem : getCharacter().getInventory()) {
            inventoryResponses.add(new ItemResponse(inventoryItem.getId(),inventoryItem.getType()));

        }

        for (String builds : dungeon.getBuildables()) {
            buildablesResponses.add(builds);
        }
        DungeonResponse dg = new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), entitiesResponses, inventoryResponses,
        buildablesResponses, dungeon.getGoals());

        Gson gson = new Gson();
        try {

            String data = readFile("data.json");
            List<Map<String, DungeonResponse>> dungeonList = gson.fromJson(data, new TypeToken<List<Map<String, DungeonResponse>>>() {
                
                    }.getType());
            if (dungeonList == null) {
                dungeonList = new ArrayList<Map<String, DungeonResponse>>();
            }
            Map<String, DungeonResponse> info = new HashMap<>();
            info.put(name, dg);
            dungeonList.add(info);

            FileWriter writer = new FileWriter("data.json");
            gson.toJson(dungeonList, writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dg;

    }

    /**
     * @param name
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {

        Gson gson = new Gson();
        DungeonResponse dg = null;

        String data = readFile("data.json");

        List<Map<String, DungeonResponse>> dungeonList = gson.fromJson(data,
                new TypeToken<List<Map<String, DungeonResponse>>>() {
                }.getType());

        for (Map<String, DungeonResponse> eachSave : dungeonList) {
            for (Map.Entry<String, DungeonResponse> entry : eachSave.entrySet()) {
                if (entry.getKey().equals(name)) {
                    dg = entry.getValue();
                    break;
                }
            }
        }

        if (dg == null) {
            throw new IllegalArgumentException("Name is not a valid name");
        }
        // Otherwise we load the dungeon
        ArrayList<Entities> newEntities = new ArrayList<>();
        ArrayList<InventoryItem> newInventory = new ArrayList<>();
        ArrayList<String> newBuildables = new ArrayList<>();

        for (EntityResponse entity : dg.getEntities()) {
            newEntities.add(entitiesFactory.creatingEntitiesFactory(entity));
        }
        
        for (ItemResponse item : dg.getInventory()) {
            newInventory.add(new InventoryItem(item.getId(), item.getType()));
        }

        for (String builds : dg.getBuildables()) {
            newBuildables.add(builds);
        }
        dungeon.setDungeonId(dg.getDungeonId());
        dungeon.setDungeonName(dg.getDungeonName());
        dungeon.setEntities(newEntities);
        getCharacter().setInventory(newInventory);
        dungeon.setBuildables(newBuildables);
        dungeon.setGoals(dg.getGoals());


        return dg;

    }

    /**
     * @param fileName
     * @return String
     */
    private String readFile(String fileName) {
        String allLines = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            allLines = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            return allLines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLines;
    }

    /**
     * @return List<String>
     */
    public List<String> allGames() {
        List<String> allGames = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String data = readFile("data.json");

        List<Map<String, DungeonResponse>> dungeonList = gson.fromJson(data,
                new TypeToken<List<Map<String, DungeonResponse>>>() {
                }.getType());

        for (Map<String, DungeonResponse> eachSave : dungeonList) {
            for (String key : eachSave.keySet()) {
                allGames.add(key);
            }
        }

        return allGames;
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
        //     throw new IllegalArgumentException("itemUsedId provided is null");
        // }

        // if (itemUsedId.equals("")) {
        //     throw new IllegalArgumentException("itemUsedId provided is an empty string");
        // }

        Character character = getCharacter();
        if (itemUsedId == null) {
            // throw new IllegalArgumentException("itemUsedId provided is null");
        }
        else {
            InventoryItem item = null;
            for (InventoryItem currItem : character.getInventory()) {
                if (currItem.getId().equals(itemUsedId)) {
                    item = currItem;
                }
            }

            if (item.equals(null)) {
                throw new InvalidActionException(String.format("Character does not have %s in inventory", itemUsedId));
            }

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
             * check movable then move char
             * if char on entity -> pickup/interact
             * if entity on character -> fight
             * check movable then move entities
             * if entity on character -> fight (if haven't fought yet) 
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
        // - Check if items in inventory can build buildables, if so, append to buildables
        // - Update character movement 

        // Move all movableEntities
        // - Calculate movableEntity's next move
        // - Check if future position is on entity
        // - If so, check what type of entity
        // - Update moveableEntity movement 
        
        // Suggestion
        // - Each entity has a function for when character/entity moves onto itself
        // - For now, move character

        Position newPosition = character.getPosition().translateBy(movementDirection);
        character.setPosition(newPosition);

        List<EntityResponse> entities = new ArrayList<>();
        List<ItemResponse> inventory = new ArrayList<>();
        List<String> buildables = new ArrayList<>();

        for (Entities entitiy : getEntities()) {
            if (entitiy != null) { // something is breaking sometin is null - temp fix
                entities.add(new EntityResponse(entitiy.getId(), entitiy.getType(), entitiy.getPosition(),
                        entitiy.isInteractable()));
            } 

        }

        for (InventoryItem inventoryItem : getCharacter().getInventory()) {
            inventory.add(new ItemResponse(inventoryItem.getId(),inventoryItem.getType()));

        }

        for (String builds : buildables) {
            buildables.add(builds);
        }
        return new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), entities, inventory,
                buildables, dungeon.getGoals());
    }

    /**
     * @param entityId
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        // calls trigger in triggerables
        return null;
    }

    /**
     * @param buildable
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public Character getCharacter() {
        System.out.println(getEntities());
        for (Entities entity: getEntities()) {
            if (entity.getType().equals("player")) {
                if (entity instanceof Character) return (Character) entity;
            }
        }
        return null;

    }

    public ArrayList<Entities> getEntities() {
        
        return dungeon.getEntities();

    }

    public Entities getEntityFromPosition(Position position) {
        // TODO what about layer in position?
        for (Entities e : getEntities()) {
            if (e.getPosition().equals(position)) {
                return e;
            }
        }
        return null;
    }
}