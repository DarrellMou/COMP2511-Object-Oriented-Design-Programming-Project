package dungeonmania.Buffs;

import java.util.List;

public abstract class Buffs {
    public int endTick;

    public Buffs(int endTick) {
        this.endTick = endTick;
    }

    public void durationEnd(int tick, List<Buffs> removeBuffs) {
        // check if buff duration has expired
        if (tick == this.endTick) {
            removeBuffs.add(this);
        }
    }
}
