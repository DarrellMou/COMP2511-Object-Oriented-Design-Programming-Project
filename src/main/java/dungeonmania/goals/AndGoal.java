package dungeonmania.goals;

public class AndGoal implements GoalNode {

    private GoalNode b1;
    private GoalNode b2;

    public AndGoal(GoalNode b1, GoalNode b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    @Override
    public Boolean evaluate() {
        return b1.evaluate() && b2.evaluate();
    }

    public String toString() {

        return "( " + b1.toString() + " AND " + b2.toString() + ")";
    }

}
