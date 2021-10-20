package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import Entities.Entities;
import Entities.Items;

public class DungeonManiaController {
    private int noCreatedDungeons;
    // private ArrayList<Dungeon> dungeons;

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

    public DungeonManiaController() {
        noCreatedDungeons = 0;
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

        Dungeon dungeon = new Dungeon(getDungeonId(), dungeonName, "", gameMode);
        List<EntityResponse> entities = null;
        List<ItemResponse> inventory = null;
        List<String> buildables = null;
        return new DungeonResponse(dungeon.getDungeonId(), dungeonName, entities, inventory, buildables,
                dungeon.getGoals());
    }

    /**
     * @param name
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        // List<EntityResponse> entities = null;
        // List<ItemResponse> inventory = null;
        // List<String> buildables = null;
        // return new DungeonResponse(dungeon.getDungeonId(), dungeonName, entities,
        // inventory, buildables,
        // Gson gson = new Gson();
        // try {
        // // Writing to a file
        // gson.toJson(allGames(), new FileWriter("data.json"));

        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        return null;
    }

    /**
     * @param name
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        return null;
    }

    /**
     * @return List<String>
     */
    public List<String> allGames() {
        return new ArrayList<>();
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