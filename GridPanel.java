package com.small;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    private boolean[] grid;
    private final int gridWidth;
    private final int gridHeight;
    private final int cellWidth;
    private final int cellHeight;

    public GridPanel(int gridWidth, int gridHeight, int cellWidth, int cellHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        Dimension size = new Dimension(this.gridWidth * this.cellWidth, this.gridWidth * this.cellWidth);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        grid = new boolean[this.gridWidth * this.gridHeight];
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int column = 1; column < gridWidth-1; ++column) {
            for (int row = 1; row < gridHeight-1; ++row) {
                int x = column * cellWidth;
                int y = row * cellHeight;
                drawIt(g, x, y, getGrid()[column * gridWidth + row]);
            }
        }
    }

    private void drawIt(Graphics g, int x, int y, boolean isOn) {
        g.setColor(isOn ? Color.RED : Color.BLACK);
        g.fillRect(x, y, cellWidth, cellHeight);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    public boolean[] getGrid() {
        return grid;
    }

    public void setGrid(boolean[] grid) {
        this.grid = grid;
        this.repaint();
    }
}
