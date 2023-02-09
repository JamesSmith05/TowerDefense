package entities;

import gameFolder.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Projectile extends Entity{

    public Entity user;

    public double speedX, speedY,distanceX,distanceY;
    int targetIndex;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int speed,int attack, int worldX, int worldY, double distanceX, double distanceY, boolean alive, Entity user, int targetIndex,int elementID){

        this.worldX = worldX;
        this.worldY = worldY;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.speed = speed;
        this.attack = attack;
        this.targetIndex = targetIndex;
        this.elementID = elementID;

        solidArea = new Rectangle(gp.tileSize/4, gp.tileSize/4, gp.tileSize/2, gp.tileSize/2);

    }

    public void setSpecialDamage(int monsterIndex){

    }

    public void update(){

        int monsterIndex;

        if (Objects.equals(user.name, "SniperTower")){
            monsterIndex = gp.cChecker.checkSingleEntity(this,gp.monster[targetIndex],targetIndex);
        }else{
            monsterIndex = gp.cChecker.checkHittableEntity(this,gp.monster);
        }

        if(monsterIndex != 999){
            user.damageMonster(monsterIndex, attack);
            setSpecialDamage(monsterIndex);
            alive = false;
        }

        calculateVectors();

        worldY += speedY;
        worldX += speedX;

        life--;
        if(life <= 0){
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

    }

    public void calculateVectors(){

//        if(Math.abs(distanceX)<Math.abs(distanceY)){
//            if(Math.abs(distanceX)/Math.abs(distanceY) < 0.025 ){
//                speedX = 0.05;
//            }else if(Math.abs(distanceX)/Math.abs(distanceY) < 0.05 ){
//                speedX = 0.1;
//            }else if(Math.abs(distanceX)/Math.abs(distanceY) < 0.075 ){
//                speedX = 0.2;
//            }else if(Math.abs(distanceX)/Math.abs(distanceY) < 0.10 ){
//                speedX = 0.3;
//            }else if(Math.abs(distanceX)/Math.abs(distanceY) < 0.125 ){
//                speedX = 0.4;
//            }
//            else{
//                speedX = Math.sqrt(speed*speed*(Math.abs(distanceX)/(Math.abs(distanceX)+ Math.abs(distanceY))));
//            }
//            speedY = Math.sqrt(speed*speed*(Math.abs(distanceY)/(Math.abs(distanceX)+ Math.abs(distanceY))));
//        }else{
//            if(Math.abs(distanceY)/Math.abs(distanceX) < 0.025 ){
//                speedY=0.05;
//            }else if(Math.abs(distanceY)/Math.abs(distanceX) < 0.05 ){
//                speedY=0.1;
//            }else if(Math.abs(distanceY)/Math.abs(distanceX) < 0.075 ){
//                speedY=0.2;
//            }else if(Math.abs(distanceY)/Math.abs(distanceX) < 0.10 ){
//                speedY=0.3;
//            }else if(Math.abs(distanceY)/Math.abs(distanceX) < 0.125 ){
//                speedY=0.4;
//            }
//            else{
//                speedY = Math.sqrt(speed*speed*(Math.abs(distanceY)/(Math.abs(distanceX)+ Math.abs(distanceY))));
//            }
//            speedX = Math.sqrt(speed*speed*(Math.abs(distanceX)/(Math.abs(distanceX)+ Math.abs(distanceY))));
//        }

        speedX = speed*(Math.abs(distanceX)/(Math.abs(distanceX)+Math.abs(distanceY)));
        speedY = speed - speedX;

        if(distanceX<0 && distanceY<0){
            speedY = -speedY;
            speedX = -speedX;
        }else if (distanceX>=0 && distanceY<0){
            speedY = -speedY;

        }else if (distanceX<0 && distanceY>=0) {
            speedX = -speedX;
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        if (spriteNum == 1) {image = down1;}
        if (spriteNum == 2) {image = down2;}

        //g2.drawString(String.valueOf(worldX) + String.valueOf(worldY),worldX,worldY);

        g2.drawImage(image, worldX, worldY,null);
    }
}