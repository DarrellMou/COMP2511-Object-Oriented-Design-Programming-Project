package dungeonmania.Buffs;

import java.util.List;

public abstract class Buffs {
    public int endTick;

    public Buffs(int endTick) {
        this.endTick = endTick;
    }

    /**
     * This checks that the tick is equal to the duration end of the buff and will
     * remove it
     * 
     * @param tick
     * @param removeBuffs
     */
    public void durationEnd(int tick, List<Buffs> removeBuffs) {
        // check if buff duration has expired
        if (tick == this.endTick) {
            removeBuffs.add(this);
        }
    }
}
