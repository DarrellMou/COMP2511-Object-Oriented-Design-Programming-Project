package dungeonmania.goals;

public class BooleanGoalEvaluator {
    GoalNode expression;

    public BooleanGoalEvaluator(GoalNode expression) {
        this.expression = expression;

    }

    /**
     * @param expression
     * @return boolean
     */
    public static boolean evaluate(GoalNode expression) {
        return expression.evaluate();
    }

}
