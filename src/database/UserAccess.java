package database;

import gameFolder.GamePanel;

import javax.swing.*;

public class UserAccess {

    DBaccess db = new DBaccess();

    public UserAccess() {

    } // asks user if they want to log in, sign up, or play as guest

    public void run() {
        ImageIcon icon = new ImageIcon("pong_icon_smaller.png"); // this is the smaller pong icon, smaller because it is displayed on JOptionpane
        String[] responses = {"Log In", "Sign Up"};

        int loginWindow = JOptionPane.showOptionDialog(
                null,
                "Do you want to:",
                "User Login",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                icon,
                responses,
                0
        ); // "Log In" = 0, "Sign Up" = 1, "Play as guest" =2, X (the x on the window) = -1

        if (loginWindow == 0) { // if user chose "Log In"
            logIn();
        }
        if (loginWindow == 1) { // if user chose "Sign up"
            signUp();
        }
    }

    private void loadGame(String username){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Tower Defense");

        GamePanel gamePanel = new GamePanel(username);
        window.add(gamePanel);

        window.pack();

        window.setLayout(null);

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();

        gamePanel.startGameThread();
    }

    private void signUp() {

        ImageIcon pfpIcon = new ImageIcon("pfp.jpg");
        boolean check = true;

        String username = ""; // initialised here so that username can be used in password checker loop

        while (check) {
            username = (String) JOptionPane.showInputDialog(
                    null,
                    "Enter username:",
                    "Sign Up",
                    JOptionPane.INFORMATION_MESSAGE,
                    pfpIcon,
                    null,
                    ""
            );
            if(username == null){
//                JOptionPane.showMessageDialog(null, "Passwords are not the same", "Sign Up", JOptionPane.WARNING_MESSAGE);
//                check = false;
//                run();
            }
            else if (RegexValidator.isValidUsername(username)){
                check = false;
            }
            else {
                JOptionPane.showMessageDialog(null, "Username cannot have blank spaces.\nThis includes no input.", "Sign Up", JOptionPane.WARNING_MESSAGE);
            }
        }

        check = true;
        while (check) {
            String password = (String) JOptionPane.showInputDialog(
                    null,
                    "Enter password:",
                    "Sign Up",
                    JOptionPane.INFORMATION_MESSAGE,
                    pfpIcon,
                    null,
                    ""
            );

            String password2 = (String) JOptionPane.showInputDialog(
                    null,
                    "Re-enter password:",
                    "Sign up",
                    JOptionPane.INFORMATION_MESSAGE,
                    pfpIcon,
                    null,
                    ""
            );

            if (password.equals(password2)) { //check passwords are the same
                if (db.createUser(username,password)) {
                    JOptionPane.showMessageDialog(null, "Account created successfully!\nUsername: " + username, "Sign Up", JOptionPane.PLAIN_MESSAGE);
                    check = false;
                    loadGame(username);
                } else {
                    JOptionPane.showMessageDialog(null, "Account not created.\nUsername may already be taken.", "Sign Up", JOptionPane.WARNING_MESSAGE);
                    check = false;
                    run();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Passwords are not the same", "Sign Up", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void logIn() {
        ImageIcon pfpIcon = new ImageIcon("pfp.jpg");

        String username = (String) JOptionPane.showInputDialog(
                null,
                "Enter username:",
                "Log In",
                JOptionPane.INFORMATION_MESSAGE,
                pfpIcon,
                null,
                ""
        );

        String password = (String) JOptionPane.showInputDialog(
                null,
                "Enter password:",
                "Log In",
                JOptionPane.INFORMATION_MESSAGE,
                pfpIcon,
                null,
                ""
        );

        if (db.loginUser(username, password)) {
            loadGame(username);
        }
        else {
            JOptionPane.showMessageDialog(
                    null,
                    "User not found.\nPassword or Username may have been entered incorrectly.",
                    "Log In",
                    JOptionPane.WARNING_MESSAGE
            );
            run();
        }
    }
}
