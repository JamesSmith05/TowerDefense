package towers;

import entities.Tower;
import gameFolder.GamePanel;
import objects.OBJ_Tack;

public class TackTower extends Tower {
    GamePanel gp;

    public TackTower(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "TackTower";
        bulletSpeed = 8;
        attack = 2;
        getImage();
        projectile = new OBJ_Tack(gp);
        projectile2 = new OBJ_Tack(gp);
        projectile3 = new OBJ_Tack(gp);
        projectile4 = new OBJ_Tack(gp);
        projectile5 = new OBJ_Tack(gp);
        projectile6 = new OBJ_Tack(gp);
        projectile7 = new OBJ_Tack(gp);
        projectile8 = new OBJ_Tack(gp);

        range = gp.tileSize*2;
        fireRate = 60;
        buyPrice = 15;
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
        image = setup("towers/tackTower", gp.tileSize, gp.tileSize);
        up1 = setup("towers/tackTower", gp.tileSize, gp.tileSize);
        up2 = setup("towers/tackTower", gp.tileSize, gp.tileSize);
        down1 = setup("towers/tackTower", gp.tileSize, gp.tileSize);
        down2 = setup("towers/tackTower", gp.tileSize, gp.tileSize);
        left1 = setup("towers/tackTower", gp.tileSize, gp.tileSize);
        left2 = setup("towers/tackTower", gp.tileSize, gp.tileSize);
        right1 = setup("towers/tackTower", gp.tileSize, gp.tileSize);
        right2 = setup("towers/tackTower", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        if(!projectile.alive) {
            projectile.set((int) Math.sqrt((bulletSpeed*bulletSpeed)*2),attack,worldX,worldY,-100,-100,true,this,0,elementID);

            gp.projectileList.add(projectile);
        }
        if(!projectile2.alive) {
            projectile2.set(bulletSpeed,attack,worldX,worldY,-100,0,true,this,0,elementID);
            gp.projectileList.add(projectile2);
        }
        if(!projectile3.alive) {
            projectile3.set((int) Math.sqrt((bulletSpeed*bulletSpeed)*2),attack,worldX,worldY,-100,100,true,this,0,elementID);
            gp.projectileList.add(projectile3);
        }
        if(!projectile4.alive) {
            projectile4.set(bulletSpeed,attack,worldX,worldY,0,-100,true,this,0,elementID);
            gp.projectileList.add(projectile4);
        }
        if(!projectile5.alive) {
            projectile5.set(bulletSpeed,attack,worldX,worldY,0,100,true,this,0,elementID);
            gp.projectileList.add(projectile5);
        }
        if(!projectile6.alive) {
            projectile6.set((int) Math.sqrt((bulletSpeed*bulletSpeed)*2),attack,worldX,worldY,100,-100,true,this,0,elementID);
            gp.projectileList.add(projectile6);
        }
        if(!projectile7.alive) {
            projectile7.set(bulletSpeed,attack,worldX,worldY,100,0,true,this,0,elementID);
            gp.projectileList.add(projectile7);
        }
        if(!projectile8.alive) {
            projectile8.set((int) Math.sqrt((bulletSpeed*bulletSpeed)*2),attack,worldX,worldY,100,100,true,this,0,elementID);
            gp.projectileList.add(projectile8);
        }
    }
    public void setUpgrade1A(){
        attack += 1;
    }
    public void setUpgrade1B(){
        attack += 1;
    }
    public void setUpgrade1C(){
        attack += 1;
    }
    public void setUpgrade2A(){
        range += gp.tileSize;
    }
    public void setUpgrade2B(){
        fireRate -= 10;
    }
    public void setUpgrade2C(){
        fireRate-=10;
    }
}
