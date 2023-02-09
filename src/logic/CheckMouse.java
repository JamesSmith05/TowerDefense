package logic;

import gameFolder.GamePanel;

import java.awt.event.*;
import javax.swing.*;

public class CheckMouse extends MouseAdapter{
    GamePanel gp;

        public CheckMouse(GamePanel gp){
            this.gp = gp;
        }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
            System.out.println("Left click detected : " + (e.getPoint()));
            gp.leftClick = true;
        }

        if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
            System.out.println("Right click detected : " + (e.getPoint()));
            gp.rightClick = true;
        }

        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
            System.out.println("Middle click detected : " + (e.getPoint()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        super.mouseReleased(mouseEvent);
    }
}