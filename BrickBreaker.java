import javax.swing.JFrame;

public class BrickBreaker {
    public static void main(String[] args) {
        // Create a new JFrame to hold the game
        JFrame frame = new JFrame();
        GamePlay gameplay = new GamePlay();

        // Setting properties of the JFrame
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Brick Breaker");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gameplay);
        frame.setVisible(true);
    }
}
