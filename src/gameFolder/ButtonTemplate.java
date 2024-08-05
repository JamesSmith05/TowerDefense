package gameFolder;

import javax.swing.*;
import java.awt.*;

public class ButtonTemplate extends JButton {

    public ButtonTemplate(int x, int  y, int width, int height, String text) {
        this.setBounds(x, y, width,  height); //sets size
        this.setFont(new Font("Comic Sans", Font.BOLD, 20)); //text unused
        this.setText(text);
        this.setFocusable(false); // removes focus box around text
        this.setOpaque(false);  // crucial, otherwise buttons appear when hovered over
        this.setContentAreaFilled(false); //used to make button invisible
        this.setBorderPainted(false); //used to make button invisible

    }

    public void updateLocation(int x, int  y){
        this.setBounds(x,y,this.getWidth(),this.getHeight()); //used to move buttons around
    }

}
