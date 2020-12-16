package game;

import screen.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private static final int WIDTH = 320;
    private static final int HEIGHT = WIDTH / 16 * 9;
    private static final int SCALE = 4;

    private Thread thread;
    private JFrame frame;
    private boolean isRunning;

    private Screen screen;

    private BufferedImage image;
    private int[] pixels;

    public Game() {
        isRunning = false;
        setSize();
        initFrame();
        initImage();
        screen = new Screen(HEIGHT, WIDTH);
    }

    private void setSize() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
    }

    private void initFrame() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("Hell Ar Bourne Est Mine");
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initImage() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    private void fillPixels(int[][] pixels) {
        int index = 0;
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                this.pixels[index] = pixels[i][j];
                index++;
            }
        }
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

        screen.render();
        fillPixels(screen.getPixels());

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
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
