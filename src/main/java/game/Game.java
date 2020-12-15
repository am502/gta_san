package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private static final int WIDTH = 320;
    private static final int HEIGHT = WIDTH / 16 * 9;
    private static final int SCALE = 4;

    private Thread thread;
    private JFrame frame;
    private boolean isRunning;

    public Game() {
        isRunning = false;

        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);

        frame = new JFrame();

        frame.setResizable(false);
        frame.setTitle("Craft Punk");
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void run() {
        while (isRunning) {
            update();
            render();
        }
    }

    private void update() {
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
    }

    public synchronized void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
