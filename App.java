package com.small;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;

public class App extends JFrame implements ActionListener {
    private static final int DELAY = 100;
    private static final int gridWidth = 100;
    private static final int gridHeight = 100;
    private static final int cellWidth = 9;
    private static final int cellHeight = 9;

    private final Timer timer = new Timer(DELAY, this);
    private final GridPanel gridPanel =
            new GridPanel(gridWidth, gridHeight, cellWidth, cellHeight);

    private JButton buttonStop;
    private JButton buttonGo;

    public App() {
        initGUI();
    }

    private void stop() {
        timer.stop();
        buttonGo.setEnabled(true);
        buttonStop.setEnabled(false);
    }

    private void go() {
        timer.start();
        buttonGo.setEnabled(false);
        buttonStop.setEnabled(true);
    }

    private void randomGridInit() {
        Random rng = new Random();
        boolean[] grid = gridPanel.getGrid();
        for (int column = 1; column < App.gridWidth - 1; ++column) {
            for (int row = 1; row < App.gridHeight - 1; ++row) {
                grid[column * gridWidth + row] = rng.nextInt() % 7 == 0;
            }
        }
        gridPanel.setGrid(grid);
    }

    private int xyToGrid(int x, int y) {
        return y * gridWidth + x;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand() == null) {
            doCycle();
        } else if (actionEvent.getActionCommand().equals("Randomize")) {
            randomGridInit();
        } else if (actionEvent.getActionCommand().equals("Stop")) {
            stop();
        } else if (actionEvent.getActionCommand().equals("Go")) {
            go();
        } else if (actionEvent.getActionCommand().equals("Quit")) {
            this.processWindowEvent(
                    new WindowEvent(
                            this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private boolean nextVal(int x, int y) {
        boolean[] grid = gridPanel.getGrid();
        boolean currentState = grid[xyToGrid(x, y)];
        int liveNeighbors = 0;
        liveNeighbors += grid[xyToGrid(x - 1, y)] ? 1 : 0;
        liveNeighbors += grid[xyToGrid(x + 1, y)] ? 1 : 0;
        liveNeighbors += grid[xyToGrid(x, y - 1)] ? 1 : 0;
        liveNeighbors += grid[xyToGrid(x, y + 1)] ? 1 : 0;
        liveNeighbors += grid[xyToGrid(x - 1, y - 1)] ? 1 : 0;
        liveNeighbors += grid[xyToGrid(x - 1, y + 1)] ? 1 : 0;
        liveNeighbors += grid[xyToGrid(x + 1, y - 1)] ? 1 : 0;
        liveNeighbors += grid[xyToGrid(x + 1, y + 1)] ? 1 : 0;

        return (currentState && liveNeighbors == 2) || liveNeighbors == 3;
    }

    private void doCycle() {
        boolean[] grid2 = new boolean[gridWidth * gridHeight];
        for (int x = 1; x < App.gridWidth - 1; ++x) {
            for (int y = 1; y < App.gridHeight - 1; ++y) {
                grid2[xyToGrid(x, y)] = nextVal(x, y);
            }
        }
        gridPanel.setGrid(grid2);
    }

    private void initGUI() {
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(gridWidth * cellWidth + 30,
                gridHeight * cellHeight + 110);

        JPanel panel = new JPanel();
        add(panel);
        panel.setBackground(Color.BLUE);
        panel.add(gridPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);

        JButton buttonRandomize = new JButton("Randomize");
        buttonRandomize.addActionListener(this);
        buttonPanel.add(buttonRandomize);

        buttonStop = new JButton("Stop");
        buttonStop.addActionListener(this);
        buttonPanel.add(buttonStop);

        buttonGo = new JButton("Go");
        buttonGo.addActionListener(this);
        buttonPanel.add(buttonGo);

        JButton buttonQuit = new JButton("Quit");
        buttonQuit.addActionListener(this);
        buttonPanel.add(buttonQuit);

        panel.add(buttonPanel);
        pack();

        setVisible(true);

        randomGridInit();
    }

    public static void main(String[] args) {
        App app = new App();
        app.go();
    }
}