package enemies;

import entities.Entity;
import gameFolder.GamePanel;

import java.util.Random;

public class MON_Undead extends Entity {

    GamePanel gp;
    boolean canSpawn = true;

    public MON_Undead(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Undead";
        elementID = 6;
        monsterRank = 4;
        originalSpeed = 1;
        speed = originalSpeed;
        maxLife = 30;
        life = maxLife;
        attack = 5;
        defence = 2;
        exp = 2;
        coin = 10;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 56;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage (){
        image = setup("monster/undead_up_1", gp.tileSize, gp.tileSize);
        up1 = setup("monster/undead_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("monster/undead_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("monster/undead_up_1", gp.tileSize, gp.tileSize);
        down2 = setup("monster/undead_up_2", gp.tileSize, gp.tileSize);
        left1 = setup("monster/undead_up_1", gp.tileSize, gp.tileSize);
        left2 = setup("monster/undead_up_2", gp.tileSize, gp.tileSize);
        right1 = setup("monster/undead_up_1", gp.tileSize, gp.tileSize);
        right2 = setup("monster/undead_up_2", gp.tileSize, gp.tileSize);

        fup1 = setup("monster/undead_up_1_frozen", gp.tileSize, gp.tileSize);
        fup2 = setup("monster/undead_up_2_frozen", gp.tileSize, gp.tileSize);
        fdown1 = setup("monster/undead_up_1_frozen", gp.tileSize, gp.tileSize);
        fdown2 = setup("monster/undead_up_2_frozen", gp.tileSize, gp.tileSize);
        fleft1 = setup("monster/undead_up_1_frozen", gp.tileSize, gp.tileSize);
        fleft2 = setup("monster/undead_up_2_frozen", gp.tileSize, gp.tileSize);
        fright1 = setup("monster/undead_up_1_frozen", gp.tileSize, gp.tileSize);
        fright2 = setup("monster/undead_up_2_frozen", gp.tileSize, gp.tileSize);
    }

    public void setAction(){

        actionLockCounter++;

        System.out.println(actionLockCounter);

        if (actionLockCounter>100){
            canSpawn = true;
        }
        if (actionLockCounter>200){
            gp.aSetter.setSpecificEnemy("Helmet",this.worldX,this.worldY,distanceTraveled);
            actionLockCounter = 100;
        }
    }

    public void damageReaction(){
        if(canSpawn){
            actionLockCounter = 0;
            gp.aSetter.setSpecificEnemy("Helmet",this.worldX,this.worldY,distanceTraveled);
            canSpawn = false;
        }
    }
}
