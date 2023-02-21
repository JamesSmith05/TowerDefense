package gameFolder;

import AI.PathFinder;
import database.DBaccess;
import entities.Entity;
import logic.*;
import objects.OBJ_UpgradeEffect;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, ActionListener {

    public String username;

    final int originalTileSize = 64;
    final int scale = 1;
    public int mouseX = 0, mouseY = 0;

    public boolean leftClick = false;
    public boolean rightClick = false;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 23; // 20 map tiles plus 3 tower tiles
    public final int maxScreenRow = 15; // 14 map tiles + info tiles
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //fps
    int FPS = 60;

    //buttons
    ButtonTemplate hitMarkerButton = new ButtonTemplate((int) (tileSize*10.75), 5,(int) (tileSize*1.375) -10,tileSize-10,"hit");
    ButtonTemplate upgradeButton1 = new ButtonTemplate((int) (tileSize * 12.25)+5, 5,tileSize*2 -10,tileSize-10,"upgrade 1");
    ButtonTemplate upgradeButton2 =  new ButtonTemplate((int) (tileSize * 14.5)+5, 5,tileSize*2 -10,tileSize-10,"upgrade 2");
    ButtonTemplate targetingButton = new ButtonTemplate((int) (tileSize * 16.75)+5, 5,tileSize*2 -10,tileSize-10,"targeting");
    ButtonTemplate deleteButton = new ButtonTemplate(tileSize * 19+5, 5,tileSize -10,tileSize-10,"delete");
    ButtonTemplate elementButton1 = new ButtonTemplate(0, 0,tileSize/2,tileSize/2,"button");
    ButtonTemplate elementButton2 = new ButtonTemplate(0, 0,tileSize/2,tileSize/2,"button");
    ButtonTemplate elementButton3 = new ButtonTemplate(0, 0,tileSize/2,tileSize/2,"button");
    int tempButtonX = ((maxScreenCol*tileSize) - (3*tileSize) + 16);
    int tempButtonY = 16;
    int tempButtonChange = tileSize*5/4;
    ButtonTemplate towerSelect1 = new ButtonTemplate(tempButtonX,tempButtonY,tileSize,tileSize,"TowerSelect");
    ButtonTemplate towerSelect2 = new ButtonTemplate(tempButtonX,tempButtonY+tempButtonChange,tileSize,tileSize,"TowerSelect");
    ButtonTemplate towerSelect3 = new ButtonTemplate(tempButtonX,tempButtonY+tempButtonChange*2,tileSize,tileSize,"TowerSelect");
    ButtonTemplate towerSelect4 = new ButtonTemplate(tempButtonX,tempButtonY+tempButtonChange*3,tileSize,tileSize,"TowerSelect");
    ButtonTemplate towerSelect5 = new ButtonTemplate(tempButtonX,tempButtonY+tempButtonChange*4,tileSize,tileSize,"TowerSelect");
    ButtonTemplate towerSelect6 = new ButtonTemplate(tempButtonX,tempButtonY+tempButtonChange*5,tileSize,tileSize,"TowerSelect");
    ButtonTemplate towerSelect7 = new ButtonTemplate(tempButtonX,tempButtonY+tempButtonChange*6,tileSize,tileSize,"TowerSelect");
    ButtonTemplate towerSelect8 = new ButtonTemplate(tempButtonX,tempButtonY+tempButtonChange*7,tileSize,tileSize,"TowerSelect");
    ButtonTemplate towerSelect9 = new ButtonTemplate(tempButtonX,tempButtonY+tempButtonChange*8,tileSize,tileSize,"TowerSelect");
    ButtonTemplate towerSelect0 = new ButtonTemplate(tempButtonX,tempButtonY+tempButtonChange*9,tileSize,tileSize,"TowerSelect");
    ButtonTemplate saveButton = new ButtonTemplate(1296, 830, (int) (tileSize*2.5), tileSize,"Save game");
    ButtonTemplate infoButton = new ButtonTemplate( 671,724,130,50,"InfoButton");

    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CheckMouse keyM = new CheckMouse(this);
    public PathFinder pFinder = new PathFinder(this);
    Sound music = new Sound();
    Sound sEffect = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);

    public UI ui = new UI(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public Thread gameThread;

    //ENTITY AND OBJECT
    public Entity[] tower = new Entity[100];
    public Entity[] towerOptions = new Entity[10];
    public Entity[] obj = new Entity[50];  //increase number to increase max number of object on screen
    public Entity[] monster = new Entity[150];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int mapState = 4;
    public final int infoState = 5;
    public final int loadState = 6;
    public Rectangle mouseSolidArea = new Rectangle(0, 0, tileSize, tileSize);
    public Rectangle mouseSolidArea2 = new Rectangle((tileSize/2)-1,(tileSize/2)-1,2,2);

    public int spawnerCounter = 0;
    public int userLife;
    public int userCurrency;
    public int waveNum;
    public int startCol, startRow;
    public int goalCol, goalRow;

    public int selectedTowerIndex = 50;
    public int interactTowerIndex = 1000;

    public int remainingEnemies;

    public JFrame frame;

    public DBaccess dba = new DBaccess();

    public boolean showDamage = false;
    public int mapID = 1;
    public int loadedGameID = -1;
    public ArrayList<Integer> possibleGameSaves;

    public GamePanel(String username) {

        this.username = username;

        //setup Jframe
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyM);
        this.setFocusable(true);

        possibleGameSaves = dba.gamesForUsername(username);

        //button setup
        upgradeButton1.addActionListener(this);
        upgradeButton2.addActionListener(this);
        targetingButton.addActionListener(this);
        deleteButton.addActionListener(this);
        towerSelect1.addActionListener(this);
        towerSelect2.addActionListener(this);
        towerSelect3.addActionListener(this);
        towerSelect4.addActionListener(this);
        towerSelect5.addActionListener(this);
        towerSelect6.addActionListener(this);
        towerSelect7.addActionListener(this);
        towerSelect8.addActionListener(this);
        towerSelect9.addActionListener(this);
        towerSelect0.addActionListener(this);
        infoButton.addActionListener(this);
        saveButton.addActionListener(this);
        hitMarkerButton.addActionListener(this);
        elementButton1.addActionListener(this);
        elementButton2.addActionListener(this);
        elementButton3.addActionListener(this);

    }

    public void addTowerButtons() {
        this.add(upgradeButton1);
        this.add(upgradeButton2);
        this.add(deleteButton);
        this.add(targetingButton);
        updateTowerElements();
        this.add(elementButton1);
        this.add(elementButton2);
        this.add(elementButton3);
    }

    public void updateTowerElements(){
        //updates location of the 3 buttons used for tower elements so that they appear above the selected tower
        int updateX = tower[interactTowerIndex].worldX + tileSize/4;
        int updateY = tower[interactTowerIndex].worldY - 3*tileSize/4;
        elementButton1.updateLocation(updateX, updateY);
        updateY += tileSize/8;
        //if tower is to close to the left shift on button to the right so that they are all accessible
        if(tower[interactTowerIndex].worldX < (3*tileSize/4)){
            updateX += 3*tileSize/4;
            elementButton2.updateLocation(updateX, updateY);
            updateX += tileSize/8;
            updateY += 3*tileSize/4;
            elementButton3.updateLocation(updateX, updateY);
        }
        //if tower is to close to the right shift on button to the left so that they are all accessible
        else if(tower[interactTowerIndex].worldX > (screenWidth - 4.5*tileSize)){
            updateX -= 3*tileSize/4;
            elementButton2.updateLocation(updateX, updateY);
            updateX -= tileSize/8;
            updateY += 3*tileSize/4;
            elementButton3.updateLocation(updateX, updateY);
        }
        //otherwise the buttons are above the tower as normal
        else{
            updateX += 3*tileSize/4;
            updateY += tileSize/8;
            elementButton2.updateLocation(updateX, updateY);
            updateX -= 6*tileSize/4;
            elementButton3.updateLocation(updateX, updateY);
        }

    }

    public void removeTowerButtons() {
        this.remove(upgradeButton1);
        this.remove(upgradeButton2);
        this.remove(deleteButton);
        this.remove(targetingButton);
        this.remove(elementButton1);
        this.remove(elementButton2);
        this.remove(elementButton3);
    }

    public void addSelectTowers(){
        this.add(towerSelect1);
        this.add(towerSelect2);
        this.add(towerSelect3);
        this.add(towerSelect4);
        this.add(towerSelect5);
        this.add(towerSelect6);
        this.add(towerSelect7);
        this.add(towerSelect8);
        this.add(towerSelect9);
        this.add(towerSelect0);
        this.add(saveButton);
        this.add(hitMarkerButton);
    }

    public void setupGame(){
        //reset all game variables and arrays and send user to title screen
        resetEntities();
        stopMusic();
        aSetter.resetMobCounter();
        aSetter.resetTowerCounter();
        aSetter.setTowerOptions();
        userLife = 50;
        userCurrency = 1000;
        waveNum = 0;
        playMusic(0);
        gameState = titleState;
        this.add(infoButton);
    }

    public void resetEntities(){
        Arrays.fill(tower, null);
        Arrays.fill(monster, null);
        Arrays.fill(obj, null);

        if (projectileList.size() > 0) {
            projectileList.subList(0, projectileList.size()).clear();
        }
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; //0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta>=1) {
                // update information e.g. positions
                update();
                // draw the screen with updated information
                repaint();
                delta--;
                drawCount++;
            }

            if(timer>=1000000000){
                drawCount = 0;
                timer = 0;
            }


        }

    }

    public void loadGameSave(int gameToLoad){ //load a game
        dba.loadGameData(gameToLoad,this);
        waveNum --;
        aSetter.k = 10000;
    }

    public void checkForTowerOverflow(){
        //since the array has a limited size the game can be crashed by repeatedly placing and deleting towers to reach the array limit of 100 when new towers are added as the adding uses an incremental value.
        //This takes all non-null towers and rearranges them to the lowest possible values in the array and resets the incremental value to the new smallest null array slot when it gets close to the limit as to not reduce performance
        if(aSetter.j >=80){ //triggers once the entry into the array is 80/100, it is not possible to fit 80 towers on the map so once this is triggered it will not be triggered again for a while
            int value = 0;
            for (int i = 0; i < tower.length; i++) {
                if(tower[i] != null){ //finds first not null entry in the tower array and moves it to the front and increments value
                    tower[value] = tower[i];
                    value++;
                }
            }
            for (int i = value; i < tower.length; i++) { //to remove duplicates every value that is after the final entry is set to null
                tower[i] = null;
            }
            aSetter.j = value; //the entry value of the separate class is then updated with the first non-null entry number of the array
        }
    }

    public void update() {

        //get information bout the frame and its location
        frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        int tempX = frame.getLocation().x;
        int tempY = frame.getLocation().y;
        int tempMouseX = MouseInfo.getPointerInfo().getLocation().x;
        int tempMouseY = MouseInfo.getPointerInfo().getLocation().y;

//        mouseX = tempMouseX - tempX - 10;  //these values are used for a 4k monitor
//        mouseY = tempMouseY - tempY - 45;  //these values are used for a 4k monitor

        //to try and put the coordinates to the tip of the mouse button
        mouseX = tempMouseX - tempX -5;
        mouseY = tempMouseY - tempY - 22;

        if(gameState == playState){

            if(userLife<=0){
                setupGame();
            }

            if (leftClick ){
                if(selectedTowerIndex>towerOptions.length){
                    if(cChecker.checkEntityMouse((mouseX - (tileSize / 2)), (mouseY - (tileSize / 2)), mouseSolidArea2, tower, true)){
                        addTowerButtons();
                        ui.drawElm = true;
                    }
                    else{
                        interactTowerIndex = 1000;
                        removeTowerButtons();
                        ui.drawElm = false;
                    }
                }
                if(selectedTowerIndex<towerOptions.length && userCurrency>0){ // currency check is irrelevant
                    if (!cChecker.checkMouseTile((mouseX - (tileSize / 2)), (mouseY - (tileSize / 2)), mouseSolidArea)){
                        if(!cChecker.checkEntityMouse((mouseX - (tileSize / 2)), (mouseY - (tileSize / 2)), mouseSolidArea, tower, false)){
                            aSetter.setTower((mouseX - (tileSize/2)),(mouseY - (tileSize/2)), selectedTowerIndex);
                            checkForTowerOverflow();
                            selectedTowerIndex = 50;
                        }
                    }
                }

            }
            if (rightClick){
                selectedTowerIndex = 50;
            }
            spawnerCounter++;
            Random rand = new Random();
            if (spawnerCounter > rand.nextInt(25)+10) {
                aSetter.waveSpawner(waveNum);
                spawnerCounter = 0;
                if(keyH.spacePressed){
                    if(!aSetter.waveLock){
                        waveNum ++;
                        aSetter.k = 0;
                    }
                    keyH.spacePressed = false;
                }
            }
            for (Entity entity : tower) {
                if (entity != null) {
                    entity.update();
                }
            }
            for (Entity entity : obj) {
                if (entity != null) {
                    entity.update();
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    if(monster[i].alive && !monster[i].dying){
                        monster[i].update();
                    }if(!monster[i].alive){
                        monster[i] = null;
                    }

                }
            }

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null && obj[i].actionFinished){
                    obj[i] = null;
                }
            }

            for (int i = 0; i < projectileList.size(); i++) { //loops for size of arraylist
                if(projectileList.get(i) != null) { //null check, it shouldn't be possible for a null entry, but to protect from crashes it is included
                    if(projectileList.get(i).alive){ //if the projectile is still alive it will update
                        projectileList.get(i).update(); //update command for projectile, check if collided with enemy, if not updates position
                    }if(!projectileList.get(i).alive){ //if the projectiles' lifetime expires
                        projectileList.remove(i); //the projectile is removed
                    }

                }
            }
        }
        leftClick = false;  //reset value for checking mouse clicks
        rightClick = false;  //reset value for checking mouse clicks
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState || gameState == mapState || gameState == infoState || gameState == loadState) {
            ui.draw(g2);

        } else {

            remainingEnemies = 0;

            //drawing the background tiles
            tileM.draw(g2);

            //add entity to list
            for (Entity element : tower) {
                if (element != null) {
                    entityList.add(element);
                }
            }
            for (Entity entity : monster) {
                if (entity != null) {
                    entityList.add(entity);
                    remainingEnemies +=1;
                }
            }
            //SORT in order of y position so that entities closer to the bottom of the screen are drawn on top
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            for (Entity entity : projectileList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity value : obj) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            //Draw entities
            for (Entity entity : entityList) {
                entity.draw(g2);
            }
            entityList.clear();

            ui.draw(g2);

            g2.setColor(new Color(255,0,0,30));

            if(interactTowerIndex<tower.length){  //draw range circle for tower that has been selected
                g2.fillOval((tower[interactTowerIndex].worldX +tileSize/2 - tower[interactTowerIndex].range), (tower[interactTowerIndex].worldY +tileSize/2 - tower[interactTowerIndex].range), (tower[interactTowerIndex].range)*2, (tower[interactTowerIndex].range)*2);
            }
            if(selectedTowerIndex<towerOptions.length){ //draw the range and tower that is going to be placed
                g2.fillOval((mouseX - towerOptions[selectedTowerIndex].range), (mouseY - towerOptions[selectedTowerIndex].range), (towerOptions[selectedTowerIndex].range)*2, (towerOptions[selectedTowerIndex].range)*2);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3F));
                g2.drawImage(towerOptions[selectedTowerIndex].image, (mouseX - (tileSize/2)), (mouseY - (tileSize/2)),null);
            }
        }
        g2.dispose();

    }

    public void playMusic ( int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic () {
        music.stop();
    }
    public void playSE ( int i){
        sEffect.setFile(i);
        sEffect.play();
    }

    void addUpgradeEffect(int x,int y){ //adds the little sparkle effect to the list of objects when upgrading a tower
        for (int j = 0; j < obj.length; j++) {
            if (obj[j] == null) {
                obj[j] = new OBJ_UpgradeEffect(this);
                obj[j].worldX = x;
                obj[j].worldY = y;
                j = obj.length;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { //handles all possible button interactions for selected tower

        if (e.getSource() == upgradeButton1) { //for when the left upgrade button if pressed,
            if (tower[interactTowerIndex] != null && !tower[interactTowerIndex].upgrade1A && userCurrency>= tower[interactTowerIndex].upgrade1Aprice){ //checks if the first of the left path isn't purchased and the user has sufficient currency
                tower[interactTowerIndex].upgrade1A = true; //sets the flag for the first upgrade in path 1 to be true
                tower[interactTowerIndex].setUpgrade1A(); //runs the tower specific code to determine what the upgrade does
                userCurrency -= tower[interactTowerIndex].upgrade1Aprice; //subtracts the price of the upgrade from the user
                tower[interactTowerIndex].towerWorth += tower[interactTowerIndex].upgrade1Aprice; //increases the value of the tower by the value of the upgrade
                addUpgradeEffect(tower[interactTowerIndex].worldX,tower[interactTowerIndex].worldY); //adds the little upgrade effect
            }
            else if (tower[interactTowerIndex] != null && tower[interactTowerIndex].upgrade1A &&  !tower[interactTowerIndex].upgrade1B && userCurrency>= tower[interactTowerIndex].upgrade1Bprice){ //checks if the first of the left path is purchased and the user has sufficient currency
                tower[interactTowerIndex].upgrade1B = true; //same as above but for upgrade 2 in path 1
                tower[interactTowerIndex].setUpgrade1B(); //same as above but for upgrade 2 in path 1
                userCurrency -= tower[interactTowerIndex].upgrade1Bprice; //same as above but for upgrade 2 in path 1
                tower[interactTowerIndex].towerWorth += tower[interactTowerIndex].upgrade1Bprice; //same as above but for upgrade 2 in path 1
                addUpgradeEffect(tower[interactTowerIndex].worldX,tower[interactTowerIndex].worldY); //same as above but for upgrade 2 in path 1
            }
            else if (tower[interactTowerIndex] != null && tower[interactTowerIndex].upgrade1A &&  tower[interactTowerIndex].upgrade1B && !tower[interactTowerIndex].upgrade1C &&  userCurrency>= tower[interactTowerIndex].upgrade1Cprice){//checks if the first and second of the left path is purchased and the user has sufficient currency
                tower[interactTowerIndex].upgrade1C = true; //same as above but for upgrade 3 in path 1
                tower[interactTowerIndex].setUpgrade1C(); //same as above but for upgrade 3 in path 1
                userCurrency -= tower[interactTowerIndex].upgrade1Cprice; //same as above but for upgrade 3 in path 1
                tower[interactTowerIndex].towerWorth += tower[interactTowerIndex].upgrade1Cprice; //same as above but for upgrade 3 in path 1
                addUpgradeEffect(tower[interactTowerIndex].worldX,tower[interactTowerIndex].worldY); //same as above but for upgrade 3 in path 1
            }
        }
        if (e.getSource() == upgradeButton2) { //for when the left upgrade button if pressed,
            System.out.println("upgrade button 2");
            if (tower[interactTowerIndex] != null && !tower[interactTowerIndex].upgrade2A && userCurrency >= tower[interactTowerIndex].upgrade2Aprice){ //same as above but path 2
                tower[interactTowerIndex].upgrade2A = true; //same as above but path 2
                tower[interactTowerIndex].setUpgrade2A(); //same as above but path 2
                userCurrency -= tower[interactTowerIndex].upgrade2Aprice; //same as above but path 2
                tower[interactTowerIndex].towerWorth += tower[interactTowerIndex].upgrade2Aprice; //same as above but path 2
                addUpgradeEffect(tower[interactTowerIndex].worldX,tower[interactTowerIndex].worldY); //same as above but path 2
            }
            else if (tower[interactTowerIndex] != null && tower[interactTowerIndex].upgrade2A &&  !tower[interactTowerIndex].upgrade2B && userCurrency>= tower[interactTowerIndex].upgrade2Bprice){ //same as above but path 2
                tower[interactTowerIndex].upgrade2B = true; //same as above but path 2
                tower[interactTowerIndex].setUpgrade2B(); //same as above but path 2
                userCurrency -= tower[interactTowerIndex].upgrade2Bprice; //same as above but path 2
                tower[interactTowerIndex].towerWorth += tower[interactTowerIndex].upgrade2Bprice; //same as above but path 2
                addUpgradeEffect(tower[interactTowerIndex].worldX,tower[interactTowerIndex].worldY); //same as above but path 2
            }
            else if (tower[interactTowerIndex] != null && tower[interactTowerIndex].upgrade2A &&  tower[interactTowerIndex].upgrade2B && !tower[interactTowerIndex].upgrade2C &&  userCurrency>= tower[interactTowerIndex].upgrade2Cprice){ //same as above but path 2
                tower[interactTowerIndex].upgrade2C = true; //same as above but path 2
                tower[interactTowerIndex].setUpgrade2C(); //same as above but path 2
                userCurrency -= tower[interactTowerIndex].upgrade2Cprice; //same as above but path 2
                tower[interactTowerIndex].towerWorth += tower[interactTowerIndex].upgrade2Cprice; //same as above but path 2
                addUpgradeEffect(tower[interactTowerIndex].worldX,tower[interactTowerIndex].worldY); //same as above but path 2
            }
        }
        if (e.getSource() == targetingButton) { //changes the targeting type of selected tower
            if (tower[interactTowerIndex] != null){
                if(tower[interactTowerIndex].targetingType<4){ //increments the variable for targeting type
                    tower[interactTowerIndex].targetingType +=1;
                }else if (tower[interactTowerIndex].targetingType == 4){ //loops back so button cycles through
                    tower[interactTowerIndex].targetingType = 1;
                }
            }
        }
        if (e.getSource() == deleteButton) { //sells selected tower and returns portion of the value to the user
            if (tower[interactTowerIndex] != null){
                userCurrency += tower[interactTowerIndex].towerWorth/2;
                tower[interactTowerIndex] = null;
                interactTowerIndex = 1000; //resets the value out of the range of the array [tower]
                removeTowerButtons(); //hides the buttons when a tower is not selected
                ui.drawElm = false; //hides the element when a tower is not selected
            }
        }
        if(e.getSource() == towerSelect1){ //for tower images on the right of the screen when pressed there is a hidden button that selects the tower image the user presses
            selectedTowerIndex = 1;
        }
        if(e.getSource() == towerSelect2){ //as above
            selectedTowerIndex = 2;
        }
        if(e.getSource() == towerSelect3){ //as above
            selectedTowerIndex = 3;
        }
        if(e.getSource() == towerSelect4){ //as above
            selectedTowerIndex = 4;
        }
        if(e.getSource() == towerSelect5){ //as above
            selectedTowerIndex = 5;
        }
        if(e.getSource() == towerSelect6){ //as above
            selectedTowerIndex = 6;
        }
        if(e.getSource() == towerSelect7){ //as above
            selectedTowerIndex = 7;
        }
        if(e.getSource() == towerSelect8){ //as above
            selectedTowerIndex = 8;
        }
        if(e.getSource() == towerSelect9){ //as above
            selectedTowerIndex = 9;
        }
        if(e.getSource() == towerSelect0){ //as above
            selectedTowerIndex = 0;
        }
        if(e.getSource() == infoButton){ //button hidden on the main menu behind the info button so that users that do not know the keyboard controls can click and will be taken to the info screen explaining the game and controls
            gameState = infoState;
            removeInfoButton();
        }
        if(e.getSource() == hitMarkerButton){ //toggles the damage values that appear when a monster is damaged
            showDamage = !showDamage;
        }
        if(e.getSource() == saveButton){ //saves game
            if(loadedGameID>=0){ //if game has been previously saved it will override
                dba.saveLoadedGame(this);
            }else{ //if game has not been previously saved it will create a new game save in the database
                dba.saveNewGame(username, this);
            }
        }
        if(e.getSource() == elementButton1){ //used to change the elements of towers
            if(userCurrency>=10){ //if user can afford to change element
                if(tower[interactTowerIndex].changeElement(1)){ //attempts to change element, returns false if trying to change to currently selected element
                    userCurrency -=10;
                }
            }
        }
        if(e.getSource() == elementButton2){ //used to change the elements of towers
            if(userCurrency>=10){ //as above
                if(tower[interactTowerIndex].changeElement(2)){//as above
                    userCurrency -=10;
                }
            }
        }
        if(e.getSource() == elementButton3){ //used to change the elements of towers
            if(userCurrency>=10){//as above
                if(tower[interactTowerIndex].changeElement(3)){//as above
                    userCurrency -=10;
                }
            }
        }
    }

    public void removeInfoButton(){ //hides info button after game has started
        this.remove(infoButton);
    }

}
