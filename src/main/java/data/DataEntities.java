package data;

import java.util.Objects;

public class DataEntities {
    private int x;
    private int y;
    private String type;
    private int key;
    private String colour;


    public DataEntities(int x, int y, String type, int key) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.key = key;
    
    }

    public DataEntities(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }


    public DataEntities(int x, int y, String type, String colour) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.colour = colour;
    
    }



    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataEntities x(int x) {
        setX(x);
        return this;
    }

    public DataEntities y(int y) {
        setY(y);
        return this;
    }

    public DataEntities type(String type) {
        setType(type);
        return this;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

     

    public String getColour() {
        return this.colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DataEntities)) {
            return false;
        }
        DataEntities dataEntities = (DataEntities) o;
        return x == dataEntities.x && y == dataEntities.y && Objects.equals(type, dataEntities.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, type);
    }

    @Override
    public String toString() {
        return "{" + " x='" + getX() + "'" + ", y='" + getY() + "'" + ", type='" + getType() + "'" + "}";
    }

}
