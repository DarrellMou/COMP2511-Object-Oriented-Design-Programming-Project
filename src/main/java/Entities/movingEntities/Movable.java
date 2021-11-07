package Entities.movingEntities;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public interface Movable {
  /**
   * Returns whether it can move to the position
   * 
   * @param position
   * @param controller
   * @return
   */
  public boolean checkMovable(Position position, Dungeon dungeon);

  /**
   * Does what is expected when an entity tries to move to given position
   * 
   * @param position
   * @param controller
   * @return
   */
  public void walkOn(Position position, Dungeon dungeon);

  /**
   * This generates the movement for the movingEntities, override it in the
   * subclass on how we want to move it
   * 
   * @param position
   * @param controller
   */
  public abstract void makeMovement(Dungeon dungeon);
}
