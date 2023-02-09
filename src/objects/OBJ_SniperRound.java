package objects;

        import entities.Projectile;
        import gameFolder.GamePanel;

        import java.awt.*;

public class OBJ_SniperRound extends Projectile {
    GamePanel gp;
    public OBJ_SniperRound(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name  = "SniperRound";
        maxLife = 80;
        life = maxLife;
        attack = 2;
        alive = false;
        solidArea = new Rectangle(gp.tileSize/8, gp.tileSize/8, (gp.tileSize*3)/4, (gp.tileSize*3)/4);
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

