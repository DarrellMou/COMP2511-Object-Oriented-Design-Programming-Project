package dungeonmania.Buffs;

import java.util.List;

import Entities.movingEntities.Character;

public interface Buffs {
    public void durationEnd(int ticksCounter, List<Buffs> removeBuffs);
}
