package dungeonmania.Buffs;

public class Invisible extends Buffs {
    private static final int duration = 10;

    public Invisible(int tick) {
        super(tick + duration);
    }
}
