package game.com;

import java.awt.*;
import java.awt.event.*;

class Fdemo extends Frame implements ActionListener, MouseListener {

    Button b[] = new Button[9];
    Button b1; // New game

    int k = 0;
    int a = 0;
    int[] states = new int[9]; // Array to keep track of button states

    Fdemo() {

        setLayout(null);
        setVisible(true);
        setSize(800, 600);
        setLocation(400, 100);
        setBackground(Color.white); // Frame background
        setForeground(Color.black);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose(); // Closes the window
                System.exit(0);
            }
        });

        // Set panel for the Tic-Tac-Toe game grid
        int gridSize = 100;
        int startX = 250;  // Centering the grid horizontally
        int startY = 100;  // Centering the grid vertically

        for (int i = 0; i < 9; i++) {
            b[i] = new Button();
            b[i].setSize(gridSize, gridSize);
            b[i].setLocation(startX + (i % 3) * gridSize, startY + (i / 3) * gridSize);
            b[i].setFont(new Font("", Font.BOLD, 40));
            b[i].setBackground(new Color(255, 141, 28)); // Original orange
            b[i].addActionListener(this);
            b[i].addMouseListener(this); // Hover effect
            add(b[i]);
        }

        // Position the "New Game" button below the grid
        b1 = new Button("New Game");
        b1.setSize(150, 40);
        b1.setLocation(325, 450); // Position it below the grid
        b1.setFont(new Font("", Font.BOLD, 20));
        b1.setForeground(Color.black);
        b1.addActionListener(this);
        b1.addMouseListener(this); // Add hover effect
        add(b1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // For new game
        if (e.getSource() == b1) {
            resetGame();
            return;
        }

        // For the game buttons
        Button clickedButton = (Button) e.getSource();
        int index = findButtonIndex(clickedButton);
        if (index == -1 || states[index] != 0) return; // Ignore if button is already used

        if (a % 2 == 0) {
            clickedButton.setLabel("O");
            states[index] = 1;
        } else {
            clickedButton.setLabel("X");
            states[index] = 2;
        }
        a++;

        // Check for winning conditions
        checkWin();
    }

    private int findButtonIndex(Button btn) {
        for (int i = 0; i < 9; i++) {
            if (b[i] == btn) return i;
        }
        return -1;
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            b[i].setLabel(""); // Reset all button labels
            states[i] = 0; // Reset button states
        }
        a = 0; // Reset the turn counter
        removeAllWinLabels(); // Remove any previous win labels
    }

    private void removeAllWinLabels() {
        // Removes all labels from the frame
        for (Component comp : getComponents()) {
            if (comp instanceof Label) {
                remove(comp);
            }
        }
        repaint();
    }

    private void checkWin() {
        // Define winning combinations
        int[][] winPatterns = {
            {0, 1, 2}, // Row 1
            {3, 4, 5}, // Row 2
            {6, 7, 8}, // Row 3
            {0, 3, 6}, // Column 1
            {1, 4, 7}, // Column 2
            {2, 5, 8}, // Column 3
            {0, 4, 8}, // Diagonal \
            {2, 4, 6}  // Diagonal /
        };

        for (int[] pattern : winPatterns) {
            if (states[pattern[0]] != 0 &&
                states[pattern[0]] == states[pattern[1]] &&
                states[pattern[1]] == states[pattern[2]]) {

                if (states[pattern[0]] == 1) {
                    displayWinMessage("Player 1 wins");
                } else if (states[pattern[0]] == 2) {
                    displayWinMessage("Player 2 wins");
                }
                return; // Exit after finding a win
            }
        }
    }

    private void displayWinMessage(String message) {
        Label p1 = new Label(message);
        p1.setSize(200, 50);
        p1.setLocation(300, 50); // Centered above the grid
        p1.setFont(new Font("", Font.BOLD, 20));
        add(p1);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof Button) {
            Button btn = (Button) e.getSource();
            if (btn == b1) {
                btn.setBackground(Color.LIGHT_GRAY); // Hover effect for "New Game" button
            } else {
                btn.setBackground(Color.YELLOW); // Hover effect for game buttons
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof Button) {
            Button btn = (Button) e.getSource();
            if (btn == b1) {
                btn.setBackground(new Color(255, 255, 255)); // Revert "New Game" background
            } else {
                btn.setBackground(new Color(255, 141, 28)); // Revert game button background to orange
            }
        }
    }

    // Implementing empty methods required by MouseListener interface
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
}

public class tictactoe {
    public static void main(String[] args) {
        new Fdemo();
    }
}
