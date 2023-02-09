package logic;

import entities.Entity;
import gameFolder.GamePanel;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxScreenCol][gp.maxScreenRow];

        int col = 0;
        int row = 0;
        while (col< gp.maxScreenCol && row < gp.maxScreenRow){

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = (gp.tileSize/2) - 3;
            eventRect[col][row].y = (gp.tileSize/2) - 3;
            eventRect[col][row].width = 6;
            eventRect[col][row].height = 6;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxScreenCol){
                col = 0;
                row++;
            }
        }
    }

    public  void checkEvent(Entity entity){
        if(hit(entity,12, 13, "any")){
            faceUp(entity);}

        if(hit(entity,12,9,"any")){
            faceLeft(entity);}

        if(hit(entity,2, 9, "any")){
            faceUp(entity);}

        if(hit(entity,2, 5, "any")){
            faceRight(entity);}

        if(hit(entity,15, 5, "any")){
            faceDown(entity);}

        if(hit(entity,15, 13, "any")){
            faceRight(entity);}

        if(hit(entity,17, 13, "any")){
            faceUp(entity);}

        if(hit(entity,17, 2, "any")){
            faceLeft(entity);}

        if(hit(entity,0,2,"any")){
            damageUser(entity);
        }

    }
    public boolean hit(Entity entity, int col, int row, String reqDirection){
        boolean hit = false;

        entity.solidAreaDirectionChanger.x = entity.worldX + entity.solidAreaDirectionChanger.x;
        entity.solidAreaDirectionChanger.y = entity.worldY + entity.solidAreaDirectionChanger.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if (entity.solidAreaDirectionChanger.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
            if(entity.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = entity.worldX;
                previousEventY = entity.worldY;
            }
        }

        entity.solidAreaDirectionChanger.x = entity.solidAreaDefaultDirectionX;
        entity.solidAreaDirectionChanger.y = entity.solidAreaDefaultDirectionY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
    public void faceUp(Entity entity){
        entity.direction = "up";
    }
    public void faceDown(Entity entity){
        entity.direction = "down";
    }
    public void faceLeft(Entity entity){
        entity.direction = "left";
    }
    public void faceRight(Entity entity){
        entity.direction = "right";
    }
    public void damageUser(Entity entity){
        gp.userLife -= entity.attack;
        if (gp.userLife<0){
            gp.userLife = 0;
        }
        entity.dying = true;
    }

}
