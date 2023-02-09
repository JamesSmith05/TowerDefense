package towers;

import entities.Tower;
import gameFolder.GamePanel;
import objects.OBJ_Iceball;


public class IceTower extends Tower {
    GamePanel gp;

    public IceTower(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "IceTower";
        bulletSpeed = 12;
        attack = 3;
        getImage();
        projectile = new OBJ_Iceball(gp);
        projectile2 = new OBJ_Iceball(gp);
        projectile3 = new OBJ_Iceball(gp);
        projectile4 = new OBJ_Iceball(gp);
        projectile5 = new OBJ_Iceball(gp);
        projectile6 = new OBJ_Iceball(gp);
        projectile7 = new OBJ_Iceball(gp);
        projectile8 = new OBJ_Iceball(gp);

        range = gp.tileSize*4;
        fireRate = 30;
        buyPrice = 20;
        targetingType = 3;
        upgrade1Aprice = 10;
        upgrade1Bprice = 15;
        upgrade1Cprice = 20;
        upgrade2Aprice = 15;
        upgrade2Bprice = 20;
        upgrade2Cprice = 25;

        elementA = 1;
        elementB = 4;
        elementC = 6;
    }
    public void getImage() {
        image = setup("towers/iceTower", gp.tileSize, gp.tileSize);
        up1 = setup("towers/iceTower", gp.tileSize, gp.tileSize);
        up2 = setup("towers/iceTower", gp.tileSize, gp.tileSize);
        down1 = setup("towers/iceTower", gp.tileSize, gp.tileSize);
        down2 = setup("towers/iceTower", gp.tileSize, gp.tileSize);
        left1 = setup("towers/iceTower", gp.tileSize, gp.tileSize);
        left2 = setup("towers/iceTower", gp.tileSize, gp.tileSize);
        right1 = setup("towers/iceTower", gp.tileSize, gp.tileSize);
        right2 = setup("towers/iceTower", gp.tileSize, gp.tileSize);
    }
    public void setAction() {

    }
    public void setUpgrade1A(){
        attack+=1;
    }
    public void setUpgrade1B(){
        attack+=1;
    }
    public void setUpgrade1C(){
        attack+=2;
    }
    public void setUpgrade2A(){
        range += gp.tileSize;
    }
    public void setUpgrade2B(){
        attack+=2;
    }
    public void setUpgrade2C(){
        fireRate -= 5;
    }
}

