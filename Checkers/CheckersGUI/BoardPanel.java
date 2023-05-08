import javax.swing.JPanel;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.io.IOException;
import javax.swing.border.*;
import java.net.*;
import java.util.*;


public class BoardPanel extends JPanel {
    private Board gameBoard= null;

    @Override

    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
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
    }

    public void display(Board board) {
        gameBoard = board;
        repaint();
    }
}