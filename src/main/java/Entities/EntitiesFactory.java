package Entities;

import java.util.UUID;

import Entities.collectableEntities.consumableEntities.Bomb;
import Entities.collectableEntities.consumableEntities.InvincibilityPotion;
import Entities.collectableEntities.equipments.Sword;
import Entities.collectableEntities.materials.Treasure;
import Entities.staticEntities.Boulder;
import Entities.staticEntities.Door;
import Entities.staticEntities.Exit;
import Entities.staticEntities.FloorSwitch;
import Entities.staticEntities.Wall;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class EntitiesFactory {

    public String getNextId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Create entity of given type at given position
     * 
     * @param type
     * @param position
     * @return
     */
    public Entities createEntities(String type, Position position) {
        Entities newEntity = null;

        // TODO Can someone finissh the rest of this lol
        if (type.equals("wall")) {
            newEntity = new Wall(getNextId(), type, position, false);
        } else if (type.equals("bomb")) {
            newEntity = new Bomb(getNextId(), type, position, false);
        } else if (type.equals("door")) {
            newEntity = new Door(getNextId(), type, position, true); // is door interactable?
        } else if (type.equals("exit")) {
            newEntity = new Exit(getNextId(), type, position, false); // is exit interactable?
        } else if (type.equals("treasure")) {
            newEntity = new Treasure(getNextId(), type, position, true);
        } else if (type.equals("invincibility_potion")) {
            newEntity = new InvincibilityPotion(getNextId(), type, position, true);
        } else if (type.equals("switch")) {
            newEntity = new FloorSwitch(getNextId(), type, position, true);
        } else if (type.equals("player")) {
            newEntity = new Character(getNextId(), type, position, true, 100); // What is character health?
        } else if (type.equals("boulder")) {
            newEntity = new Boulder(getNextId(), type, position, false); // is boulder interctable?
        } else if (type.equals("sword")) {
            newEntity = new Sword(getNextId(), type, position, false); // is sword interctable?
        } else if (type.equals("mercenary")) {
            newEntity = new Mercenary(getNextId(), type, position, true, 100); // What is mecernary health?
        }

        return newEntity;
    }

}
