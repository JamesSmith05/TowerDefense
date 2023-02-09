package logic;

import enemies.*;
import entities.Entity;
import gameFolder.GamePanel;
import towers.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AssetSetter {

    GamePanel gp;

    public int enemyTotalCounter;
    public int enemyId;
    public int j;
    public int k;
    public Boolean waveLock = false;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
        resetMobCounter();
        resetTowerCounter();
    }

    public void loadTowerFromSave(int x,int y,String towerName, int elementID,boolean a,boolean b,boolean c,boolean d,boolean e,boolean f,int towerWorth){
        if(towerName.equals("FlameTower")){
            gp.tower[j] = new FlameTower(gp);
        }else if(towerName.equals("BombTower")){
            gp.tower[j] = new BombTower(gp);
        }else if(towerName.equals("IceTower")){
            gp.tower[j] = new IceTower(gp);
        }else if(towerName.equals("MachineGunTower")){
            gp.tower[j] = new MachineGunTower(gp);
        }else if(towerName.equals("PlainTower")){
            gp.tower[j] = new PlainTower(gp);
        }else if(towerName.equals("SniperTower")){
            gp.tower[j] = new SniperTower(gp);
        }else if(towerName.equals("TackTower")){
            gp.tower[j] = new TackTower(gp);
        }
        gp.tower[j].worldX = x;
        gp.tower[j].worldY = y;
        gp.tower[j].upgrade1A = a;
        gp.tower[j].upgrade1B = b;
        gp.tower[j].upgrade1C = c;
        gp.tower[j].upgrade2A = d;
        gp.tower[j].upgrade2B = e;
        gp.tower[j].upgrade2C = f;
        gp.tower[j].towerWorth = towerWorth;
        j++;
    }

    public void setTower(int x, int y, int towerIndex){
        if(gp.userCurrency >= gp.towerOptions[towerIndex].buyPrice){

            if(towerIndex == 0 ){
                gp.tower[j] = new IceTower(gp);
            }else if(towerIndex == 1 ){
                gp.tower[j] = new PlainTower(gp);
            }else if(towerIndex == 2 ){
                gp.tower[j] = new SniperTower(gp);
            }else if(towerIndex == 3 ){
                gp.tower[j] = new BombTower(gp);
            }else if(towerIndex == 4 ){
                gp.tower[j] = new FlameTower(gp);
            }else if(towerIndex == 5 ){
                gp.tower[j] = new IceTower(gp);
            }else if(towerIndex == 6 ){
                gp.tower[j] = new TackTower(gp);
            }else if(towerIndex == 7 ){
                gp.tower[j] = new MachineGunTower(gp);
            }else if(towerIndex == 8 ){
                gp.tower[j] = new IceTower(gp);
            }else if(towerIndex == 9){
                gp.tower[j] = new IceTower(gp);
            }
            gp.tower[j].worldX = x;
            gp.tower[j].worldY = y;
            gp.tower[j].towerWorth += gp.towerOptions[towerIndex].buyPrice;
            gp.userCurrency -=gp.towerOptions[towerIndex].buyPrice;
            j++;
        }
    }

    public void setSpecificEnemy(String name,int x, int y){
        switch (name){
            case "Helmet":
                gp.monster[enemyId] = new MON_Helmet(gp);
        }

        int waveNum = gp.waveNum;

        if(3 < waveNum && waveNum <=6){
            gp.monster[enemyId].originalSpeed +=1;
            gp.monster[enemyId].speed = gp.monster[enemyId].originalSpeed;
            gp.monster[enemyId].maxLife +=2;
            gp.monster[enemyId].life = gp.monster[enemyId].maxLife;
        }
        else if(6 < waveNum && waveNum <=8){
            gp.monster[enemyId].originalSpeed +=1;
            gp.monster[enemyId].speed = gp.monster[enemyId].originalSpeed;
            gp.monster[enemyId].maxLife +=6;
            gp.monster[enemyId].life = gp.monster[enemyId].maxLife;
        }
        else if(8 < waveNum && waveNum <=10){
            gp.monster[enemyId].originalSpeed +=2;
            gp.monster[enemyId].speed = gp.monster[enemyId].originalSpeed;
            gp.monster[enemyId].maxLife +=12;
            gp.monster[enemyId].life = gp.monster[enemyId].maxLife;
        }
        else if (waveNum > 10){
            gp.monster[enemyId].originalSpeed +=2;
            gp.monster[enemyId].speed = gp.monster[enemyId].originalSpeed;
            gp.monster[enemyId].maxLife += (waveNum*2);
            gp.monster[enemyId].life = gp.monster[enemyId].maxLife;
        }

        gp.monster[enemyId].worldX = x;
        gp.monster[enemyId].worldY = y;

        enemyId++;
    }

    public void setEnemy(int waveNum){
        Random rand = new Random();
        int temp = rand.nextInt(15);
        if (temp == 0 || temp == 3){
            gp.monster[enemyId] = new MON_Wraith(gp);
        }
        else if (temp == 2 || temp == 1 || temp == 4){
            gp.monster[enemyId] = new MON_Bat(gp);
        }
        else if (temp == 5){
            gp.monster[enemyId] = new MON_Undead(gp);
        }
        else if (temp > 10 ){
            gp.monster[enemyId] = new MON_PinkSlime(gp);
        }
        else{
            gp.monster[enemyId] = new MON_GreenSlime(gp);
        }

        if(3 < waveNum && waveNum <=6){
            gp.monster[enemyId].originalSpeed +=1;
            gp.monster[enemyId].speed = gp.monster[enemyId].originalSpeed;
            gp.monster[enemyId].maxLife +=2;
            gp.monster[enemyId].life = gp.monster[enemyId].maxLife;
        }
        else if(6 < waveNum && waveNum <=8){
            gp.monster[enemyId].originalSpeed +=1;
            gp.monster[enemyId].speed = gp.monster[enemyId].originalSpeed;
            gp.monster[enemyId].maxLife +=6;
            gp.monster[enemyId].life = gp.monster[enemyId].maxLife;
        }
        else if(8 < waveNum && waveNum <=10){
            gp.monster[enemyId].originalSpeed +=2;
            gp.monster[enemyId].speed = gp.monster[enemyId].originalSpeed;
            gp.monster[enemyId].maxLife +=12;
            gp.monster[enemyId].life = gp.monster[enemyId].maxLife;
        }
        else if (waveNum > 10){
            gp.monster[enemyId].originalSpeed +=2;
            gp.monster[enemyId].speed = gp.monster[enemyId].originalSpeed;
            gp.monster[enemyId].maxLife += (waveNum*2);
            gp.monster[enemyId].life = gp.monster[enemyId].maxLife;
        }

        gp.monster[enemyId].worldX = gp.tileSize*gp.startCol;
        gp.monster[enemyId].worldY = gp.tileSize*gp.startRow;

        enemyId++;
    }

    public void waveSpawner(int waveNum){

        if(waveNum == 0){

        }
        if (waveNum > 0 && waveNum <= 5){
            if(k < waveNum*2+8) {
                waveLock = true;
                if(enemyId >= gp.monster.length){
                    resetMobCounter();
                }
                setEnemy(waveNum);
                k++;

            }else{
                waveLock = false;
            }
        }
        if (waveNum > 5 && waveNum <= 10){
            if(k < waveNum*3+8) {
                waveLock = true;
                if(enemyId >= gp.monster.length){
                    resetMobCounter();
                }
                setEnemy(waveNum);
                k++;

            }else{
                waveLock = false;
            }
        }
        if (waveNum > 10){
            if(k < waveNum+5) {
                waveLock = true;
                if(enemyId >= gp.monster.length){
                    resetMobCounter();
                }
                setEnemy(waveNum);
                k++;

            }else{
                waveLock = false;
            }
        }

    }

    public void resetMobCounter(){enemyId = 0;}
    public void resetTowerCounter(){j = 0;}
    public void setTowerOptions(){
        gp.towerOptions[0] = new IceTower(gp);
        gp.towerOptions[1] = new PlainTower(gp);
        gp.towerOptions[2] = new SniperTower(gp);
        gp.towerOptions[3] = new BombTower(gp);
        gp.towerOptions[4] = new FlameTower(gp);
        gp.towerOptions[5] = new IceTower(gp);
        gp.towerOptions[6] = new TackTower(gp);
        gp.towerOptions[7] = new MachineGunTower(gp);
        gp.towerOptions[8] = new IceTower(gp);
        gp.towerOptions[9] = new IceTower(gp);
    }
}
