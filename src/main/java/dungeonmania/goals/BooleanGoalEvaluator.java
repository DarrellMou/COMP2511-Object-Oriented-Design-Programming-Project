package dungeonmania.goals;

public class BooleanGoalEvaluator {
    GoalNode expression;

    public BooleanGoalEvaluator(GoalNode expression) {
        this.expression = expression;

    }

    public static boolean evaluate(GoalNode expression) {
        return expression.evaluate();
    }

}
