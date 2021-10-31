package data;

import java.util.List;
import java.util.Objects;

public class DataGoals {
    private String goal;
    private List<DataSubgoal> subgoals;

    public DataGoals() {
    }

    public DataGoals(String goal, List<DataSubgoal> subgoals) {
        this.goal = goal;
        this.subgoals = subgoals;
    }

    public String getGoal() {
        return this.goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public List<DataSubgoal> getSubgoals() {
        return this.subgoals;
    }

    public void setSubgoals(List<DataSubgoal> subgoals) {
        this.subgoals = subgoals;
    }

    public DataGoals goal(String goal) {
        setGoal(goal);
        return this;
    }

    public DataGoals subgoals(List<DataSubgoal> subgoals) {
        setSubgoals(subgoals);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DataGoals)) {
            return false;
        }
        DataGoals dataGoals = (DataGoals) o;
        return Objects.equals(goal, dataGoals.goal) && Objects.equals(subgoals, dataGoals.subgoals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goal, subgoals);
    }

    @Override
    public String toString() {
        return "{" + " goal='" + getGoal() + "'" + ", subgoals='" + getSubgoals() + "'" + "}";
    }

}
