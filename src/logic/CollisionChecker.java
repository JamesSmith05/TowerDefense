package logic;

import entities.Entity;
import gameFolder.GamePanel;

import java.awt.*;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTileForEnemy(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].enemyCollision || gp.tileM.tile[tileNum2].enemyCollision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].enemyCollision || gp.tileM.tile[tileNum2].enemyCollision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].enemyCollision || gp.tileM.tile[tileNum2].enemyCollision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].enemyCollision || gp.tileM.tile[tileNum2].enemyCollision) {
                    entity.collisionOn = true;
                }
                break;
        }

    }

        public boolean checkMouseTile(int mouseX, int mouseY, Rectangle solidArea){
            int entityLeftWorldX = mouseX + solidArea.x;
            int entityRightWorldX = mouseX + solidArea.x + solidArea.width;
            int entityTopWorldY = mouseY + solidArea.y;
            int entityBottomWorldY = mouseY + solidArea.y + solidArea.height;

            int entityLeftCol = entityLeftWorldX / gp.tileSize;
            int entityRightCol = entityRightWorldX / gp.tileSize;
            int entityTopRow = entityTopWorldY / gp.tileSize;
            int entityBottomRow = entityBottomWorldY / gp.tileSize;

            Boolean collision = false;

            if(entityLeftCol>=0 && entityRightCol<gp.maxScreenCol && entityBottomRow<gp.maxScreenRow && entityTopRow>=0){
                int tileNum1, tileNum2, tileNum3, tileNum4;

                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum3 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum4 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].mouseCollision || gp.tileM.tile[tileNum2].mouseCollision || gp.tileM.tile[tileNum3].mouseCollision || gp.tileM.tile[tileNum4].mouseCollision) {
                    collision = true;
                }
            }
            else{
                collision = true;
            }
            return collision;

        }

    public boolean checkEntityMouse(int mouseX, int mouseY, Rectangle solidArea, Entity[] target, boolean selectTower){
        boolean collision = false;
        int solidAreaDefaultX = solidArea.x;
        int solidAreaDefaultY = solidArea.y;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                //get entity solid area position
                solidArea.x = mouseX + solidArea.x;
                solidArea.y = mouseY + solidArea.y;
                //get object solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                if (solidArea.intersects(target[i].solidArea)) {
                    collision = true;
                    if(selectTower){
                        gp.interactTowerIndex = i;
                    }
                }
                solidArea.x = solidAreaDefaultX;
                solidArea.y = solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return collision;
    }
    public int checkHittableEntity(Entity entity,Entity[] target){
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null && index==999) {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;

                    }
                    if (entity.solidArea.intersects(target[i].solidArea) && !target[i].invincible) {
                        if(target[i] != entity){
                            entity.collisionOn = true;
                            index = i;
                        }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public int checkSingleEntity(Entity entity,Entity target,int targetIndex){
        int index = 999;
            if (target != null) {
                //get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //get object solid area position
                target.solidArea.x = target.worldX + target.solidArea.x;
                target.solidArea.y = target.worldY + target.solidArea.y;

                entity.solidArea.y += entity.speed;
                entity.solidArea.x += entity.speed;

                if (entity.solidArea.intersects(target.solidArea)) {
                    if(target != entity){
                        entity.collisionOn = true;
                        index = targetIndex;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target.solidArea.x = target.solidAreaDefaultX;
                target.solidArea.y = target.solidAreaDefaultY;
            }
        return index;
    }


}
