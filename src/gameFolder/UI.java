package gameFolder;

import logic.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;


public class UI {

    GamePanel gp;
    Graphics2D g2;
    public Font cartoon, alagard;
    BufferedImage titleImage1,titleImage2,titleImage3,titleImage4,titleImage5,titleImage6,titleImage7,titleImage8,titleImage9;
    BufferedImage neutralElement, waterElement, fireElement, iceElement, earthElement, airElement, grassElement;
    public boolean messageOn = false;
    public boolean drawElm = false;
    int titleCounter;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Message> damageMessages = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;

    Color infoTextColor = new Color(217, 217, 217);
    int infoX, infoY;

    public UI(GamePanel gp) {
        this.gp = gp;

        getTitleImages();
        getElementImages();

        try{
            InputStream is = getClass().getResourceAsStream("/resources/fonts/SweetCherryFree.otf");
            cartoon = Font.createFont(Font.TRUETYPE_FONT,is);
        }catch (Exception e){

        }

    }

    void getTitleImages(){
        titleImage1 = setup("specialImages/waterfall1",gp.screenWidth,gp.screenHeight);
        titleImage2 = setup("specialImages/waterfall2",gp.screenWidth,gp.screenHeight);
        titleImage3 = setup("specialImages/waterfall3",gp.screenWidth,gp.screenHeight);
        titleImage4 = setup("specialImages/waterfall4",gp.screenWidth,gp.screenHeight);
        titleImage5 = setup("specialImages/waterfall5",gp.screenWidth,gp.screenHeight);
        titleImage6 = setup("specialImages/waterfall6",gp.screenWidth,gp.screenHeight);
        titleImage7 = setup("specialImages/waterfall7",gp.screenWidth,gp.screenHeight);
        titleImage8 = setup("specialImages/waterfall8",gp.screenWidth,gp.screenHeight);
        titleImage9 = setup("specialImages/titleScreen02",gp.screenWidth,gp.screenHeight);
    }

//    public void addMessage(String text){
//        message.add(text);
//        messageCounter.add(0);
//    }

    void getElementImages(){
        neutralElement = setup("elements/neutral",gp.tileSize/2,gp.tileSize/2);
        waterElement = setup("elements/water",gp.tileSize/2,gp.tileSize/2);
        fireElement = setup("elements/fire",gp.tileSize/2,gp.tileSize/2);
        iceElement = setup("elements/ice",gp.tileSize/2,gp.tileSize/2);
        earthElement = setup("elements/earth",gp.tileSize/2,gp.tileSize/2);
        airElement = setup("elements/air",gp.tileSize/2,gp.tileSize/2);
        grassElement = setup("elements/grass",gp.tileSize/2,gp.tileSize/2);

    }

    public void addDamageMessage(String text, int x, int y){
        Message tempMessage = new Message(text, x, y);
        damageMessages.add(tempMessage);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(alagard);
        g2.setColor(Color.WHITE);

        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        if(gp.gameState == gp.loadState){
            drawLoadScreen();
        }
        if(gp.gameState == gp.playState){
            drawUserInfo();
            drawTowerImages();
            updateDamageMessages();
            if(gp.showDamage){
                drawDamageMessage();
            }
            if(drawElm){
                drawElements();
            }

        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
        if(gp.gameState == gp.mapState){
            drawMapNames();
        }
        if(gp.gameState == gp.infoState){
            drawInfoScreen();
        }
    }

    public void drawInfoScreen(){
        BufferedImage tempImage = titleImage9;
        g2.drawImage(tempImage,0,0 ,null);
        infoY = gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,96F));
        String text = "This is my project game";
        drawText(text);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,42F));
        text = "Now you probably got here by clicking the info button,";
        drawText(text);
        text = "however menus are supposed to be navigated using";
        drawText(text);
        text = "'w' to go up, 's' to go down and 'enter' to select.";
        drawText(text);
        text = "Once in a game you can use number keys to select towers";
        drawText(text);
        text = "or you can click on the icon. There are many types of towers,";
        drawText(text);
        text = "however i will leave you to figure out what each one does.";
        drawText(text);
        text = "Once a tower is selected they are placed using the mouse.";
        drawText(text);
        text = "You can click on a placed tower to interact with it.";
        drawText(text);
        text = "Press 'space' to start a wave and i'll leave the rest up to you!";
        drawText(text);
        text = "It should be quite simple if you've played a TowerDefence before.";
        drawText(text);
        text = "However don't be afraid to ask me how to do something.";
        drawText(text);
        text = "The game is very much in beta but this is my CS project.";
        drawText(text);
    }

    void drawText(String text){
        infoX = getXForCentreText(text);
        infoY += gp.tileSize;
        g2.setColor(Color.gray);
        g2.drawString(text,infoX+1,infoY+1);
        g2.setColor(infoTextColor);
        g2.drawString(text,infoX,infoY);
    }

    public void drawUserInfo(){

        if (!gp.aSetter.waveLock){
            drawSaveButton();
        }

        g2.setColor(Color.WHITE);

        int messageX = gp.tileSize/2;
        int messageY = (gp.tileSize/4)*3;
        String text = "Life: " + gp.userLife;
        g2.setFont(g2.getFont().deriveFont(32F));
        g2.drawString(text,messageX,messageY);
        messageX += (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 10;
        text = "Coins: " + gp.userCurrency;
        g2.drawString(text,messageX,messageY);
        messageX += (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 10;
        text = "Wave: " + gp.waveNum;
        g2.drawString(text,messageX,messageY);
        messageX += (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 10;
        text = "No. Enemies: " + gp.remainingEnemies;
        g2.drawString(text,messageX,messageY);

        g2.setColor(new Color(100, 53, 25, 52));
        g2.fillRoundRect( (int) (gp.tileSize*10.75), 5,(int) (gp.tileSize*1.375) -10,gp.tileSize-10,25,25);

        g2.setColor(Color.WHITE);

        messageY = (gp.tileSize/2)-2;
        g2.setFont(g2.getFont().deriveFont(20F));
        text = "damage";
        messageX = (int) (gp.tileSize*10.75) + getXForCentreBoxText(text,(int) (gp.tileSize*1.375) -10);
        g2.drawString(text,messageX,messageY);
        messageY = (gp.tileSize/2)+20;
        text = "values";
        messageX = (int) (gp.tileSize*10.75) + getXForCentreBoxText(text,(int) (gp.tileSize*1.375) -10);
        g2.drawString(text,messageX,messageY);


        if(gp.interactTowerIndex<gp.tower.length){

            g2.setColor(new Color(100, 53, 25,52));
            g2.fillRoundRect((int) (gp.tileSize * 12.25)+5, 5,gp.tileSize*2 -10,gp.tileSize-10,25,25);
            g2.fillRoundRect((int) (gp.tileSize * 14.5)+5, 5,gp.tileSize*2 -10,gp.tileSize-10,25,25);
            g2.fillRoundRect((int) (gp.tileSize * 16.75)+5, 5,gp.tileSize*2 -10,gp.tileSize-10,25,25);
            g2.fillRoundRect(gp.tileSize * 19+5, 5,gp.tileSize -10,gp.tileSize-10,25,25);

            g2.setColor(Color.WHITE);

            messageY = (gp.tileSize/2)-2;
            g2.setFont(g2.getFont().deriveFont(24F));
            text = "upgrade 1";
            messageX = (int) (gp.tileSize * 12.25) + getXForCentreBoxText(text,gp.tileSize*2);
            g2.drawString(text,messageX,messageY);

            messageY = (gp.tileSize/2)+20;
            if(!gp.tower[gp.interactTowerIndex].upgrade1A){
                text = "cost: " + gp.tower[gp.interactTowerIndex].upgrade1Aprice;
            }else if(!gp.tower[gp.interactTowerIndex].upgrade1B){
                text = "cost: " + gp.tower[gp.interactTowerIndex].upgrade1Bprice;
            }else if(!gp.tower[gp.interactTowerIndex].upgrade1C){
                text = "cost: " + gp.tower[gp.interactTowerIndex].upgrade1Cprice;
            }else{
                text = "sold out!";
            }

            messageX = (int) (gp.tileSize * 12.25) + getXForCentreBoxText(text,gp.tileSize*2);
            g2.drawString(text,messageX,messageY);

            messageY = (gp.tileSize/2)-2;
            text = "upgrade 2";
            messageX = (int) (gp.tileSize * 14.5) + getXForCentreBoxText(text,gp.tileSize*2);
            g2.drawString(text,messageX,messageY);

            messageY = (gp.tileSize/2)+20;
            if(!gp.tower[gp.interactTowerIndex].upgrade2A){
                text = "cost: " + gp.tower[gp.interactTowerIndex].upgrade2Aprice;
            }else if(!gp.tower[gp.interactTowerIndex].upgrade2B){
                text = "cost: " + gp.tower[gp.interactTowerIndex].upgrade2Bprice;
            }else if(!gp.tower[gp.interactTowerIndex].upgrade2C){
                text = "cost: " + gp.tower[gp.interactTowerIndex].upgrade2Cprice;
            }else{
                text = "sold out!";
            }
            messageX = (int) (gp.tileSize * 14.5) + getXForCentreBoxText(text,gp.tileSize*2);
            g2.drawString(text,messageX,messageY);

            messageY = (gp.tileSize/2)-2;
            text = "targeting:";
            messageX = (int) (gp.tileSize * 16.75) + getXForCentreBoxText(text,gp.tileSize*2);
            g2.drawString(text,messageX,messageY);

            messageY = (gp.tileSize/2)+22;
            if(gp.tower[gp.interactTowerIndex].targetingType == 1)
                text = "Strongest";
            if(gp.tower[gp.interactTowerIndex].targetingType == 2)
                text = "Closest";
            if(gp.tower[gp.interactTowerIndex].targetingType == 3)
                text = "First";
            if(gp.tower[gp.interactTowerIndex].targetingType == 4)
                text = "Last";

            messageX = (int) (gp.tileSize * 16.75) + getXForCentreBoxText(text,gp.tileSize*2);
            g2.drawString(text,messageX,messageY);

            messageY = (gp.tileSize/4)*3-6;
            text = "Bin";
            messageX = (gp.tileSize * 19) + getXForCentreBoxText(text,gp.tileSize);
            g2.drawString(text,messageX,messageY);
        }


    }

    void drawElements(){
        int x1 = gp.elementButton1.getX();
        int y1 = gp.elementButton1.getY();
        int x2 = gp.elementButton2.getX();
        int y2 = gp.elementButton2.getY();
        int x3 = gp.elementButton3.getX();
        int y3 = gp.elementButton3.getY();

        switch (gp.tower[gp.interactTowerIndex].elementA){
            case 1:
                g2.drawImage(neutralElement,x1,y1,null);
                break;
            case 2:
                g2.drawImage(waterElement,x1,y1,null);
                break;
            case 3:
                g2.drawImage(fireElement,x1,y1,null);
                break;
            case 4:
                g2.drawImage(iceElement,x1,y1,null);
                break;
            case 5:
                g2.drawImage(earthElement,x1,y1,null);
                break;
            case 6:
                g2.drawImage(airElement,x1,y1,null);
                break;
            case 7:
                g2.drawImage(grassElement,x1,y1,null);
                break;
        }

        switch (gp.tower[gp.interactTowerIndex].elementB){
            case 1:
                g2.drawImage(neutralElement,x2,y2,null);
                break;
            case 2:
                g2.drawImage(waterElement,x2,y2,null);
                break;
            case 3:
                g2.drawImage(fireElement,x2,y2,null);
                break;
            case 4:
                g2.drawImage(iceElement,x2,y2,null);
                break;
            case 5:
                g2.drawImage(earthElement,x2,y2,null);
                break;
            case 6:
                g2.drawImage(airElement,x2,y2,null);
                break;
            case 7:
                g2.drawImage(grassElement,x2,y2,null);
                break;
        }

        switch (gp.tower[gp.interactTowerIndex].elementC){
            case 1:
                g2.drawImage(neutralElement,x3,y3,null);
                break;
            case 2:
                g2.drawImage(waterElement,x3,y3,null);
                break;
            case 3:
                g2.drawImage(fireElement,x3,y3,null);
                break;
            case 4:
                g2.drawImage(iceElement,x3,y3,null);
                break;
            case 5:
                g2.drawImage(earthElement,x3,y3,null);
                break;
            case 6:
                g2.drawImage(airElement,x3,y3,null);
                break;
            case 7:
                g2.drawImage(grassElement,x3,y3,null);
                break;
        }


    }

    public void updateDamageMessages(){
        for (int i = 0; i < damageMessages.size(); i ++) {
            if (damageMessages.get(i) != null){

                int counter = messageCounter.get(i) +1;

                messageCounter.set(i,counter);

                if(messageCounter.get(i) > 45){

                    damageMessages.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawDamageMessage(){

        int messageX;
        int messageY;
        String text;

        g2.setFont(g2.getFont().deriveFont(24F));

        for (int i = 0; i < damageMessages.size(); i ++) {
            if (damageMessages.get(i) != null){
                text = damageMessages.get(i).text;
                messageX = damageMessages.get(i).x;
                messageY = damageMessages.get(i).y;
                g2.setColor(Color.black);
                g2.drawString(damageMessages.get(i).text,messageX+2,messageY+2);
                g2.setColor(Color.RED);
                g2.drawString(damageMessages.get(i).text,messageX,messageY);

                int messageOffset = messageY - 1;

                Message tempMessage = new Message(text, messageX, messageOffset);
                damageMessages.set(i,tempMessage);

            }
        }
    }



    public void drawLoadScreen(){
        BufferedImage tempImage = backgroundAnimation();


        g2.drawImage(tempImage,0,0 ,null);
        g2.setFont(cartoon);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,96F));
        Color textColour = new Color(44, 42, 42);
        g2.setColor(textColour);
        String text = "Load Game Save";
        int x = getXForCentreText(text);
        int y = gp.tileSize*3;

        g2.setColor(Color.gray);
        g2.drawString(text,x+5,y+5);

        g2.setColor(textColour);
        g2.drawString(text,x,y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,64F));

        text = "You can select from your saved games";
        x = getXForCentreText(text);
        y += 2*gp.tileSize;

        g2.setColor(Color.gray);
        g2.drawString(text,x+2,y+2);

        g2.setColor(textColour);
        g2.drawString(text,x,y);

        for (int i = 0; i < gp.possibleGameSaves.size(); i++) {
            y = gp.tileSize*8;

            int k = commandNum/4 + 1;

            if(commandNum <= (4*k)){
                if(i >=4*(k-1) && i < 4*k){
                    text = "GAME " + (i+1) + ": " + gp.dba.returnGameInfo(gp.possibleGameSaves.get(i));
                    x = getXForCentreText(text);
                    y += gp.tileSize * (i-(4*(k-1)));

                    g2.setColor(Color.gray);
                    g2.drawString(text,x+2,y+2);

                    g2.setColor(textColour);
                    g2.drawString(text,x,y);
                    if (commandNum == i){
                        g2.setColor(Color.WHITE);
                        g2.drawString(">",x-gp.tileSize+2,y+2);

                        g2.setColor(textColour);
                        g2.drawString(">",x-gp.tileSize,y);
                    }
                }
            }
        }
    }

    private BufferedImage backgroundAnimation() {
        titleCounter++;
        BufferedImage tempImage = null;
        if (titleCounter<4){
            tempImage = titleImage1;
        }else if (titleCounter<8){
            tempImage = titleImage2;
        }else if (titleCounter<16){
            tempImage = titleImage3;
        }else if (titleCounter<20){
            tempImage = titleImage4;
        }else if (titleCounter<24){
            tempImage = titleImage5;
        }else if (titleCounter<28){
            tempImage = titleImage6;
        }else if (titleCounter<32){
            tempImage = titleImage7;
        }else if (titleCounter<36){
            tempImage = titleImage8;
        }if (titleCounter==35){
            titleCounter=0;
        }
        return tempImage;
    }

    public void drawTitleScreen(){
        BufferedImage tempImage = backgroundAnimation();

        g2.drawImage(tempImage,0,0 ,null);
        g2.setFont(cartoon);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,96F));
        Color myGrey = new Color(44, 42, 42);
        g2.setColor(myGrey);
        String text = "Title Scran";
        int x = getXForCentreText(text);
        int y = gp.tileSize*3;

        g2.setColor(Color.gray);
        g2.drawString(text,x+2,y+2);

        g2.setColor(myGrey);
        g2.drawString(text,x,y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,64F));

        text = "It's a game";
        x = getXForCentreText(text);
        y += 2*gp.tileSize;

        g2.setColor(Color.gray);
        g2.drawString(text,x+2,y+2);

        g2.setColor(myGrey);
        g2.drawString(text,x,y);

        //MENU
        text = "NEW GAME";
        x = getXForCentreText(text);
        y += 4* gp.tileSize;
        g2.setColor(Color.gray);
        g2.drawString(text,x+2,y+2);

        g2.setColor(myGrey);
        g2.drawString(text,x,y);
        if (commandNum == 0){
            g2.setColor(Color.gray);
            g2.drawString(">",x-gp.tileSize+2,y+2);

            g2.setColor(myGrey);
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "LOAD GAME";
        x = getXForCentreText(text);
        y += gp.tileSize;
        g2.setColor(Color.gray);
        g2.drawString(text,x+2,y+2);

        g2.setColor(myGrey);
        g2.drawString(text,x,y);
        if (commandNum == 1){
            g2.setColor(Color.gray);
            g2.drawString(">",x-gp.tileSize+2,y+2);

            g2.setColor(myGrey);
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "MAPS";
        x = getXForCentreText(text);
        y += gp.tileSize;
        g2.setColor(Color.gray);
        g2.drawString(text,x+2,y+2);

        g2.setColor(myGrey);
        g2.drawString(text,x,y);
        if (commandNum == 2){
            g2.setColor(Color.gray);
            g2.drawString(">",x-gp.tileSize+2,y+2);

            g2.setColor(myGrey);
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "INFO";
        x = getXForCentreText(text);
        y += gp.tileSize;
        g2.setColor(Color.gray);
        g2.drawString(text,x+2,y+2);

        g2.setColor(myGrey);
        g2.drawString(text,x,y);
        if (commandNum == 3){
            g2.setColor(Color.gray);
            g2.drawString(">",x-gp.tileSize+2,y+2);

            g2.setColor(myGrey);
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public void drawMapNames(){
        BufferedImage tempImage = backgroundAnimation();

        g2.drawImage(tempImage,0,0 ,null);
        g2.setFont(cartoon);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,72F));
        Color myGrey = new Color(44, 42, 42);
        g2.setColor(myGrey);

        String text = "Map1";
        int x = getXForCentreText(text);
        int y = gp.tileSize*5;
        //g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);
        if (commandNum == 0){
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "Map2";
        x = getXForCentreText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNum == 1){
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "Map3";
        x = getXForCentreText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNum == 2){
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "Map4";
        x = getXForCentreText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNum == 3){
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public void drawTowerImages(){

        int offset = gp.tileSize/13;

        int X = (gp.maxScreenCol*gp.tileSize) - (3*gp.tileSize) + 16, Y = 16;

        g2.setFont(g2.getFont().deriveFont(Float.parseFloat((gp.tileSize / 4) + "F")));
        g2.setColor(Color.WHITE);
//        g2.setStroke(new BasicStroke(5));
        for (int i = 1; i < 10; i++) {
            if(gp.towerOptions[i].buyPrice > gp.userCurrency) {
                g2.setColor(new Color(95, 90, 77));
                g2.fillRoundRect(X - offset, Y - offset, gp.tileSize + 2 * offset, gp.tileSize + 2 * offset, 10, 10);
            }else {
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(X-offset,Y-offset,gp.tileSize+2*offset,gp.tileSize+2*offset,10,10);
            }
            g2.drawImage(gp.towerOptions[i].image,X,Y,null);

            if(i == gp.selectedTowerIndex){
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(X-offset,Y-offset,gp.tileSize+2*offset,gp.tileSize+2*offset,10,10);
            }

            X += gp.tileSize + 2*offset;

            g2.setColor(Color.WHITE);
            Y += gp.tileSize/4;
            g2.drawString("Cost: " + gp.towerOptions[i].buyPrice,X,Y);
            Y += gp.tileSize/4;
            g2.drawString("Range: " + (gp.towerOptions[i].range/gp.tileSize),X,Y);
            Y += gp.tileSize/4;
            g2.drawString("Fire rate: " + (60/ gp.towerOptions[i].fireRate),X,Y);

            X -= (gp.tileSize + 2*offset);
            Y += gp.tileSize/2;
        }

        if(gp.towerOptions[0].buyPrice > gp.userCurrency) {
            g2.setColor(new Color(95, 90, 77));
            g2.fillRoundRect(X - offset, Y - offset, gp.tileSize + 2 * offset, gp.tileSize + 2 * offset, 10, 10);
        }else{
            g2.setColor(new Color(240,190,90));
            g2.fillRoundRect(X-offset,Y-offset,gp.tileSize+2*offset,gp.tileSize+2*offset,10,10);
        }

        g2.drawImage(gp.towerOptions[0].image,X,Y,null);

        if(gp.selectedTowerIndex == 0){
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(X-offset,Y-offset,gp.tileSize+2*offset,gp.tileSize+2*offset,10,10);
        }
        X += gp.tileSize + 2*offset;

        g2.setColor(Color.WHITE);
        Y += gp.tileSize/4;
        g2.drawString("Cost: " + gp.towerOptions[0].buyPrice,X,Y);
        Y += gp.tileSize/4;
        g2.drawString("Range: " + (gp.towerOptions[0].range/gp.tileSize),X,Y);
        Y += gp.tileSize/4;
        g2.drawString("Fire rate: " + (60/ gp.towerOptions[0].fireRate),X,Y);
    }

    public void drawPauseScreen(){
        g2.setFont(cartoon);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        g2.setColor(Color.WHITE);
        String text = "PAUSED";
        int x = getXForCentreText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen(){
        //window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth-gp.tileSize*4;
        int height = gp.tileSize*5;
        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y += gp.tileSize;
        }
    }

    public void drawSaveButton(){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2F));

        g2.setColor(new Color(100, 53, 25));
        g2.fillRoundRect(1296, 830, (int) (gp.tileSize*2.5), gp.tileSize,25,25);

//        g2.fillRoundRect((int) (gp.tileSize * 12.25)+5, 5,gp.tileSize*2 -10,gp.tileSize-10,25,25)

        g2.setColor(Color.WHITE);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));

        int messageY = 870;
        g2.setFont(g2.getFont().deriveFont(24F));
        String text = "Save Game";
        int messageX = 1296 + getXForCentreBoxText(text, (int) (gp.tileSize*2.5));
        g2.drawString(text,messageX,messageY);

//        (896,1296,tileSize*2,tileSize,"Save game")
    }

    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10,height-10,25,25);

    }

    public int getXForCentreText(String text){
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - textLength / 2;
    }

    public int getXForCentreBoxText(String text,int width){
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (width - textLength) / 2;
    }

    public int getXForAlignRightText(String text, int tailX){
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX-textLength;
        return x;
    }

    public BufferedImage setup(String imagePath,int width,int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/" + imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);


        } catch (Exception e) {
        }
        return image;
    }
}
