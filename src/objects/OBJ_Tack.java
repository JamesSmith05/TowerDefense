package objects;

import entities.Projectile;
import gameFolder.GamePanel;

public class OBJ_Tack extends Projectile {
    GamePanel gp;
    public OBJ_Tack(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name  = "Tack";
        maxLife = 15;
        life = maxLife;
        attack = 2;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("projectiles/rock_down_2", gp.tileSize, gp.tileSize);

        down1 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("projectiles/rock_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("projectiles/rock_down_2", gp.tileSize, gp.tileSize);

        right1 = setup("projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("projectiles/rock_down_2", gp.tileSize, gp.tileSize);

    }
    public void setSpecialDamage(int monsterIndex) {

    }
}

