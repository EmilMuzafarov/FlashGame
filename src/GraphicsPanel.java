import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {
    private BufferedImage background;
    private Player player;
    private Enemy enemy;
    private boolean[] pressedKeys;
    private ArrayList<Coin> coins;
    private Timer timer;
    private int time;


    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/background.png"));
            //background = (BufferedImage) background.getScaledInstance(1120, 725, Image.SCALE_DEFAULT);//
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        player = new Player("src/marioleft.png", "src/marioright.png", "Flash");
        enemy = new Enemy("src/enemyleft.png", "src/enemyright.png", "Reverse Flash");
        coins = new ArrayList<>();
        pressedKeys = new boolean[128];
        time = 5;
        timer = new Timer(1000, this); // this Timer will call the actionPerformed interface method every 1000ms = 1 second
        timer.start();
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this
        g.drawImage(background, 0, 0, null);// the order that things get "painted" matter; we put background down first
        if (!endGame().equals("NoEnd")) {
            g.setFont(new Font("LEXEND", Font.BOLD, 120));
            if (!endGame().equals("VICTORY")) {
                g.setColor(Color.red);
                if (endGame().length()>6) {
                    g.setColor(Color.DARK_GRAY);
                    g.drawString(endGame(), 80, 305);
                } else {
                    g.drawString(endGame(), 500, 305);
                }
            } else {
                g.setColor(Color.yellow);
                g.drawString(endGame(), 200, 305);
            }
        } else {
            g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);
            g.drawImage(enemy.getPlayerImage(), enemy.getxCoord(), enemy.getyCoord(), null);
            Color m = new Color(51, 51, 255);
            g.setColor(m);
            g.fillRect(860, 40, 180, 45);
            g.setColor(Color.red);
            g.setFont(new Font("Concert One", Font.BOLD, 24));
            g.drawString("RESTART", 890, 74);
            //if (player.playerRect().intersects(890, 74) {}
            g.setColor(Color.orange);
            // this loop does two things:  it draws each Coin that gets placed with mouse clicks,
            // and it also checks if the player has "intersected" (collided with) the Coin, and if so,
            // the score goes up and the Coin is removed from the arraylist
            for (int i = 0; i < coins.size(); i++) {
                Coin coin = coins.get(i);
                g.drawImage(coin.getImage(), coin.getxCoord(), coin.getyCoord(), null); // draw Coin
                if (player.playerRect().intersects(coin.coinRect())) { // check for collision
                    player.takeDmg();
                    coins.remove(i);
                    i--;
                }
            }

            // draw score
            g.setFont(new Font("Courier New", Font.BOLD, 24));
            g.drawString(player.getName() + "'s Health: " + player.getHealth(), 20, 40);
            g.drawString(enemy.getName() + "'s Health: " + enemy.getHealth(), 450, 40);
            g.drawString("Time: " + time, 20, 70);

            // player moves left (A)
            if (pressedKeys[65]) {
                player.faceLeft();
                player.moveLeft();
            }

            // player moves right (D)
            if (pressedKeys[68]) {
                player.faceRight();
                player.moveRight();
            }

            // player moves up (W)
            if (pressedKeys[87]) {
                player.moveUp();
            }

            // player moves down (S)
            if (pressedKeys[83]) {
                player.moveDown();
            }
            if (pressedKeys[70]) {

            }
        }
    }

    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { } // unimplemented

    public void keyPressed(KeyEvent e) {
        // see this for all keycodes: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
        // A = 65, D = 68, S = 83, W = 87, left = 37, up = 38, right = 39, down = 40, space = 32, enter = 10
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }  // unimplemented; if you move your mouse while clicking,
    // this method isn't called, so mouseReleased is best

    public void mousePressed(MouseEvent e) { } // unimplemented

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click
            Point mouseClickLocation = e.getPoint();
            Coin coin = new Coin(mouseClickLocation.x, mouseClickLocation.y);
            coins.add(coin);
        } else {
            Point mouseClickLocation = e.getPoint();
            if (player.playerRect().contains(mouseClickLocation)) {
                player.turn();
            }
        }
    }

    public void mouseEntered(MouseEvent e) { } // unimplemented

    public void mouseExited(MouseEvent e) { } // unimplemented

    public String endGame() {
        if (player.getHealth()<0) {
            return "DEFEAT!";
        }
        else if (time==0) {
            return "LOSS TO TIME!";
        }
        else if (enemy.getHealth()<0) {
            return "VICTORY!";
        } else {
            return "NoEnd";
        }
    }
    // ACTIONLISTENER INTERFACE METHODS: used for buttons AND timers!
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            if (time>0) {
                time--;
            }
        }
    }
}
