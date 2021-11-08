package dungeonmania.goals;

public class GoalLeaf implements GoalNode {

    private Boolean b1;
    private String goal;

    public GoalLeaf(Boolean b1, String goal) {
        this.b1 = b1;
        this.goal = goal;
    }

    @Override
    public Boolean evaluate() {
        return this.b1;
    }

    public String toString() {
        return ":" + goal.toString();
    }

}
