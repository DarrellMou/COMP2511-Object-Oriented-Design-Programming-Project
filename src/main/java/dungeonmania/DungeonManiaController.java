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
import Entities.movingEntities.*;
import Entities.movingEntities.Character;

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
    private int noCreatedDungeons;
    private static Map<String, Entities> entities;
    private Dungeon dungeon;
    private Character character;

    public DungeonManiaController() {
        noCreatedDungeons = 0;
        entities = new HashMap<String, Entities>();
        dungeon = new Dungeon(getDungeonId(), "", "", ""); // fix this
        character = new Character("character", "moving", new Position(0, 0), true, 100); // TODO: Fix this - only
                                                                                         // instantiating this to grab
                                                                                         // my inventory
    }

    /**
     * @return int
     */
    public int getNoCreatedDungeons() {
        return this.noCreatedDungeons;
    }

    /**
     * @param noCreatedDungeons
     */
    public void setNoCreatedDungeons(int noCreatedDungeons) {
        this.noCreatedDungeons = noCreatedDungeons;
    }

    /**
     * @return String
     */
    public static Map<String, Entities> getEntities() {
        return entities;
    }

    public static void setEntities(Map<String, Entities> entities) {
        DungeonManiaController.entities = entities;
    }

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
        int currentDungeonNo = getNoCreatedDungeons();
        setNoCreatedDungeons(noCreatedDungeons + 1); // Increment the next id of dungeon
        return "dungeon" + String.valueOf(currentDungeonNo);
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
        List<EntityResponse> entities = new ArrayList<>();
        List<ItemResponse> inventory = new ArrayList<>();
        List<String> buildables = new ArrayList<>();
        return new DungeonResponse(dungeon.getDungeonId(), dungeonName, entities, inventory, buildables,
                dungeon.getGoals());
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
            entities.add(new EntityResponse(entitiy.getId(), entitiy.getType(), entitiy.getPosition(),
                    entitiy.isInteractable()));
        }

        for (Map.Entry<Item, Integer> entry : character.getInventory().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                inventory.add(new ItemResponse(entry.getKey().getId(), entry.getKey().getType()));

            }

        }

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
            entities.add(
                    new Entities(entitiy.getId(), entitiy.getType(), entitiy.getPosition(), entitiy.isInteractable()));
        }

        for (ItemResponse item : dg.getInventory()) {
            inventory.add(new Item(item.getId(), item.getType()));
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
        return null;
    }

    /**
     * @param entityId
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
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