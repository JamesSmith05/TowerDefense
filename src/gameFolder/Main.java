package gameFolder;

import database.UserAccess;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        UserAccess gameUserAccess = new UserAccess();

        gameUserAccess.run();

    }
}
