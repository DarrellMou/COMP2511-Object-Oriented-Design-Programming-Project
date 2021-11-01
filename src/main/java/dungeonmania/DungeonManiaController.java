package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.*;
import Entities.movingEntities.Character;
import Entities.staticEntities.Boulder;
import Entities.staticEntities.Triggerable;
import Items.InventoryItem;
import data.Data;
import data.DataEntities;
import Entities.staticEntities.ZombieToastSpawner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import Entities.collectableEntities.CollectableEntity;

public class DungeonManiaController {
    private int numCreatedDungeons;
    private Dungeon dungeon;
    private Random random;

    public DungeonManiaController() {
        numCreatedDungeons = 0;
        random = new Random(System.currentTimeMillis()); // Seed is the time
        dungeon = new Dungeon(getDungeonId(), random);
    }

    public DungeonManiaController(Random random) {
        numCreatedDungeons = 0;
        this.random = random; // Used in testing
        dungeon = new Dungeon(getDungeonId(), random);
    }

    /**
     * @return Random
     */
    public Random getRandom() {
        return this.random;
    }

    /**
     * @param random
     */
    public void setRandom(Random random) {
        this.random = random;
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

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * @param dungeon
     */
    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
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
        dungeon.setTicksCounter(0);
        dungeon.setEntities(new ArrayList<Entities>()); // Clear out anything from the previous game
        dungeon.setBuildables(new ArrayList<String>());

        List<EntityResponse> entitiesResponses = new ArrayList<>();
        List<ItemResponse> inventoryResponses = new ArrayList<>();
        List<String> buildableResponses = new ArrayList<>();

        newGameCreateMap(entitiesResponses, dungeonName, gameMode);

        return new DungeonResponse(getDungeonId(), dungeonName, entitiesResponses, inventoryResponses,
                buildableResponses, dungeon.getGoals());
    }

    /**
     * @param entitiesResponses
     * @param dungeonName
     */
    public void newGameCreateMap(List<EntityResponse> entitiesResponses, String dungeonName, String gameMode) {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("src/main/resources/dungeons/" + dungeonName + ".json"));
            Data data = new Gson().fromJson(br, Data.class);
            if (data.getGoalCondition() != null) {
                dungeon.setAllGoals(data); // Set the goals given by the map only if there is a goal condition

            } else {
                dungeon.setGoals("");
            }

            int height = data.getHeight(); // Sets the height and width dimensions of the dungeons
            int width = data.getWidth();

            dungeon.setHeight(height);
            dungeon.setWidth(width);

            for (DataEntities entity : data.getEntities()) {

                Entities newEntity = EntitiesFactory.creatingEntitiesFactory(entity);
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
     * 
     */
    public DungeonResponse saveGame(String name) {
        List<EntityResponse> entitiesResponses = new ArrayList<>();
        List<ItemResponse> inventoryResponses = new ArrayList<>();
        List<String> buildablesResponses = new ArrayList<>();

        for (Entities entity : dungeon.getEntities()) {
            entitiesResponses.add(new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(),
                    entity.isInteractable()));

        }

        for (InventoryItem inventoryItem : getCharacter().getInventory()) {
            inventoryResponses.add(new ItemResponse(inventoryItem.getId(), inventoryItem.getType()));

        }

        for (String builds : dungeon.getBuildables()) {
            buildablesResponses.add(builds);
        }
        DungeonResponse dg = new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), entitiesResponses,
                inventoryResponses, buildablesResponses, dungeon.getGoals());

        Gson gson = new Gson();
        try {

            String data = readFile("data.json");
            List<Map<String, DungeonResponse>> dungeonList = gson.fromJson(data,
                    new TypeToken<List<Map<String, DungeonResponse>>>() {

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
            newEntities.add(EntitiesFactory.creatingEntitiesFactory(entity));
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
        dungeon.getCharacter().setInventory(newInventory);
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
        return dungeon.tick(itemUsedId, movementDirection);
    }

    /**
     * @param entityId
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return dungeon.interact(entityId);
    }

    /**
     * @param buildable
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (getCharacter().build(buildable)) {
            getCharacter().checkForBuildables(null, dungeon);
        }

        // Temporary, store responses and change necessary responses only
        List<EntityResponse> entitiesResponses = new ArrayList<>();
        List<ItemResponse> inventoryResponses = new ArrayList<>();
        List<String> buildablesResponses = new ArrayList<>();

        for (Entities entity : dungeon.getEntities()) {
            entitiesResponses.add(new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(),
                    entity.isInteractable()));

        }

        for (InventoryItem inventoryItem : getCharacter().getInventory()) {
            inventoryResponses.add(new ItemResponse(inventoryItem.getId(), inventoryItem.getType()));

        }

        for (String builds : dungeon.getBuildables()) {
            buildablesResponses.add(builds);
        }

        return new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), entitiesResponses,
                inventoryResponses, buildablesResponses, dungeon.getGoals());
    }

    /**
     * @return Character
     */
    private Character getCharacter() {
        return dungeon.getCharacter();
    }

}