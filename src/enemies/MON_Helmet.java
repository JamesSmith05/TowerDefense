package enemies;

import entities.Entity;
import gameFolder.GamePanel;

import java.util.Random;

public class MON_Helmet extends Entity {

    GamePanel gp;

    public MON_Helmet(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Helmet";
        elementID = 6;
        monsterRank = 4;
        originalSpeed = 4;
        speed = originalSpeed;
        maxLife = 3;
        life = maxLife;
        attack = 2;
        defence = 0;
        exp = 2;
        coin = 2;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 56;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage (){
        image = setup("monster/helmet_up_1", gp.tileSize, gp.tileSize);
        up1 = setup("monster/helmet_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("monster/helmet_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("monster/helmet_up_1", gp.tileSize, gp.tileSize);
        down2 = setup("monster/helmet_up_2", gp.tileSize, gp.tileSize);
        left1 = setup("monster/helmet_up_1", gp.tileSize, gp.tileSize);
        left2 = setup("monster/helmet_up_2", gp.tileSize, gp.tileSize);
        right1 = setup("monster/helmet_up_1", gp.tileSize, gp.tileSize);
        right2 = setup("monster/helmet_up_2", gp.tileSize, gp.tileSize);

        fup1 = setup("monster/helmet_up_1_frozen", gp.tileSize, gp.tileSize);
        fup2 = setup("monster/helmet_up_2_frozen", gp.tileSize, gp.tileSize);
        fdown1 = setup("monster/helmet_up_1_frozen", gp.tileSize, gp.tileSize);
        fdown2 = setup("monster/helmet_up_2_frozen", gp.tileSize, gp.tileSize);
        fleft1 = setup("monster/helmet_up_1_frozen", gp.tileSize, gp.tileSize);
        fleft2 = setup("monster/helmet_up_2_frozen", gp.tileSize, gp.tileSize);
        fright1 = setup("monster/helmet_up_1_frozen", gp.tileSize, gp.tileSize);
        fright2 = setup("monster/helmet_up_2_frozen", gp.tileSize, gp.tileSize);
    }

    public void setAction(){

    }
//    public void damageReaction(){
//        actionLockCounter = 0;
//        direction = gp.player.direction;
//    }
}
