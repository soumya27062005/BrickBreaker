import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameGUI {
    private JFrame frame;
    private JPanel gridPanel;
    private List<TileGUI> tiles;
    private boolean gameOver;
    private TileGUI firstTile;
    private TileGUI secondTile;

    public GameGUI() {
        frame = new JFrame("Memory Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(6, 6));
        gridPanel.setBackground(Color.decode("#964B00")); // Brown background color

        tiles = createTiles();
        for (TileGUI tile : tiles) {
            gridPanel.add(tile.getButton());
        }

        frame.add(gridPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    private List<TileGUI> createTiles() {
        List<TileGUI> tiles = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 18; i++) {
            numbers.add(i);
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int i = 0; i < 36; i++) {
            TileGUI tile = new TileGUI(numbers.get(i));
            tile.getButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tileClicked(tile);
                }
            });
            tile.getButton().setBackground(Color.decode("#87CEEB")); // drak brown tile color
            tile.getButton().setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tile.getButton().setPreferredSize(new Dimension(60, 60)); // Increased button size
            tile.getButton().setFont(new Font("Arial", Font.BOLD, 24)); // Increased font size
            tiles.add(tile);
        }

        return tiles;
    }

    private void tileClicked(TileGUI tile) {
        if (tile.isFlipped() || tile.isMatched()) {
            return;
        }

        tile.flip();

        if (firstTile == null) {
            firstTile = tile;
        } else if (secondTile == null) {
            secondTile = tile;

            if (firstTile.getNumber() == secondTile.getNumber()) {
                firstTile.setMatched(true);
                secondTile.setMatched(true);
                checkWin();
            } else {
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        firstTile.flip();
                        secondTile.flip();
                        firstTile = null;
                        secondTile = null;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    private void checkWin() {
        for (TileGUI tile : tiles) {
            if (!tile.isMatched()) {
                return;
            }
        }

        JOptionPane.showMessageDialog(frame, "Congratulations, you won!");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameGUI();
            }
        });
    }
}

class TileGUI {
    private int number;
    private JButton button;
    private boolean flipped;
    private boolean matched;

    public TileGUI(int number) {
        this.number = number;
        this.flipped = false;
        this.matched = false;

        button = new JButton();
    }

    public JButton getButton() {
        return button;
    }

    public int getNumber() {
        return number;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void flip() {
        flipped = !flipped;
        if (flipped) {
            button.setText(String.valueOf(number));
        } else {
            button.setText("");
        }
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;}
}
