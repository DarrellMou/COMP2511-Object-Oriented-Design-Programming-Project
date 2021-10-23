package app.data;

import java.util.Objects;

public class DataSubgoal {
    private String goal;

    public DataSubgoal() {
    }

    public DataSubgoal(String goal) {
        this.goal = goal;
    }

    public String getGoal() {
        return this.goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public DataSubgoal goal(String goal) {
        setGoal(goal);
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
        return Objects.equals(goal, dataSubgoal.goal);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(goal);
    }

    @Override
    public String toString() {
        return "{" + " goal='" + getGoal() + "'" + "}";
    }

}
