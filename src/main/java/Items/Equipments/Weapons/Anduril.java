package Items.Equipments.Weapons;

public class Anduril extends Weapons {
    private static double DAMAGE_MULTIPLIER = 1.5;
    private static final int DURABILITY = 4;

    public Anduril(String id) {
        super(id, "anduril", DAMAGE_MULTIPLIER, DURABILITY);
    }

}
