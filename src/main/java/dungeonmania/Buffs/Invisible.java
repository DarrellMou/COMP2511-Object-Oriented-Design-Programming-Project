package dungeonmania.Buffs;

import java.util.List;

import Entities.movingEntities.Character;

public class Invisible extends Buffs {
    public Invisible(int tick) {
        // duration is 10
        super(tick + 10);
    }
}
