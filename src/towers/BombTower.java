package towers;

import entities.Tower;
import gameFolder.GamePanel;
import objects.OBJ_Bomb;

public class BombTower extends Tower {
    GamePanel gp;

    public BombTower(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "BombTower";
        bulletSpeed = 12;
        attack = 4;
        getImage();
        projectile = new OBJ_Bomb(gp);
        projectile2 = new OBJ_Bomb(gp);
        projectile3 = new OBJ_Bomb(gp);
        projectile4 = new OBJ_Bomb(gp);
        projectile5 = new OBJ_Bomb(gp);
        projectile6 = new OBJ_Bomb(gp);
        projectile7 = new OBJ_Bomb(gp);
        projectile8 = new OBJ_Bomb(gp);

        range = gp.tileSize*4;
        fireRate = 120;
        buyPrice = 10;
        targetingType = 3;
        upgrade1Aprice = 10;
        upgrade1Bprice = 15;
        upgrade1Cprice = 20;
        upgrade2Aprice = 15;
        upgrade2Bprice = 20;
        upgrade2Cprice = 25;

        elementA = 1;
        elementB = 5;
        elementC = 7;
    }
    public void getImage() {
        image = setup("towers/bombTower", gp.tileSize, gp.tileSize);
        up1 = setup("towers/bombTower", gp.tileSize, gp.tileSize);
        up2 = setup("towers/bombTower", gp.tileSize, gp.tileSize);
        down1 = setup("towers/bombTower", gp.tileSize, gp.tileSize);
        down2 = setup("towers/bombTower", gp.tileSize, gp.tileSize);
        left1 = setup("towers/bombTower", gp.tileSize, gp.tileSize);
        left2 = setup("towers/bombTower", gp.tileSize, gp.tileSize);
        right1 = setup("towers/bombTower", gp.tileSize, gp.tileSize);
        right2 = setup("towers/bombTower", gp.tileSize, gp.tileSize);
    }
    public void setAction() {

    }
    public void setUpgrade1A(){
        attack +=1;
    }
    public void setUpgrade1B(){
        attack +=1;
    }
    public void setUpgrade1C(){
        attack +=1;
    }
    public void setUpgrade2A(){
        range += gp.tileSize;
    }
    public void setUpgrade2B(){
        fireRate -=10;
    }
    public void setUpgrade2C(){
        fireRate -=10;
    }
}

