package dungeonmania.Buffs;

import java.util.List;

import Entities.movingEntities.Character;

public class Invisible implements Buffs {
    public int endTick;
    public int duration = 10;

    public Invisible(int tick) {
        endTick = tick + duration;
    }

    @Override
    public void durationEnd(int tick, List<Buffs> removeBuffs) {
        if (tick == this.endTick) {
            removeBuffs.add(this);
        }
    }
}
