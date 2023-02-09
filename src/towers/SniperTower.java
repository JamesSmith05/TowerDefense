package towers;

import entities.Tower;
import gameFolder.GamePanel;
import objects.OBJ_SniperRound;

public class SniperTower extends Tower {
    GamePanel gp;

    public SniperTower(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "SniperTower";
        bulletSpeed = 40;
        attack = 5;
        getImage();
        projectile = new OBJ_SniperRound(gp);
        projectile2 = new OBJ_SniperRound(gp);
        projectile3 = new OBJ_SniperRound(gp);
        projectile4 = new OBJ_SniperRound(gp);
        projectile5 = new OBJ_SniperRound(gp);
        projectile6 = new OBJ_SniperRound(gp);
        projectile7 = new OBJ_SniperRound(gp);
        projectile8 = new OBJ_SniperRound(gp);
        range = gp.tileSize * 8;
        fireRate = 60;
        buyPrice = 15;
        targetingType = 1;
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
        image = setup("towers/sniperTower", gp.tileSize, gp.tileSize);
        up1 = setup("towers/sniperTower", gp.tileSize, gp.tileSize);
        up2 = setup("towers/sniperTower", gp.tileSize, gp.tileSize);
        down1 = setup("towers/sniperTower", gp.tileSize, gp.tileSize);
        down2 = setup("towers/sniperTower", gp.tileSize, gp.tileSize);
        left1 = setup("towers/sniperTower", gp.tileSize, gp.tileSize);
        left2 = setup("towers/sniperTower", gp.tileSize, gp.tileSize);
        right1 = setup("towers/sniperTower", gp.tileSize, gp.tileSize);
        right2 = setup("towers/sniperTower", gp.tileSize, gp.tileSize);
    }

    public void setAction() {

//        if (shotAvailableCounter < 20) {
//            shotAvailableCounter++;
//        }
    }
    public void setUpgrade1A(){
        attack += 1;
    }
    public void setUpgrade1B(){
        attack += 1;
    }
    public void setUpgrade1C(){
        attack += 2;
    }
    public void setUpgrade2A(){
        range += gp.tileSize;
    }
    public void setUpgrade2B(){
        fireRate -= 20;
    }
    public void setUpgrade2C(){
        attack += 3;
    }
}