package objects;

import entities.Projectile;
import gameFolder.GamePanel;

public class OBJ_Bomb extends Projectile {
    GamePanel gp;
    public int explosionRange = 2;
    public OBJ_Bomb(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name  = "bomb";
        maxLife = 110;
        life = maxLife;
        attack = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setup("projectiles/bomb_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("projectiles/bomb_down_2", gp.tileSize, gp.tileSize);

        down1 = setup("projectiles/bomb_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("projectiles/bomb_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("projectiles/bomb_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("projectiles/bomb_down_2", gp.tileSize, gp.tileSize);

        right1 = setup("projectiles/bomb_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("projectiles/bomb_down_2", gp.tileSize, gp.tileSize);
    }

    public void setSpecialDamage(int monsterIndex) {

        for (int j = 0; j < gp.obj.length; j++) {
            if (gp.obj[j] == null) {
                gp.obj[j] = new OBJ_Explosion(gp);
                gp.obj[j].worldX = worldX - gp.tileSize;
                gp.obj[j].worldY = worldY - gp.tileSize;
                j = gp.obj.length;
            }
        }
        for (int i = 0; i < gp.monster.length; i++) {
            if(gp.monster[i] != null && i != monsterIndex){
                int monsterDistanceX = (gp.monster[i].worldX - worldX + (gp.tileSize/2));
                int monsterDistanceY = (gp.monster[i].worldY - worldY + (gp.tileSize/2));
                double monsterDistanceABS = Math.sqrt((monsterDistanceX*monsterDistanceX)+(monsterDistanceY*monsterDistanceY));
                if(monsterDistanceABS<(explosionRange*gp.tileSize)){
                    user.damageMonster(i, attack);
                }
            }
        }
    }
}

