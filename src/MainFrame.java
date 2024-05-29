import javax.swing.*;

public class MainFrame implements Runnable {

    private GraphicsPanel panel;
    private boolean t=true;
    public MainFrame(String name) {
        JFrame frame = new JFrame("The Flash Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1072, 610); // 540 height of image + 40 for window menu bar
        frame.setLocationRelativeTo(null); // auto-centers frame in screen

        // create and add panel
        panel = new GraphicsPanel(name);
        frame.add(panel);

        // display the frame
        frame.setVisible(true);

        // start thread, required for animation
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        if (panel.getTime()<0) {
            t=false;
        }
        while (t) {
            panel.repaint();  // we don't ever call "paintComponent" directly, but call this to refresh the panel
        }
    }
}
