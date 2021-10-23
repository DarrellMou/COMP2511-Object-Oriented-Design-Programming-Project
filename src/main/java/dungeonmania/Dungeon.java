package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import Entities.Entities;
import Entities.InventoryItem;
import app.data.Data;
import app.data.DataSubgoal;

public class Dungeon {
    private String dungeonId;
    private String dungeonName;
    private ArrayList<Entities> entities;
    private ArrayList<String> buildables;
    private String goals;
    private String gameMode;

    public Dungeon(String dungeonId, String dungeonName, String goals, String gameMode) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.entities = new ArrayList<Entities>();
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

}
