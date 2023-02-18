package entities;

import gameFolder.GamePanel;

import java.awt.*;
import java.util.Objects;


public class Tower extends Entity {
    GamePanel gp;

    int savedMonsterIndex;
    double selectedMonsterDistanceX, selectedMonsterDistanceY, selectedMonsterDistance;

    public Tower(GamePanel gp) {
        super(gp);
        this.gp = gp;

        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public boolean changeElement(int newElement){
        if(newElement == 1){
            newElement = elementA;
        } else if(newElement == 2){
            newElement = elementB;
        } else if(newElement == 3){
            newElement = elementC;
        }
        if(elementID == newElement){
            return false;
        }
        else{
            elementID = newElement;
            return true;
        }
    }

    public void update() {

        if (targetingType == 1){
            returnStrongestEnemy();
        }else if (targetingType == 2){
            returnClosestEnemy();
        }else if (targetingType == 3){
            returnFirstEnemy();
        }else if (targetingType == 4){
            returnLastEnemy();
        }

        double distanceX = selectedMonsterDistanceX;
        double distanceY = selectedMonsterDistanceY;
        double distanceABS = selectedMonsterDistance;

        if (Objects.equals(name, "MachineGunTower") && gp.remainingEnemies != 0 ){
            distanceX = gp.mouseX-worldX;
            distanceY = gp.mouseY-worldY;
            distanceABS = 1;
        }

        if (distanceABS<range){
            if(shotAvailableCounter >= fireRate ){
                if(!Objects.equals(name, "TackTower")){
                    createProjectiles(distanceX,distanceY);
                }
                setAction();
                shotAvailableCounter = 0;
            }
        }

        if(shotAvailableCounter < fireRate){
            shotAvailableCounter++;
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

    void createProjectiles(double distanceX, double distanceY){

        if(!projectile.alive){
            projectile.set(bulletSpeed,attack,worldX,worldY,distanceX,distanceY,true,this,savedMonsterIndex, elementID);
            gp.projectileList.add(projectile);
        }
        else if(!projectile2.alive){
            projectile2.set(bulletSpeed,attack,worldX,worldY,distanceX,distanceY,true,this,savedMonsterIndex, elementID);
            gp.projectileList.add(projectile2);
        }
        else if(!projectile3.alive){
            projectile3.set(bulletSpeed,attack,worldX,worldY,distanceX,distanceY,true,this,savedMonsterIndex, elementID);
            gp.projectileList.add(projectile3);
        }
        else if(!projectile4.alive){
            projectile4.set(bulletSpeed,attack,worldX,worldY,distanceX,distanceY,true,this,savedMonsterIndex, elementID);
            gp.projectileList.add(projectile4);
        }
        else if(!projectile5.alive){
            projectile5.set(bulletSpeed,attack,worldX,worldY,distanceX,distanceY,true,this,savedMonsterIndex, elementID);
            gp.projectileList.add(projectile5);
        }
        else if(!projectile6.alive){
            projectile6.set(bulletSpeed,attack,worldX,worldY,distanceX,distanceY,true,this,savedMonsterIndex, elementID);
            gp.projectileList.add(projectile6);
        }
        else if(!projectile7.alive){
            projectile7.set(bulletSpeed,attack,worldX,worldY,distanceX,distanceY,true,this,savedMonsterIndex, elementID);
            gp.projectileList.add(projectile7);

        }
        else if(!projectile8.alive){
            projectile8.set(bulletSpeed,attack,worldX,worldY,distanceX,distanceY,true,this,savedMonsterIndex, elementID);
            gp.projectileList.add(projectile8);
        }

    }

    public void draw(Graphics2D g2) {

        g2.drawImage(down1, worldX, worldY,null);

        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));


    }

    public void returnClosestEnemy(){
        int i = 0;
        double monsterDistanceX, monsterDistanceY, monsterDistanceABS;
        selectedMonsterDistance = 1000;
        savedMonsterIndex = 0;
        while (i < gp.monster.length){
            if(gp.monster[i] != null && !gp.monster[i].dying){                                      //&& !gp.monster[i].invincible  to make towers ignore invincible enemies
                monsterDistanceX = (gp.monster[i].worldX - worldX);
                monsterDistanceY = (gp.monster[i].worldY - worldY);
                monsterDistanceABS = Math.sqrt((monsterDistanceX*monsterDistanceX)+(monsterDistanceY*monsterDistanceY));
                if(monsterDistanceABS<selectedMonsterDistance && monsterDistanceABS<range){
                    savedMonsterIndex = i;
                    selectedMonsterDistance = monsterDistanceABS;
                    selectedMonsterDistanceX = monsterDistanceX;
                    selectedMonsterDistanceY = monsterDistanceY;
                }
            }
            i++;
        }
    }

    public void returnStrongestEnemy(){
        int i = 0;
        double monsterDistanceX, monsterDistanceY, monsterDistanceABS;
        int largestMonsterHealth = 0;
        selectedMonsterDistance = 1000;
        savedMonsterIndex = 0;
        while (i < gp.monster.length){
            if(gp.monster[i] != null && !gp.monster[i].dying){                                      //&& !gp.monster[i].invincible  to make towers ignore invincible enemies
                monsterDistanceX = (gp.monster[i].worldX - worldX);
                monsterDistanceY = (gp.monster[i].worldY - worldY);
                monsterDistanceABS = Math.sqrt((monsterDistanceX*monsterDistanceX)+(monsterDistanceY*monsterDistanceY));
                if(gp.monster[i].life > largestMonsterHealth && monsterDistanceABS<range){
                    largestMonsterHealth = gp.monster[i].life;
                    savedMonsterIndex = i;
                    selectedMonsterDistance = monsterDistanceABS;
                    selectedMonsterDistanceX = monsterDistanceX;
                    selectedMonsterDistanceY = monsterDistanceY;
                }
            }
            i++;
        }
    }

    public void returnFirstEnemy(){
        int i = 0;
        double monsterDistanceX, monsterDistanceY, monsterDistanceABS;
        int enemyPositionInWave;
        int selectedMonsterPosition = 0;
        selectedMonsterDistance = 1000;
        savedMonsterIndex = 0;
        while (i < gp.monster.length){
            if(gp.monster[i] != null && !gp.monster[i].dying){                                      //&& !gp.monster[i].invincible  to make towers ignore invincible enemies
                monsterDistanceX = (gp.monster[i].worldX - worldX);
                monsterDistanceY = (gp.monster[i].worldY - worldY);
                monsterDistanceABS = Math.sqrt((monsterDistanceX*monsterDistanceX)+(monsterDistanceY*monsterDistanceY));
                enemyPositionInWave = gp.monster[i].distanceTraveled;
                if(enemyPositionInWave>selectedMonsterPosition && monsterDistanceABS<range){
                    selectedMonsterPosition = enemyPositionInWave;
                    savedMonsterIndex = i;
                    selectedMonsterDistance = monsterDistanceABS;
                    selectedMonsterDistanceX = monsterDistanceX;
                    selectedMonsterDistanceY = monsterDistanceY;
                }
            }
            i++;
        }
    }

    public void returnLastEnemy(){
        int i = 0;
        double monsterDistanceX, monsterDistanceY, monsterDistanceABS; //initialise variables
        int enemyPositionInWave; //initialise variables
        int selectedMonsterPosition = 100000; //initialise variables
        selectedMonsterDistance = 1000; //initialise variables
        savedMonsterIndex = 0; //initialise variables
        while (i < gp.monster.length){ //loops for monster array size
            if(gp.monster[i] != null && !gp.monster[i].dying){ //if the location isnt null, and the monster is alive
                monsterDistanceX = (gp.monster[i].worldX - worldX); //cardinal distances
                monsterDistanceY = (gp.monster[i].worldY - worldY); //cardinal distances
                monsterDistanceABS = Math.sqrt((monsterDistanceX*monsterDistanceX)+(monsterDistanceY*monsterDistanceY)); //distance as the crow flies
                enemyPositionInWave = gp.monster[i].distanceTraveled; //since the goal is to return the enemy that is furthest back but still within range the distance traveled is used to compare
                if(enemyPositionInWave<selectedMonsterPosition && monsterDistanceABS<range){ // if the enemy has travelled less and is still in range it is selected
                    selectedMonsterPosition = enemyPositionInWave; // the new values from the selected enemy are updated
                    savedMonsterIndex = i; // the new values from the selected enemy are updated
                    selectedMonsterDistance = monsterDistanceABS; // the new values from the selected enemy are updated
                    selectedMonsterDistanceX = monsterDistanceX; // the new values from the selected enemy are updated
                    selectedMonsterDistanceY = monsterDistanceY; // the new values from the selected enemy are updated
                }
            }
            i++; //works the same as a for loop
        }
    }


}
