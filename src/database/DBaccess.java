package database;

import entities.Entity;
import gameFolder.GamePanel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class DBaccess{

    private final String dbConnectionUrl = "jdbc:ucanaccess://theBigDataSheet.accdb";

    private Connection dbConnection = null;

    public DBaccess(){
    }

    private Statement getSqlStatement() throws SQLException {

        //if there is no connection then it establishes a connection
        if (dbConnection == null) {
            dbConnection = DriverManager.getConnection(dbConnectionUrl, "", ""); // connects to db
        }

        //returns the result of the ran statement
        return dbConnection.createStatement();
    }

    private boolean executeUpdateSql(String sqlQuery) {
        try {
            Statement stmt = getSqlStatement();
            stmt.executeUpdate(sqlQuery);
            stmt.close(); // to save resources

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Integer executeUpdateSqlReturnKey(String sqlQuery) {
        //executes sql query and return the randomly generated key
        int key = -1;

        try {
            Statement stmt = getSqlStatement();
            stmt.executeUpdate(sqlQuery,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getInt(1);
            }
            stmt.close(); // to save resources

        } catch (SQLException e) {
            e.printStackTrace();
            return key;
        }
        return key;
    }

    public ArrayList<Integer> gamesForUsername(String username){
        //return a list of saved games that are associated with the user that has signed in
        ArrayList<Integer> tempArray = new ArrayList<>();

        String sqlQuery = "SELECT GameID, Username from Game";

        try {

            Statement stmt = getSqlStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                if (Objects.equals(username, rs.getString("Username"))){
                    tempArray.add(rs.getInt("GameID"));
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tempArray;
    }

    public void loadGameData(int gameSaveID , GamePanel gp) {

        String sqlQuery = "SELECT * from Game";

        int gameID;

        //load information about the game from database
        try {

            Statement stmt = getSqlStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                gameID = rs.getInt("GameID");
                if (gameID == gameSaveID){
                    gp.userCurrency = rs.getInt("Cash");
                    gp.waveNum = rs.getInt("Round");
                    gp.userLife = rs.getInt("Lives");
                    gp.tileM.loadMap("/resources/maps/map0" + rs.getInt("MapID") + ".txt");
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sqlQuery = "SELECT GameID, TowerID from GameTowerRelation";
        int towerID;

        //load information about the towers linked with the loaded game from database
        try {

            Statement stmt = getSqlStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                gameID = rs.getInt("GameID");
                towerID = rs.getInt("TowerID");
                if (gameID == gameSaveID){
                    loadSingleTower(towerID,gp);
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadSingleTower(int towerSavedID,GamePanel gp){

        String sqlQuery = "SELECT * from Tower";

        int towerID, xCoord,yCoord,elementID,towerWorth;
        boolean u1A,u1B,u1C,u2A,u2B,u2C;
        String towerName;

        //Load all information about towers that is to be loaded
        try {
            Statement stmt = getSqlStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                towerID = rs.getInt("TowerID");
                if (towerID == towerSavedID){
                    towerName = rs.getString("TowerName");
                    xCoord = rs.getInt("xCoord");
                    yCoord = rs.getInt("yCoord");
                    elementID = rs.getInt("ElementID");
                    u1A = rs.getBoolean("upgrade1A");
                    u1B = rs.getBoolean("upgrade1B");
                    u1C = rs.getBoolean("upgrade1C");
                    u2A = rs.getBoolean("upgrade2A");
                    u2B = rs.getBoolean("upgrade2B");
                    u2C = rs.getBoolean("upgrade2C");
                    towerWorth = rs.getInt("towerWorth");
                    System.out.println("Setting tower");
                    gp.aSetter.loadTowerFromSave(xCoord,yCoord,towerName,elementID,u1A,u1B,u1C,u2A,u2B,u2C,towerWorth);
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public String returnGameInfo (int gameToDisplay){
        //Used by the UI to draw game info on the game loading screen
        String sqlQuery = "SELECT * FROM Game";

        String result = "blank";

        try {

            Statement stmt = getSqlStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                if (gameToDisplay == rs.getInt("GameID")){
                    result = "cash: " + rs.getInt("Cash") + " round: " + rs.getInt("Round") + " lives: " + rs.getInt("Lives");
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public void saveLoadedGame (GamePanel gp){ //code that will update the game table and replace the towers in the tower table
        //updates game info in database
        String sqlQuery = String.format("UPDATE Game SET Cash = '%s' AND Set Round = '%s' AND SET Lives = '%s' WHERE GameID = '%s'", gp.userCurrency,gp.waveNum,gp.userLife,gp.loadedGameID); //updates the cash round and lives for a specific gameID
        executeUpdateSql(sqlQuery);//a try catch method that opens a statement runs the query and closes the statement

        int gameID;

        sqlQuery = "SELECT GameID, TowerID from GameTowerRelation";
        int towerID;


        try {

            Statement stmt = getSqlStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            //deletes all towers and their occurrences in the link table associated with current game from database
            //it is important to delete and replace instead of trying to update, since it would be impossible to update the correct tower, as towers can be deleted from the game
            while (rs.next()) {
                gameID = rs.getInt("GameID"); //gets the GameID of current row
                towerID = rs.getInt("TowerID");//gets the TowerID of current row
                if (gameID == gp.loadedGameID){ //checks if tower is associated with current game
                    sqlQuery = String.format("DELETE FROM GameTowerRelation WHERE TowerID = '%s'",towerID); //deletes row
                    executeUpdateSql(sqlQuery);
                    sqlQuery = String.format("DELETE FROM Tower WHERE TowerID = '%s'",towerID); //deletes row
                    executeUpdateSql(sqlQuery);
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int generatedTowerKey;

        //adds all towers currently in the game to database
        for (int i = 0; i < gp.tower.length; i++) { //loops for length of array
            if (gp.tower[i] != null){ //if the contents is not null (contains a tower)
                generatedTowerKey = insertTowerReturnKey(gp, i); //adds all relevant tower information into the database as a new entry and returns the generated primary key

                sqlQuery = String.format("INSERT INTO GameTowerRelation (GameID, TowerID) VALUES ('%s','%s')",gp.loadedGameID,generatedTowerKey); //uses the returned primary key to link the tower to the current game
                executeUpdateSql(sqlQuery);

            }
        }
    }

    private int insertTowerReturnKey(GamePanel gp, int i) {
        //adds tower to tower table and returns generated key to be used in the relation table
        String sqlQuery;
        int generatedTowerKey;
        sqlQuery = String.format("INSERT INTO Tower (TowerName, xCoord, yCoord, ElementID, Upgrade1A, Upgrade1B, Upgrade1C, Upgrade2A, Upgrade2B, Upgrade2C, TowerWorth) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", gp.tower[i].name,gp.tower[i].worldX,gp.tower[i].worldY,1
                , gp.tower[i].upgrade1A, gp.tower[i].upgrade1B, gp.tower[i].upgrade1C, gp.tower[i].upgrade2A, gp.tower[i].upgrade2B, gp.tower[i].upgrade2C, gp.tower[i].towerWorth);
        generatedTowerKey = executeUpdateSqlReturnKey(sqlQuery);
        return generatedTowerKey;
    }

    public void saveNewGame(String username, GamePanel gp){
        //insert into database the game info
        String sqlQuery = String.format("INSERT INTO Game (Username, Cash, Round, Lives, MapID) VALUES ('%s', '%s', '%s', '%s', '%s')", username,gp.userCurrency,gp.waveNum,gp.userLife,gp.mapID);

        int generatedGameKey = executeUpdateSqlReturnKey(sqlQuery);

        gp.loadedGameID = generatedGameKey;

        int generatedTowerKey;

        for (int i = 0; i < gp.tower.length; i++) {
            if (gp.tower[i] != null){
                generatedTowerKey = insertTowerReturnKey(gp, i);

                sqlQuery = String.format("INSERT INTO GameTowerRelation (GameID, TowerID) VALUES ('%s','%s')",generatedGameKey,generatedTowerKey);
                executeUpdateSql(sqlQuery);

            }
        }



    }

    public boolean createUser(String username, String password) {

        String passwordHashValue = StringHasher.getHashValue(password);

        String sqlQuery = String.format("INSERT INTO User (Username, Password) VALUES ('%s', '%s')" , username , passwordHashValue);

        return executeUpdateSql(sqlQuery);
    }

    public Boolean loginUser(String username, String password) {

        String passwordHashValue = StringHasher.getHashValue(password);

        String sqlQuery = String.format("SELECT * FROM User WHERE Username ='%s' AND Password = '%s'", username, passwordHashValue);

        Boolean isValidUser = false;

        try {
            Statement stmt = getSqlStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            isValidUser = rs.next();

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValidUser;
    }
}