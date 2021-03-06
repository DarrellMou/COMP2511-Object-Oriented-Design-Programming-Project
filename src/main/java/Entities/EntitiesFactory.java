package Entities;

import java.util.UUID;

import Entities.buildableEntities.Bow;
import Entities.buildableEntities.Sceptre;
import Entities.buildableEntities.Shield;
import Entities.collectableEntities.OneRing;
import Entities.collectableEntities.consumables.Bomb;
import Entities.collectableEntities.consumables.HealthPotion;
import Entities.collectableEntities.consumables.InvincibilityPotion;
import Entities.collectableEntities.consumables.InvisibilityPotion;
import Entities.collectableEntities.consumables.Key;
import Entities.collectableEntities.equipments.Anduril;
import Entities.collectableEntities.equipments.Armour;
import Entities.collectableEntities.equipments.Sword;
import Entities.collectableEntities.materials.Arrow;
import Entities.collectableEntities.materials.SunStone;
import Entities.collectableEntities.materials.Treasure;
import Entities.collectableEntities.materials.Wood;
import Entities.staticEntities.BombActive;
import Entities.staticEntities.Boulder;
import Entities.staticEntities.Door;
import Entities.staticEntities.DoorOpen;
import Entities.staticEntities.Exit;
import Entities.staticEntities.FloorSwitch;
import Entities.staticEntities.Portal;
import Entities.staticEntities.SwampTile;
import Entities.staticEntities.Wall;
import Entities.staticEntities.ZombieToastSpawner;
import Items.Equipments.SceptreItem;
import data.DataEntities;
import Entities.movingEntities.Assassin;
import Entities.movingEntities.BribedAssassin;
import Entities.movingEntities.BribedMercenary;
import Entities.movingEntities.Character;
import Entities.movingEntities.Hydra;
import Entities.movingEntities.Mercenary;
import Entities.movingEntities.Spider;
import Entities.movingEntities.ZombieToast;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class EntitiesFactory {

    /**
     * @return String
     */
    public static String getNextId() {
        return UUID.randomUUID().toString();
    }

    /**
     * @param entity
     * @return Entities
     */
    public static Entities creatingEntitiesFactory(EntityResponse entity) {

        return createEntities(entity.getType(), entity.getPosition());

    }

    /**
     * 
     * Given the dataEntities object converted from the JSON object, it creates
     * entities
     * 
     * @param entity
     * @return Entities
     */
    public static Entities creatingEntitiesFactory(DataEntities entity) {
        String type = entity.getType();
        if (type.equals("door") || type.equals("key")) {
            return createEntities(entity.getType(), new Position(entity.getX(), entity.getY()), entity.getKey());

        } else if (type.equals("portal")) {
            return createEntities(entity.getType(), new Position(entity.getX(), entity.getY()), entity.getColour());

        } else if (type.equals("swamp_tile")) {
            return createSwampTile(entity.getType(), new Position(entity.getX(), entity.getY()),
                    entity.getMovementFactor());

        } else {
            return createEntities(entity.getType(), new Position(entity.getX(), entity.getY()));

        }
    }

    /**
     * @param type
     * @param position
     * @param colour
     * @return Entities
     */
    public static Entities createEntities(String type, Position position, String colour) {

        Entities newEntity = null;

        if (type.substring(0, 6).equals("portal")) {
            newEntity = new Portal(getNextId(), position, colour);
        }

        return newEntity;
    }

    public static Entities createSwampTile(String type, Position position, int movementFactor) {

        Entities newEntity = null;

        if (type.equals("swamp_tile")) {
            newEntity = new SwampTile(getNextId(), position, movementFactor);
        }

        return newEntity;
    }

    /**
     * @param type
     * @param position
     * @param key
     * @return Entities
     */
    public static Entities createEntities(String type, Position position, int key) {

        Entities newEntity = null;

        if (type.equals("door")) {
            newEntity = new Door(getNextId(), position, key);
        } else if (type.equals("key")) {
            newEntity = new Key(getNextId(), position, key);
        }

        return newEntity;
    }

    /**
     * Create entity of given type at given position
     * 
     * @param type
     * @param position
     * @return
     */
    public static Entities createEntities(String type, Position position) {
        Entities newEntity = null;

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
        } else if (type.equals("invisibility_potion")) {
            newEntity = new InvisibilityPotion(getNextId(), position);
        } else if (type.equals("health_potion")) {
            newEntity = new HealthPotion(getNextId(), position);
        } else if (type.equals("switch")) {
            newEntity = new FloorSwitch(getNextId(), position);
        } else if (type.equals("boulder")) {
            newEntity = new Boulder(getNextId(), position);
        } else if (type.equals("sword")) {
            newEntity = new Sword(getNextId(), position);
        } else if (type.equals("armour")) {
            newEntity = new Armour(getNextId(), position);
        } else if (type.equals("anduril")) {
            newEntity = new Anduril(getNextId(), position);
        } else if (type.equals("one_ring")) {
            newEntity = new OneRing(getNextId(), position);
        } else if (type.equals("mercenary")) {
            newEntity = new Mercenary(getNextId(), position);
        } else if (type.equals("spider")) {
            newEntity = new Spider(getNextId(), position);
        } else if (type.equals("player")) {
            newEntity = new Character(getNextId(), position);
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
        } else if (type.equals("door_open")) {
            newEntity = new DoorOpen(getNextId(), position);
        } else if (type.equals("hydra")) {
            newEntity = new Hydra(getNextId(), position);
        } else if (type.equals("assassin")) {
            newEntity = new Assassin(getNextId(), position);
        } else if (type.equals("sun_stone")) {
            newEntity = new SunStone(getNextId(), position);
        } else if (type.equals("bribed_assassin")) {
            newEntity = new BribedAssassin(getNextId(), position);
        } else if (type.equals("bribed_mercenary")) {
            newEntity = new BribedMercenary(getNextId(), position);
        }

        return newEntity;
    }

}
