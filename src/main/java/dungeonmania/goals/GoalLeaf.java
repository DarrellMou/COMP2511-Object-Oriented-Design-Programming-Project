package dungeonmania.goals;

public class GoalLeaf implements GoalNode {

    private Boolean b1;
    private String goal;

    public GoalLeaf(Boolean b1, String goal) {
        this.b1 = b1;
        this.goal = goal;
    }

    /**
     * @return Boolean
     */
    @Override
    public Boolean evaluate() {
        return this.b1;
    }

    /**
     * @return String
     */
    public String toString() {
        return ":" + goal.toString();
    }

}
