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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import Entities.Entities;
import Entities.Item;

public class DungeonManiaController {
    private int numCreatedDungeons;
    private static Map<String, Entities> entities; // change to list of entities
    // private ArrayList<Entities> entities;

    private Dungeon dungeon;
    private Character character;
    private EntitiesFactory entitiesFactory;

    public DungeonManiaController() {
        numCreatedDungeons = 0;
        entities = new HashMap<String, Entities>();
        // entities = new ArrayList<>();
        dungeon = new Dungeon(getDungeonId(), "", "", ""); // TODO fix this
        entitiesFactory = new EntitiesFactory(); // instantiating this to grab
        // my inventory
        character = (Character) entitiesFactory.createEntities("player", new Position(0, 0)); // TODO: Fix this - only

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
     * @return String
     */
    public static Map<String, Entities> getEntities() {
        return entities;
    }

    /**
     * @param entities
     */
    public static void setEntities(Map<String, Entities> entities) {
        DungeonManiaController.entities = entities;
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
                    new FileReader("src/test/resources/dungeons/" + dungeonName + ".json"));
            Data data = new Gson().fromJson(br, Data.class);
            if (data.getGoalCondition().getGoal().equals("AND")) { //

                // Need to see how to implement two goals in a string
            } else {
                dungeon.setGoals(data.getGoalCondition().getGoal());
            }
            // Set the goals given by the map

            for (DataEntities entity : data.getEntities()) {

                Entities newEntity = entitiesFactory.createEntities(entity.getType(),
                        new Position(entity.getX(), entity.getY()));
                // This may change ...
                ArrayList<Entities> res = dungeon.getEntities();
                res.add(newEntity);
                dungeon.setEntities(res);

                EntityResponse item = new EntityResponse(newEntity.getId(), newEntity.getType(),
                        newEntity.getPosition(), newEntity.isInteractable());
                entitiesResponses.add(item);

                // Need to somehow separate items from entities

                // Add the items to List<items>

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
        List<EntityResponse> entities = new ArrayList<>();
        List<ItemResponse> inventory = new ArrayList<>();
        List<String> buildables = new ArrayList<>();

        for (Entities entitiy : dungeon.getEntities()) {
            if (entitiy != null) { // something is breaking sometin is null - temp fix
                entities.add(new EntityResponse(entitiy.getId(), entitiy.getType(), entitiy.getPosition(),
                        entitiy.isInteractable()));
            } else {
                System.out.println(entitiy);
            }

        }

        // for (Map.Entry<Item, Integer> entry : character.getInventory().entrySet()) {
        // for (int i = 0; i < entry.getValue(); i++) {
        // inventory.add(new ItemResponse(entry.getKey().getId(),
        // entry.getKey().getType()));

        // }

        // }

        for (String builds : buildables) {
            buildables.add(builds);
        }
        DungeonResponse dg = new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), entities, inventory,
                buildables, dungeon.getGoals());

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
        ArrayList<Entities> entities = new ArrayList<>();
        ArrayList<Item> inventory = new ArrayList<>();
        ArrayList<String> buildables = new ArrayList<>();

        for (EntityResponse entitiy : dg.getEntities()) {
            Entities newEntity = entitiesFactory.createEntities(entitiy.getId(), entitiy.getPosition());
            entities.add(newEntity);
        }

        for (ItemResponse item : dg.getInventory()) {
            // inventory.add(new Item(item.getId(), item.getType())); //todo: create factory
            // method to spawn these items
        }

        for (String builds : dg.getBuildables()) {
            buildables.add(builds);
        }
        dungeon.setDungeonId(dg.getDungeonId());
        dungeon.setDungeonName(dg.getDungeonName());
        dungeon.setEntities(entities);
        dungeon.setInventory(inventory);
        dungeon.setBuildables(buildables);
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
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse tick(String itemUsed, Direction movementDirection)
            throws IllegalArgumentException, InvalidActionException {
            /**
             * check movable then move char
             * if char on entity -> pickup/interact
             * if entity on character -> fight
             * check movable then move entities
             * if entity on character -> fight (if haven't fought yet) 
             */
        return null;
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
}