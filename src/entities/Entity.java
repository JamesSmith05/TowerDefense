package entities;

import gameFolder.GamePanel;
import logic.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Entity {

    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage fup1,fup2,fdown1,fdown2,fleft1,fleft2,fright1,fright2;
    public BufferedImage image;
    public Rectangle solidArea = new Rectangle(0, 0, 0,0);
    public Rectangle solidAreaDirectionChanger = new Rectangle(0, 0, 6, 6);    // change around for different tile sizes
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int solidAreaDefaultDirectionX, solidAreaDefaultDirectionY;

    public boolean collision = false;

    //STATE
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public int worldX, worldY;
    public String direction = "right";
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean selected;

    //COUNTERS
    public int actionLockCounter=0;
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;

    //ENTITY STATS
    public String name;
    public int speed = 0;
    public int originalSpeed = 0;
    public int maxLife;
    public int life;
    public int attack;
    public int defence;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Projectile projectile, projectile2,projectile3,projectile4,projectile5,projectile6,projectile7,projectile8;
    public int elementID = 1;
    public int elementA, elementB, elementC;

    public int monsterRank;

    public boolean upgrade1A, upgrade1B, upgrade1C, upgrade2A, upgrade2B, upgrade2C;

    public int distanceTraveled;

    public int range;
    public int fireRate;
    public int bulletSpeed;
    public int buyPrice;
    public int upgrade1Aprice;
    public int upgrade1Bprice;
    public int upgrade1Cprice;
    public int upgrade2Aprice;
    public int upgrade2Bprice;
    public int upgrade2Cprice;
    public boolean actionFinished;
    public boolean frozen = false;
    public int frozenCounter;
    public boolean onFire = false;
    public int fireCounter;

    public int targetingType;
    public int towerWorth = 0;

    //TYPE
    public int type; // 0 player, 1 npc, 2 monster ,
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_staff = 3;
    public final int type_shield = 4;
    public final int type_consumable = 5;
    public final int type_smallBoss = 6;
    public final int type_largeBoss = 7;

    public Entity(GamePanel gp){
        this.gp = gp;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDirectionChanger.x = (gp.tileSize/2) - 3;
        solidAreaDirectionChanger.y = (gp.tileSize/2) - 3;
        solidAreaDefaultDirectionX = solidAreaDirectionChanger.x;
        solidAreaDefaultDirectionY = solidAreaDirectionChanger.y;
    }

    public void setAction(){}
    public void setUpgrade1A(){}
    public void setUpgrade1B(){}
    public void setUpgrade1C(){}

    public void setUpgrade2A(){}
    public void setUpgrade2B(){}
    public void setUpgrade2C(){}
    public void damageReaction() {}
    public void use(Entity entity){}

    public void getImage(){}

    public boolean changeElement(int newElement){return false;}

    public void checkEnemyCollision(){
        collisionOn = false;
        gp.cChecker.checkTileForEnemy(this);
    }

    //update position direction and health of entities that don't overide
    public void update(){

        setAction();

        if(type == type_monster){
            int goalCol = gp.goalCol;
            int goalRow = gp.goalRow;
            searchPath(goalCol,goalRow);
            gp.cChecker.checkTileForEnemy(this);
            distanceTraveled += speed;
        }

        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
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

        if(frozen){
            speed = originalSpeed;
            if(speed%2==1){
                speed += 1;
            }
            speed = speed/2;
            frozenCounter++;
            if (frozenCounter >120){
                frozen = false;
                speed = originalSpeed;
                frozenCounter = 0;
                getImage();
            }
        }

        if(onFire){
            fireCounter++;
            if(fireCounter%60 == 0){
                life -= 1;
                if (life <= 0){
                    dying = true;
                }
            }
            if (fireCounter >120){
                onFire = false;
                fireCounter = 0;
            }
        }

        if(invincible){
            invincibleCounter++;
            if (invincibleCounter >5){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX;
        int screenY = worldY;

        switch (direction) {
            case "up":
                if(frozen){
                    if (spriteNum == 1) {image = fup1;}
                    if (spriteNum == 2) {image = fup2;}
                }
                else{
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                break;
            case "down":
                if(frozen){
                    if (spriteNum == 1) {image = fdown1;}
                    if (spriteNum == 2) {image = fdown2;}
                }
                else{
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                break;
            case "left":
                if(frozen){
                    if (spriteNum == 1) {image = fleft1;}
                    if (spriteNum == 2) {image = fleft2;}
                }
                else{
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                break;
            case "right":
                if(frozen){
                    if (spriteNum == 1) {image = fright1;}
                    if (spriteNum == 2) {image = fright2;}
                }
                else{
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                break;
        }

        //Monster HP BAR
        if (hpBarOn && (type == type_monster  || type == type_smallBoss || type == type_largeBoss)) {
            double oneScale;
            int width;
            if (type == type_smallBoss){
                oneScale = (double)gp.tileSize*2/maxLife;
                width = gp.tileSize*2;
            }else if (type == type_largeBoss){
                oneScale = (double)gp.tileSize*3/maxLife;
                width = gp.tileSize*3;
            }else{
                oneScale = (double)gp.tileSize/maxLife;
                width = gp.tileSize;
            }
            double hpBarValue = oneScale*life;
            if(hpBarValue<0){
                hpBarValue=0;
            }

            g2.setColor(new Color(35,35,35));
            g2.fillRect(screenX-1,screenY-16,width+2,12);

            g2.setColor(new Color(255,0,30));
            g2.fillRect(screenX,screenY - 15, (int)hpBarValue, 10);

            hpBarCounter++;
            if(hpBarCounter > 600){
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }

        if (invincible){
            hpBarOn = true;
            hpBarCounter = 0;
            changeOpacity(g2,0.4f);
        }

        if(dying){
            dyingAnimation(g2);
        }

        g2.drawImage(image, screenX, screenY,null);

        changeOpacity(g2,1f);
    }

    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if(!gp.monster[i].invincible){

                gp.playSE(5);

                int damage = attack - gp.monster[i].defence;
                if(damage<0) {
                    damage = 0;
                }

                gp.monster[i].life -= damage;

                gp.ui.addDamageMessage("-" + damage , gp.monster[i].worldX,gp.monster[i].worldY);

                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                if (gp.monster[i].life <=0){
                    gp.monster[i].dying = true;
                    exp += gp.monster[i].exp;

                    Random rand = new Random();
                    if(rand.nextInt(3)<2) {
                        gp.userCurrency += gp.monster[i].coin;
                    }
                }
            }
        }
    }

    public void dyingAnimation(Graphics2D g2){

        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i){changeOpacity(g2,0f);}
        if(dyingCounter > i   && dyingCounter <= i*2){changeOpacity(g2,1f);}  //CAN BE CHANGED TO AN ANIMATION BY CHANGING IMAGE HERE
        if(dyingCounter > i*2 && dyingCounter <= i*3){changeOpacity(g2,0f);}  //CAN BE CHANGED TO AN ANIMATION BY CHANGING IMAGE HERE
        if(dyingCounter > i*3 && dyingCounter <= i*4){changeOpacity(g2,1f);}  //CAN BE CHANGED TO AN ANIMATION BY CHANGING IMAGE HERE
        if(dyingCounter > i*4 && dyingCounter <= i*5){changeOpacity(g2,0f);}  //CAN BE CHANGED TO AN ANIMATION BY CHANGING IMAGE HERE
        if(dyingCounter > i*5 && dyingCounter <= i*6){changeOpacity(g2,1f);}  //CAN BE CHANGED TO AN ANIMATION BY CHANGING IMAGE HERE
        if(dyingCounter > i*6 && dyingCounter <= i*7){changeOpacity(g2,0f);}  //CAN BE CHANGED TO AN ANIMATION BY CHANGING IMAGE HERE
        if(dyingCounter > i*7 && dyingCounter <= i*8){changeOpacity(g2,1f);}  //CAN BE CHANGED TO AN ANIMATION BY CHANGING IMAGE HERE
        if (dyingCounter > 40){
            alive = false;
        }
    }

    public void searchPath(int goalCol, int goalRow){

        int startCol = (worldX+solidArea.x)/gp.tileSize;
        int startRow = (worldY+solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow);

        if(gp.pFinder.search()){

            // next world x and y
            int nextX = gp.pFinder.pathList.get(0).col*gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row*gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX+gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX+gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if (enTopY > nextY && enLeftX > nextX){
                direction = "up";
                checkEnemyCollision();
                if(collisionOn){
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                direction = "up";
                checkEnemyCollision();
                if(collisionOn){
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                direction = "down";
                checkEnemyCollision();
                if(collisionOn){
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                direction = "down";
                checkEnemyCollision();
                if(collisionOn){
                    direction = "right";
                }
            }

        }
        //if reaches goal
        if(startCol == goalCol && startRow == goalRow){
            gp.userLife -= attack;
            if (gp.userLife<0){
                gp.userLife = 0;
            }
            dying = true;
        }
    }

    public void changeOpacity(Graphics2D g2, Float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
    }

    public BufferedImage setup(String imagePath,int width,int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/" + imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (Exception e) {
        }
        return image;
    }

}
