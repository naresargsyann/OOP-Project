
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckersGUI extends JFrame implements ActionListener {

    private static class ButtonCell extends JButton {

        private int CellPosX;
        private int CellPosY;

        public ButtonCell(int x, int y) {
            this.CellPosX = x;
            this.CellPosY = y;
        }

        public int getPosX() {
            return CellPosX;
        }

        public int getPosY() {
            return CellPosY;
        }

        public void changeState(Icon img) {
            this.setIcon(img);
        }
    }

    private Game game;
    private CheckersGUI.ButtonCell[][] buttons = new CheckersGUI.ButtonCell[8][8];
    private int[] firstClick = new int[2];
    private int[] secondClick = new int[2];
    private int[] click = new int[2];
    private int clickCounter = 0;
    private JPanel board = new JPanel();
    private JButton giveUp, newGame;
    private JLabel turnLabel;
    private boolean player = true;
    private ImageIcon scaledGiveUpIcon;
    private ImageIcon scaledWhitePieceIcon;
    private ImageIcon scaledBlackPieceIcon;
    private ImageIcon scaledNewGameIcon;

    public CheckersGUI() {
        super("Checkers");
        setSize(900, 900);
        setTitle("Checkers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        Border silverBorder = BorderFactory.createLineBorder(Color.GRAY, 10);

        // creating and setting button pannel;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 778));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // an empty border for separation
        buttonPanel.setBorder(silverBorder);

        // setting the board panel;
        board.setLayout(new GridLayout(8, 8));
        board.setEnabled(true);
        board.setSize(760, 760);
        board.setBorder(silverBorder);

        // a label indicating who's turn it is;
        turnLabel = new JLabel("White's Turn"); // initial text
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        turnLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(turnLabel, BorderLayout.NORTH);

        game = new Game();

        // creating a button for "New Game";
        newGame = new JButton("NEW GAME");
        newGame.setFont(new Font("Times New Roman", Font.BOLD, 14));
        newGame.setBackground(Color.WHITE);
        newGame.setPreferredSize(new Dimension(300, 300));
        newGame.addActionListener(this);
        scaledNewGameIcon = createImageIcon("C:/Users/Anna/Desktop/OOPProject/Checkers/CheckersGUI/restart.png");
        newGame.setIcon(scaledNewGameIcon);
        buttonPanel.add(newGame);

        // creating a button for "Give Up";
        giveUp = new JButton("GIVE UP");
        giveUp.setFont(new Font("Times New Roman", Font.BOLD, 18));
        giveUp.setPreferredSize(new Dimension(300, 300));
        giveUp.setBackground(Color.WHITE);
        giveUp.addActionListener(this);
        scaledGiveUpIcon = createImageIcon("C:/Users/Anna/Desktop/OOPProject/Checkers/CheckersGUI/alcohol.png");
        giveUp.setIcon(scaledGiveUpIcon);
        buttonPanel.add(giveUp);

        // adding button panel to main frame;

        add(buttonPanel, BorderLayout.WEST);

        // creating black and white pieces for board;
        scaledBlackPieceIcon = createImageIcon("C:/Users/Anna/Desktop/OOPProject/Checkers/CheckersGUI/black.png");
        scaledWhitePieceIcon = createImageIcon("C:/Users/Anna/Desktop/OOPProject/Checkers/CheckersGUI/white.png");
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ButtonCell cell = new ButtonCell(row, col);
                if ((row + col) % 2 == 0) {
                    cell.setBackground(Color.WHITE);
                    cell.setEnabled(false);
                } else {
                    cell.setBackground(Color.DARK_GRAY);
                    cell.setPreferredSize(new Dimension(95, 95));
                    cell.addActionListener(this);
                }

                if (row < 3 && (row + col) % 2 != 0) {
                    cell.changeState(scaledBlackPieceIcon);
                } else if (row > 4 && (row + col) % 2 != 0) {
                    cell.changeState(scaledWhitePieceIcon);
                }
                buttons[row][col] = cell;
                board.add(cell);
            }
        }

        // adding board to main frame;
        add(board, BorderLayout.WEST);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof ButtonCell) {
            ButtonCell source = (ButtonCell) e.getSource();
            click[0] = source.getPosX();
            click[1] = source.getPosY();
            if (clickCounter == 0) {
                if (game.isEmpty(click[0], click[1]) == null) {
                    clickCounter = 0;
                } else {
                    ++clickCounter;
                    clicked(click);
                }
            } else {
                ++clickCounter;
                clicked(click);
            }
        } else if (e.getSource() == giveUp) {
            Object[] options = { "Yes", "No" };
            int choice = JOptionPane.showOptionDialog(null,
                    "Do you want to give up?",
                    "WARNING: LIFE or DEATH",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    createImageIcon("C:/Users/Anna/Desktop/OOPProject/Checkers/CheckersGUI/new.png"),
                    options,
                    options[0]);
            if (choice == 0) {
                JOptionPane.showMessageDialog(null, "The opponent won!");
                this.dispose();
            } else {
                return;
            }
        } else if (e.getSource() == newGame) {
            Object[] options = { "Yes", "No" };
            int choice = JOptionPane.showOptionDialog(null,
                    "Do you want to restart the game? The new game will begin.",
                    "Restarting the game",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    createImageIcon("C:/Users/Anna/Desktop/OOPProject/Checkers/CheckersGUI/meditation.png"),
                    options,
                    options[0]);
            if (choice == 0) {
                this.dispose();
                new CheckersGUI();
            } else {
                return;
            }
        }
    }

    private void clickDisable() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons.row.length; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void clicked(int[] clicks) {
        if (clickCounter == 1) {
            if (game.isEmpty(click[0], click[1]).getColor() != (player ? 'W' : 'B')) {
                return;
            }
            firstClick[0] = clicks[0];
            firstClick[1] = clicks[1];
        } else if (clickCounter == 2) {
            secondClick[0] = click[0];
            secondClick[1] = click[1];
            play(player, firstClick[0], firstClick[1], secondClick[0], secondClick[1]);
            clickCounter = 0;
            player = !player;
            turnLabel.setText(player ? "White's Turn" : "Black's Turn");
        }
    }

    public void play(boolean player, int startx, int starty, int destx, int desty) {

        String destination = startx + "" + starty + "" + destx + "" + desty;

        if (!game.isGameOver(player)) {
            if (game.isCaptureAvailable(player)) {

                for (int i = 0; i < game.moves.size(); i++) {
                    if (game.moves.get(i).equals(destination)) {
                        game.isEmpty(startx, starty)
                                .move(destx, desty);
                        buttons[startx][starty].changeState(null);
                        if (player) {
                            buttons[destx][desty].changeState(scaledWhitePieceIcon);
                        } else {
                            buttons[destx][desty].changeState(scaledBlackPieceIcon);
                        }
                        game.capture((startx + destx) / 2, (starty + desty) / 2, !player);
                        buttons[(startx + destx) / 2][(starty + desty) / 2].changeState(null);
                    }
                }
            } else {
                if (game.isMoveValid(startx, starty, destx, desty, player)) {
                    game.isEmpty(startx, starty)
                            .move(destx, desty);
                    buttons[startx][starty].changeState(null);
                    if (player)
                        buttons[destx][desty].changeState(scaledWhitePieceIcon);
                    else
                        buttons[destx][desty].changeState(scaledBlackPieceIcon);
                }
            }
            game.moves.clear();
        }

        else {
            if (player) {
                JOptionPane.showMessageDialog(null, "The black won!");
            } else {
                JOptionPane.showMessageDialog(null, "The White won!");

            }
            clickDisable();
        }
    }

    private ImageIcon createImageIcon(String imagePath) { // helper method to set Icons on buttons;
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);

        return new ImageIcon(image);
    }

    public static void main(String[] args) {
        new CheckersGUI();
    }
}
