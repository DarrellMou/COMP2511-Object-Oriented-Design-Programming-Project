package app.data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Data {
    private int width;
    private int height;
    private List<DataEntities> entities;
    private Map<String, String> goalConditions;

    public Data(int width, int height, List<DataEntities> entities, Map<String, String> goalConditions) {
        this.width = width;
        this.height = height;
        this.entities = entities;
        this.goalConditions = goalConditions;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<DataEntities> getEntities() {
        return this.entities;
    }

    public void setEntities(List<DataEntities> entities) {
        this.entities = entities;
    }

    public Map<String, String> getGoalConditions() {
        return this.goalConditions;
    }

    public void setGoalConditions(Map<String, String> goalConditions) {
        this.goalConditions = goalConditions;
    }

    public Data width(int width) {
        setWidth(width);
        return this;
    }

    public Data height(int height) {
        setHeight(height);
        return this;
    }

    public Data entities(List<DataEntities> entities) {
        setEntities(entities);
        return this;
    }

    public Data goalConditions(Map<String, String> goalConditions) {
        setGoalConditions(goalConditions);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Data)) {
            return false;
        }
        Data data = (Data) o;
        return width == data.width && height == data.height && Objects.equals(entities, data.entities)
                && Objects.equals(goalConditions, data.goalConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, entities, goalConditions);
    }

    @Override
    public String toString() {
        return "{" + " width='" + getWidth() + "'" + ", height='" + getHeight() + "'" + ", entities='" + getEntities()
                + "'" + ", goalConditions='" + getGoalConditions() + "'" + "}";
    }

}
