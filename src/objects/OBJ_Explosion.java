package objects;

import entities.Entity;
import gameFolder.GamePanel;

import java.awt.*;

public class OBJ_Explosion extends Entity {

    GamePanel gp;

    int explosionCounter = 0;

    public OBJ_Explosion(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name = "explosion";
        image = setup("animations/explosion01", (gp.tileSize*3), (gp.tileSize*3));
    }

    public void update(){

        explosionCounter++;
        if (explosionCounter== 1){
            //play sound
        }
        else if (explosionCounter== 3){
            getImage(2);
        }
        else if (explosionCounter== 6){
            getImage(3);
        }
        else if (explosionCounter== 9){
            getImage(4);
        }
        else if (explosionCounter== 12){
            getImage(5);
        }
        else if (explosionCounter== 15){
            getImage(6);
        }
        else if (explosionCounter== 18){
            getImage(7);
        }
        else if (explosionCounter== 21){
            getImage(8);
        }
        else if (explosionCounter== 24){
            actionFinished = true;
        }
    }

    public void getImage(int imageID){
        if (imageID == 1){
            image = setup("animations/explosion01", (gp.tileSize*3), (gp.tileSize*3));
        }else if (imageID == 2){
            image = setup("animations/explosion02", (gp.tileSize*3), (gp.tileSize*3));
        }else if (imageID == 3){
            image = setup("animations/explosion03", (gp.tileSize*3), (gp.tileSize*3));
        }else if (imageID == 4){
            image = setup("animations/explosion04", (gp.tileSize*3), (gp.tileSize*3));
        }else if (imageID == 5){
            image = setup("animations/explosion05", (gp.tileSize*3), (gp.tileSize*3));
        }else if (imageID == 6){
            image = setup("animations/explosion06", (gp.tileSize*3), (gp.tileSize*3));
        }else if (imageID == 7){
            image = setup("animations/explosion07", (gp.tileSize*3), (gp.tileSize*3));
        }else if (imageID == 8){
            image = setup("animations/explosion08", (gp.tileSize*3), (gp.tileSize*3));
        }

    }

    public void draw(Graphics2D g2){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.9f));

        g2.drawImage(image, worldX, worldY,null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

    }

}
