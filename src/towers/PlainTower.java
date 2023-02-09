package towers;

import entities.Tower;
import gameFolder.GamePanel;

import objects.OBJ_Rock;

public class PlainTower extends Tower {

    GamePanel gp;

    public PlainTower(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "PlainTower";
        bulletSpeed = 12;
        attack = 2;
        getImage();
        projectile = new OBJ_Rock(gp);
        projectile2 = new OBJ_Rock(gp);
        projectile3 = new OBJ_Rock(gp);
        projectile4 = new OBJ_Rock(gp);
        projectile5 = new OBJ_Rock(gp);
        projectile6 = new OBJ_Rock(gp);
        projectile7 = new OBJ_Rock(gp);
        projectile8 = new OBJ_Rock(gp);

        range = gp.tileSize*4;
        fireRate = 20;
        buyPrice = 5;
        targetingType = 3;
        upgrade1Aprice = 10;
        upgrade1Bprice = 15;
        upgrade1Cprice = 20;
        upgrade2Aprice = 15;
        upgrade2Bprice = 20;
        upgrade2Cprice = 25;

        elementA = 1;
        elementB = 5;
        elementC = 6;
    }
    public void getImage() {
        image = setup("towers/plainTower", gp.tileSize, gp.tileSize);
        up1 = setup("towers/plainTower", gp.tileSize, gp.tileSize);
        up2 = setup("towers/plainTower", gp.tileSize, gp.tileSize);
        down1 = setup("towers/plainTower", gp.tileSize, gp.tileSize);
        down2 = setup("towers/plainTower", gp.tileSize, gp.tileSize);
        left1 = setup("towers/plainTower", gp.tileSize, gp.tileSize);
        left2 = setup("towers/plainTower", gp.tileSize, gp.tileSize);
        right1 = setup("towers/plainTower", gp.tileSize, gp.tileSize);
        right2 = setup("towers/plainTower", gp.tileSize, gp.tileSize);
    }
    public void setAction() {
    }
    public void setUpgrade1A(){
        attack += 1;
    }
    public void setUpgrade1B(){
        attack += 2;
    }
    public void setUpgrade1C(){
        attack +=4;
    }
    public void setUpgrade2A(){
        range += gp.tileSize;
    }
    public void setUpgrade2B(){
        range += gp.tileSize;
    }
    public void setUpgrade2C(){
        range += gp.tileSize;
    }
}
