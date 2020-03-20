import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {

    public SnakePanel() {
        this.setFocusable(true);
        initSnake();
        this.addKeyListener(this); //第一个this表示给snakepanel注册监听器。传参里使用this是因为snakepanel实现了keylistener的具体方法（在Java中能触发事件源的对象，都调用addXXXListener（XXXListener  listener）方法将某个对象注册为自己的监听器，参数listener是个接口，listener可以引用任何实现了该接口的类的所创建的对象，当事件源发生时，接口listener 立刻回调类实现的接口方法（接口的实现类包含了接口方法和非接口方法，接口只能调用接口方法，不能调用非接口方法））
        timer.setRepeats(true);
        timer.start();

    }

    //import the image
    ImageIcon up = new ImageIcon("贪吃蛇/up.png");
    ImageIcon down = new ImageIcon("贪吃蛇/down.png");
    ImageIcon left = new ImageIcon("贪吃蛇/left.png");
    ImageIcon right = new ImageIcon("贪吃蛇/right.png");
    ImageIcon body = new ImageIcon("贪吃蛇/body.png");
    ImageIcon title = new ImageIcon("贪吃蛇/title.png");

    private int[] snakex = new int[750];
    private int[] snakey = new int[750];
    private String direction;
    private int len;
    private boolean starting = false;
    private boolean gameover = false;
    Random generator = new Random(System.currentTimeMillis());
    Timer timer = new Timer(150, this);

    //food data
    private int foodx = generator.nextInt(34) * 25 + 25;
    private  int foody = generator.nextInt(24) * 25 + 75;


    public void initSnake() {
        starting = false;
        len = 3;
        direction = "r";
        snakex[0] = 100;
        snakey[0] = 100;
        snakex[1] = 75;
        snakey[1] = 100;
        snakex[2] = 50;
        snakey[2] = 100;
    }


    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.fillRect(25,75,850,600);//draw the black game area

        //set title
        title.paintIcon(this,g,25,11);

        //draw snake head
        if(direction.equals("r")) {
            right.paintIcon(this, g, snakex[0], snakey[0]);
        } else if (direction.equals("l")) {
            left.paintIcon(this,g,snakex[0],snakey[0]);
        } else if (direction.equals("u")) {
            up.paintIcon(this,g,snakex[0],snakey[0]);
        } else if (direction.equals("d")) {
            down.paintIcon(this,g,snakex[0],snakey[0]);
        }

        //draw snake body
        for (int i = 1; i < len; i++) {
            body.paintIcon(this,g,snakex[i], snakey[i]);
        }

        //set pause statement
        if(!starting) {
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD,30));
            g.drawString("Press space to start/pause.",265,300);
        }

        //set gameover statement
        if(gameover) {
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD,30));
            g.drawString("Game Over!",350,325);
            g.drawString("Press space to restart", 275, 375);
        }

        body.paintIcon(this, g, foodx, foody);

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();

        //press space for pause and consume
        if(keycode == KeyEvent.VK_SPACE) {
            if(gameover == true) {
                initSnake();
                gameover = false;
            }
            starting = !starting;
            repaint();
        }

        if(starting && !gameover) {
            //press down for turning down
            if(keycode == KeyEvent.VK_DOWN && !direction.equals("u")) {
                direction = "d";
                repaint();
            }

            //press right for turning right
            if(keycode == KeyEvent.VK_RIGHT && !direction.equals("l")) {
                direction = "r";
                repaint();
            }

            //press left for turning left
            if(keycode == KeyEvent.VK_LEFT && !direction.equals("r")) {
                direction = "l";
                repaint();
            }

            //press up for turning up
            if(keycode == KeyEvent.VK_UP && !direction.equals("d")) {
                direction = "u";
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(starting && !gameover) {
            for(int i = len; i > 0; i--) {
                snakex[i] = snakex[i-1];
                snakey[i] = snakey[i-1];
            }
            if(direction.equals("r")) {
                snakex[0] += 25;
                if(snakex[0] == 875) {
                    gameover = true;
                }
            } else if (direction.equals("l")) {
                snakex[0] -= 25;
                if(snakex[0] == 0) {
                    gameover = true;
                }
            } else if (direction.equals("u")) {
                snakey[0] -= 25;
                if(snakey[0] == 50) {
                    gameover = true;
                }
            } else if (direction.equals("d")) {
                snakey[0] += 25;
                if(snakey[0] == 675) {
                    gameover = true;
                }
            }

            //if head touches body, then game over
            for(int i = 1; i < len; i++) {
                if(snakex[0] == snakex[i] && snakey[0] == snakey[i]) {
                    gameover = true;
                }
            }

            //eat food
            if(snakex[0] == foodx && snakey[0] == foody) {
                foodx = generator.nextInt(34) * 25 + 25;
                foody = generator.nextInt(24) * 25 + 75;
                len += 1;
            }
        }
        repaint();

    }
}
