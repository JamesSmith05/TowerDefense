package objects;

import entities.Entity;
import gameFolder.GamePanel;

import java.awt.*;

public class OBJ_UpgradeEffect extends Entity {

    GamePanel gp;

    int spriteCounter = 0;

    public OBJ_UpgradeEffect(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name = "upgradeEffect";
        image = setup("animations/upgrade1", gp.tileSize, gp.tileSize);
    }

    public void update(){

        spriteCounter++;
        if (spriteCounter== 1){
            //play sound
        }
        else if (spriteCounter== 3){
            getImage(2);
        }
        else if (spriteCounter== 6){
            getImage(3);
        }
        else if (spriteCounter== 9){
            getImage(4);
        }
        else if (spriteCounter== 12){
            actionFinished = true;
        }
    }

    public void getImage(int imageID){
        if (imageID == 1){
            image = setup("animations/upgrade1", gp.tileSize, gp.tileSize);
        }else if (imageID == 2){
            image = setup("animations/upgrade2", gp.tileSize, gp.tileSize);
        }else if (imageID == 3){
            image = setup("animations/upgrade3", gp.tileSize, gp.tileSize);
        }else if (imageID == 4){
            image = setup("animations/upgrade4", gp.tileSize, gp.tileSize);
        }

    }

    public void draw(Graphics2D g2){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.9f));

        g2.drawImage(image, worldX, worldY,null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

    }

}
