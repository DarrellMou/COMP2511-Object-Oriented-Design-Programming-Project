package dungeonmania;

import java.util.ArrayList;

import Entities.Entities;
import Entities.Items;

public class Dungeon {
    private String dungeonId;
    private String dungeonName;
    private ArrayList<Entities> entities;
    private ArrayList<Items> inventory;
    private ArrayList<String> buildables;
    private String goals;
    private String gameMode;

    public Dungeon(String dungeonId, String dungeonName, String goals, String gameMode) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.entities = new ArrayList<Entities>();
        this.inventory = new ArrayList<Items>();
        this.buildables = new ArrayList<String>();
        this.goals = goals;
        this.gameMode = gameMode;
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
        return this.entities;
    }

    public void setEntities(ArrayList<Entities> entities) {
        this.entities = entities;
    }

    public ArrayList<Items> getInventory() {
        return this.inventory;
    }

    public void setInventory(ArrayList<Items> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<String> getBuildables() {
        return this.buildables;
    }

    public void setBuildables(ArrayList<String> buildables) {
        this.buildables = buildables;
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

    public Dungeon dungeonId(String dungeonId) {
        setDungeonId(dungeonId);
        return this;
    }

    public Dungeon dungeonName(String dungeonName) {
        setDungeonName(dungeonName);
        return this;
    }

    public Dungeon entities(ArrayList<Entities> entities) {
        setEntities(entities);
        return this;
    }

    public Dungeon inventory(ArrayList<Items> inventory) {
        setInventory(inventory);
        return this;
    }

    public Dungeon buildables(ArrayList<String> buildables) {
        setBuildables(buildables);
        return this;
    }

    public Dungeon goals(String goals) {
        setGoals(goals);
        return this;
    }

    public Dungeon gameMode(String gameMode) {
        setGameMode(gameMode);
        return this;
    }

}
