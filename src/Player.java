import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    private final double MOVE_AMT = 1;
    private BufferedImage orig;
    private BufferedImage right;
    private BufferedImage left;
    private boolean facingRight;
    private double xCoord;
    private double yCoord;
    private int health;
    private String name;
    private boolean start=true;

    public Player(String leftImg, String rightImg, String origImg, String name) {
        this.name = name;
        facingRight = false;
        xCoord = 50; // starting position is (50, 435), right on top of ground
        yCoord = 270;
        health = 100;
        try {
            orig = ImageIO.read(new File(origImg));
            left = ImageIO.read(new File(leftImg));
            right = ImageIO.read(new File(rightImg));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) yCoord;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public boolean isFacingRight() {
        if (facingRight) {
            return true;
        }
        return false;
    }

    public void faceRight() {
        facingRight = true;
        start=false;
    }

    public void faceLeft() {
        facingRight = false;
        start=false;
    }

    public void moveRight() {
        if (xCoord + MOVE_AMT <= 920) {
            xCoord += MOVE_AMT;
        }
    }

    public void moveLeft() {
        if (xCoord - MOVE_AMT >= 0) {
            xCoord -= MOVE_AMT;
        }
    }

    public void moveDown() {
        if (yCoord + MOVE_AMT <= 500) {
            yCoord += MOVE_AMT;
        }
    }
    public void moveUp(double MOVE_AMT) {
        if (yCoord - MOVE_AMT >= 0) {
            yCoord -= MOVE_AMT;
        }
    }

    public void moveDown(double MOVE_AMT) {
        if (yCoord + MOVE_AMT <= 500) {
            yCoord += MOVE_AMT;
        }
    }

    public void turn() {
        if (facingRight) {
            faceLeft();
        } else {
            faceRight();
        }
    }
    public void takeDmg() {
        health-=10;
    }

    public BufferedImage getPlayerImage() {
        if (facingRight) {
            return right;
        } else {
            if (!start) {
                return left;
            } else {
                return orig;
            }
        }
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
