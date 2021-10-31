package data;

import java.util.List;
import java.util.Objects;

public class DataSubgoal {

    private String goal;
    private List<DataSubgoal> subgoals;

    public DataSubgoal() {
    }

    public DataSubgoal(String goal, List<DataSubgoal> subgoals) {
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

    public DataSubgoal goal(String goal) {
        setGoal(goal);
        return this;
    }

    public DataSubgoal subgoals(List<DataSubgoal> subgoals) {
        setSubgoals(subgoals);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DataSubgoal)) {
            return false;
        }
        DataSubgoal dataSubgoal = (DataSubgoal) o;
        return Objects.equals(goal, dataSubgoal.goal) && Objects.equals(subgoals, dataSubgoal.subgoals);
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
