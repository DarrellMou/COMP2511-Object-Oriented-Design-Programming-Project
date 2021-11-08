package dungeonmania.goals;

public class OrGoal implements GoalNode {

    private GoalNode b1;
    private GoalNode b2;
    private String goal1;
    private String goal2;

    public OrGoal(GoalNode b1, GoalNode b2, String goal1, String goal2) {
        this.b1 = b1;
        this.b2 = b2;
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    @Override
    public Boolean evaluate() {
        return b1.evaluate() || b2.evaluate();
    }

    public String toString() {
        return "( " + b1.toString() + " OR " + b2.toString() + ")";
    }
}
