package objects;

import entities.Projectile;
import gameFolder.GamePanel;

public class OBJ_Iceball extends Projectile {
    GamePanel gp;
    public OBJ_Iceball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name  = "Iceball";
        speed = 5;
        maxLife = 120;
        life = maxLife;
        attack = 2;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setup("projectiles/iceball_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("projectiles/iceball_up_2", gp.tileSize, gp.tileSize);

        down1 = setup("projectiles/iceball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("projectiles/iceball_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("projectiles/iceball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("projectiles/iceball_left_2", gp.tileSize, gp.tileSize);

        right1 = setup("projectiles/iceball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("projectiles/iceball_right_2", gp.tileSize, gp.tileSize);

    }
    public void setSpecialDamage(int monsterIndex) {
        gp.monster[monsterIndex].frozen=true;
        gp.monster[monsterIndex].frozenCounter=0;

    }
}
