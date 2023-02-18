package tile;

import gameFolder.GamePanel;
import logic.UtilityTool;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    boolean drawPath = false;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[60];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/resources/maps/map01.txt");
    }


    public void getTileImage() {
        //PLACEHOLDERS
        setup(0, "grass00", false, true, false, false);
        setup(1, "grass00", false, true, false, false);
        setup(2, "grass00", false, true, false, false);
        setup(3, "grass00", false, true, false, false);
        setup(4, "grass00", false, true, false, false);
        setup(5, "grass00", false, true, false, false);
        setup(6, "grass00", false, true, false, false);
        setup(7, "grass00", false, true, false, false);
        setup(8, "grass00", false, true, false, false);
        setup(9, "grass00", false, true, false, false);

        //ACTUALLY USED
        setup(10, "grass00", false, true, false, false);
        setup(11, "grass01", false, true, false, false);
        setup(12, "water00", true,true, false, false);
        setup(13, "water01", true,true, false, false);
        setup(14, "water02", true,true, false, false);
        setup(15, "water03", true,true, false, false);
        setup(16, "water04", true,true, false, false);
        setup(17, "water05", true,true, false, false);
        setup(18, "water06", true,true, false, false);
        setup(19, "water07", true,true, false, false);
        setup(20, "water08", true,true, false, false);
        setup(21, "water09", true,true, false, false);
        setup(22, "water10", true,true, false, false);
        setup(23, "water11", true,true, false, false);
        setup(24, "water12", true,true, false, false);
        setup(25, "road16", true,true, false, false);
        setup(26, "road00", true, false,false, false);
        setup(27, "road01", false,true, false, false);
        setup(28, "road02", false,true, false, false);
        setup(29, "road03", false,true, false, false);
        setup(30, "road04", false,true, false, false);
        setup(31, "road05", false,true, false, false);
        setup(32, "road06", false,true, false, false);
        setup(33, "road07", false,true, false, false);
        setup(34, "road08", false,true, false, false);
        setup(35, "road09", false,true, false, false);
        setup(36, "road10", false,true, false, false);
        setup(37, "road11", false,true, false, false);
        setup(38, "road12", false,true, false, false);
        setup(39, "earth", true, true, false, false);
        setup(40, "wall", true, true, false, false);
        setup(41, "tree", true, true, false, false);

        setup(42, "road13", false,true, false, false);
        setup(43, "road14", false,true, false, false);
        setup(44, "road15", false,true, false, false);
        setup(45, "gui00", true,true, false, false);
        setup(46, "gui01", true,true, false, false);
        setup(47, "gui02", true,true, false, false);
        setup(48, "gui03", true,true, false, false);
        setup(49, "gui04", true,true, false, false);
        setup(50, "gui05", true,true, false, false);
        setup(51, "gui06", true,true, false, false);
        setup(52, "gui07", true,true, false, false);
        setup(53, "gui08", true,true, false, false);
        setup(54, "gui09", true,true, false, false);
        setup(55, "gui10", true,true, false, false);
        setup(56, "gui11", true,true, false, false);
        setup(57, "roadStart", true,false,true, false);
        setup(58, "roadEnd", true,false, false, true);

    }

    public void setup(int index, String imageName, boolean mouseCollision, boolean enemyCollision, boolean startPath, boolean endPath) {

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].mouseCollision = mouseCollision;
            tile[index].enemyCollision = enemyCollision;
            tile[index].startTile = startPath;
            tile[index].endTile = endPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath); //input stream from the text file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); //buffered reader for the input stream

            int col = 0, row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) { //loops untill whole map has been added

                String line = br.readLine(); //reads next line

                while (col < gp.maxScreenCol) { //loops until edge of screen is reached
                    String numbers[] = line.split(" "); //splits up line String into array

                    int num = Integer.parseInt(numbers[col]); //gets the number corresponding to current column number

                    mapTileNum[col][row] = num; //assigns the number into the 2d Array
                    if(tile[num].startTile){ //assigns start tile for the map
                        gp.startCol = col;
                        gp.startRow = row;
                    }
                    if(tile[num].endTile){ //assigns goal tile for the map
                        gp.goalCol = col;
                        gp.goalRow = row;
                    }
                    col++; //increments the column

                }
                if (col == gp.maxScreenCol) { //once the edge is reached the column is reset and the row is incremented
                    col = 0;
                    row++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0, worldRow = 0;

        while (worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            g2.drawImage(tile[tileNum].image, worldX, worldY,null);
            worldCol++;

            if (worldCol == gp.maxScreenCol) {
                worldCol = 0;
                worldRow++;
            }
        }

        if(drawPath){
            g2.setColor(new Color(225,0,0,70));

            for (int i = 0; i < gp.pFinder.pathList.size(); i++) {
                int screenX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int screenY = gp.pFinder.pathList.get(i).row * gp.tileSize;

                g2.fillRect(screenX,screenY,gp.tileSize,gp.tileSize);
            }
        }


    }
}
