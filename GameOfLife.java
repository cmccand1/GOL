package GOL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// A basic implementation of Conway's Game of Life

public class GameOfLife {

    private JButton[][] board;

    public GameOfLife() {
        board = new JButton[50][50];

        JFrame frame = new JFrame("Game of Life");
        JPanel panel = new JPanel(new GridLayout(50, 50));
        Random rand = new Random();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                // Create the JButton
                board[i][j] = new JButton();
                panel.add(board[i][j]);
                board[i][j].setBackground(Color.LIGHT_GRAY);
                board[i][j].setOpaque(true);
                board[i][j].setBorderPainted(false);
                // Populate 1/2 of the grid at random
                if (rand.nextFloat() < .75) {
                    board[i][j].setBackground(Color.BLACK);
                }
            }
        }

        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setResizable(false);
    } // End of constructor

    /**
     * Returns the game board as a 2-D JButton array
     * @return    the game board
     */
    private JButton[][] getBoard() {
        return board;
    }

    /**
     *
     * @param a    the 2-D array to search (the game board)
     * @param i    the cell's row index
     * @param j    the cell's column index
     * @return    the number of neighboring alive cells
     */
    private int getNumberOfNeighbors(JButton[][] a, int i, int j) {
        int neighbors = 0;
        // Top left corner case (3 neighbors)
        if (i == 0 && j == 0) {
            if (a[i+1][j].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i+1][j+1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i][j+1].getBackground() == Color.BLACK) {neighbors++;}
        }
        // Top right corner case (3 neighbors)
        else if (i == 0 && j == a[j].length - 1) {
            if (a[i][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i+1][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i+1][j].getBackground() == Color.BLACK) {neighbors++;}
        }
        // Bottom left corner case (3 neighbors)
        else if (i == a[j].length - 1 && j == 0) {
            if (a[i-1][j].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i-1][j+1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i][j+1].getBackground() == Color.BLACK) {neighbors++;}
        }
        // Bottom right corner case (3 neighbors)
        else if (i == a[j].length - 1 && j == a[i].length - 1) {
            if (a[i][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i-1][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i-1][j].getBackground() == Color.BLACK) {neighbors++;}
        }
        // Top edge cases, non-corner (5 neighbors each)
        else if (i == 0 && j > 0 && j < a[i].length) {
            if (a[i][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i+1][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i+1][j].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i+1][j+1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i][j+1].getBackground() == Color.BLACK) {neighbors++;}
        }
        // Left side edge cases, non-corner (5 neighbors each)
        else if (j == 0 && i > 0 && i < a[j].length) {
            if (a[i-1][j].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i-1][j+1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i][j+1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i+1][j+1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i+1][j].getBackground() == Color.BLACK) {neighbors++;}
        }
        // Bottom edge cases, non-corner (5 neighbors each)
        else if (i == a[j].length - 1 && j > 0 && j < a[i].length) {
            if (a[i][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i-1][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i-1][j].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i-1][j+1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i][j+1].getBackground() == Color.BLACK) {neighbors++;}
        }
        // Right edge cases, non-corner (5 neighbors each)
        else if (j == a[i].length - 1 && i > 0 && i < a[j].length - 1) {
            if (a[i+1][j].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i+1][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i-1][j-1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i-1][j].getBackground() == Color.BLACK) {neighbors++;}
        }
        else if (i > 0 && j > 0 && i < a[j].length - 1 && j < a[i].length - 1) {
            if (a[i][j - 1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i - 1][j - 1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i - 1][j].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i - 1][j + 1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i][j + 1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i + 1][j + 1].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i + 1][j].getBackground() == Color.BLACK) {neighbors++;}
            if (a[i + 1][j - 1].getBackground() == Color.BLACK) {neighbors++;}
        }
        return neighbors;
    }

    /**
     * Checks a given cell for life by checking for it's color (WHITE = dead,  BLACK = alive)
     * @param rowIdx    the row index of the cell
     * @param colIdx    the column index of the cell
     * @return    true if the cell is alive, false otherwise
     */
    private boolean isAlive(int rowIdx, int colIdx) {
        return this.getBoard()[rowIdx][colIdx].getBackground() == Color.BLACK;
    }

    /**
     * Populates the cell according to it's indices by painting it's background color BLACK
     * @param rowIdx    the row index of the cell
     * @param colIdx    the column index of the cell
     */
    private void populateCell(int rowIdx, int colIdx) {
        this.getBoard()[rowIdx][colIdx].setBackground(Color.BLACK);
    }

    /**
     * Destroys the cell specified by its indices by painting it's background color WHITE
     * @param rowIdx    the cell's row index
     * @param colIdx    the cell's column index
     */
    private void destroyCell(int rowIdx, int colIdx) {
        this.getBoard()[rowIdx][colIdx].setBackground(Color.LIGHT_GRAY);
    }


    public static void main(String[] args) {

        GameOfLife gol = new GameOfLife();

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton[][] a = gol.getBoard();
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j < 50; j++) {
                        if (gol.isAlive(i, j)) {
                            if (gol.getNumberOfNeighbors(a, i, j) < 2) {
                                gol.destroyCell(i, j);
                            }
                            if (gol.getNumberOfNeighbors(a, i, j) == 2 || gol.getNumberOfNeighbors(a, i, j) == 3) {
                                gol.populateCell(i, j);
                            }
                            if (gol.getNumberOfNeighbors(a, i, j) > 3) {
                                gol.destroyCell(i, j);
                            }
                        }
                        else if (!gol.isAlive(i, j)) {
                            if (gol.getNumberOfNeighbors(a, i, j) == 3) {
                                gol.populateCell(i, j);
                            }
                        }
                    }

                }
            }
        };
        new Timer(100, listener).start();

        // Any live cell with < 2 live neighbours dies
        // Any live cell with 2 or 3 live neighbours lives
        // Any live cell with > 3 live neighbours dies
        // Any dead cell with three live neighbours becomes a live cell

    } // end of main()
} // end of GameOfLife

