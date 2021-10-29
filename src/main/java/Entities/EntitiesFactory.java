package Entities;

import java.util.UUID;

import Entities.buildableEntities.Bow;
import Entities.buildableEntities.Shield;
import Entities.collectableEntities.consumables.Bomb;
import Entities.collectableEntities.consumables.InvincibilityPotion;
import Entities.collectableEntities.consumables.Key;
import Entities.collectableEntities.equipments.Sword;
import Entities.collectableEntities.materials.Arrow;
import Entities.collectableEntities.materials.Treasure;
import Entities.collectableEntities.materials.Wood;
import Entities.staticEntities.BombActive;
import Entities.staticEntities.Boulder;
import Entities.staticEntities.Door;
import Entities.staticEntities.Exit;
import Entities.staticEntities.FloorSwitch;
import Entities.staticEntities.Portal;
import Entities.staticEntities.Wall;
import Entities.staticEntities.ZombieToastSpawner;
import app.data.DataEntities;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;
import Entities.movingEntities.Spider;
import Entities.movingEntities.ZombieToast;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class EntitiesFactory {

    public static String getNextId() {
        return UUID.randomUUID().toString();
    }

    public static Entities creatingEntitiesFactory(EntityResponse entity) {

        return createEntities(entity.getType(), entity.getPosition());

    }

    public static Entities creatingEntitiesFactory(DataEntities entity) {
        String type = entity.getType();
        if (type.equals("door") || type.equals("key")) {
            return createEntities(entity.getType(), new Position(entity.getX(), entity.getY()), entity.getKey());

        } else if (type.equals("portal")) {
            return createEntities(entity.getType(), new Position(entity.getX(), entity.getY()), entity.getColour());

        } else {
            return createEntities(entity.getType(), new Position(entity.getX(), entity.getY()));

        }
    }

    public static Entities createEntities(String type, Position position, String colour) {

        Entities newEntity = null;

        if (type.equals("portal")) {
            newEntity = new Portal(getNextId(), position, colour);
        }

        return newEntity;
    }

    public static Entities createEntities(String type, Position position, int key) {

        Entities newEntity = null;

        if (type.equals("door")) {
            newEntity = new Door(getNextId(), position, key);
        } else if (type.equals("key")) {
            newEntity = new Key(getNextId(), position, key);
        }

        return newEntity;
    }

    // public Entities createEntities(String type) {

    // Entities newEntity = null;

    // if (type.equals("bow")) {
    // newEntity = new Bow(getNextId(), true); // is bow interactable?
    // } else if (type.equals("shield")) {
    // newEntity = new Shield(getNextId(), false); // is shield interactable?
    // }

    // return newEntity;
    // }
    /**
     * Create entity of given type at given position
     * 
     * @param type
     * @param position
     * @return
     */
    public static Entities createEntities(String type, Position position) {
        Entities newEntity = null;

        // TODO Can someone finissh the rest of this lol
        if (type.equals("wall")) {
            newEntity = new Wall(getNextId(), position);
        } else if (type.equals("bomb")) {
            newEntity = new Bomb(getNextId(), position);
        } else if (type.equals("exit")) {
            newEntity = new Exit(getNextId(), position);
        } else if (type.equals("treasure")) {
            newEntity = new Treasure(getNextId(), position);
        } else if (type.equals("arrow")) {
            newEntity = new Arrow(getNextId(), position);
        } else if (type.equals("wood")) {
            newEntity = new Wood(getNextId(), position);
        } else if (type.equals("invincibility_potion")) {
            newEntity = new InvincibilityPotion(getNextId(), position);
        } else if (type.equals("switch")) {
            newEntity = new FloorSwitch(getNextId(), position);
        } else if (type.equals("player")) {
            newEntity = new Character(getNextId(), position); // What is character health?
        } else if (type.equals("boulder")) {
            newEntity = new Boulder(getNextId(), position);
        } else if (type.equals("sword")) {
            newEntity = new Sword(getNextId(), position); // is sword interctable?no
        } else if (type.equals("mercenary")) {
            newEntity = new Mercenary(getNextId(), position); // What is mecernary health?
        } else if (type.equals("spider")) {
            newEntity = new Spider(getNextId(), position);
        } else if (type.equals("bow")) {
            newEntity = new Bow(getNextId());
        } else if (type.equals("shield")) {
            newEntity = new Shield(getNextId());
        } else if (type.equals("zombie_toast")) {
            newEntity = new ZombieToast(getNextId(), position);
        } else if (type.equals("zombie_toast_spawner")) {
            newEntity = new ZombieToastSpawner(getNextId(), position);
        } else if (type.equals("bomb_active")) {
            newEntity = new BombActive(getNextId(), position);
        }

        return newEntity;
    }

}
