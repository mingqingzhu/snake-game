import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        //create the frame
        JFrame jf = new JFrame("Snake Game!!!");
        jf.setBounds(280,80,900,720);
        jf.setResizable(false);
        jf.setBackground(Color.gray);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add panel

        SnakePanel panel = new SnakePanel();

        jf.add(panel);



        jf.setVisible(true);

    }
}
