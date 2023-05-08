import java.awt.*;
import javax.swing.*;

public class GUIBoard extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel gameBoard;

    public GUIBoard() {
        setTitle("Checkers Game");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);

        gameBoard = new JPanel(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                if ((i + j) % 2 == 0) {
                    button.setBackground(Color.WHITE);
                    button.setEnabled(false);
                } else {
                    button.setBackground(Color.BLACK);
                }
                gameBoard.add(button);
            }
        }
        add(gameBoard);
        setVisible(true);
    }

    // protected void paintComponent(java.awt.Graphics g) {
    //     super.paintComponent(g);


    public static void main(String[] args) {
        new GUIBoard();
    }
}
