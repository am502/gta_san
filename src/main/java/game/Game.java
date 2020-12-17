package game;

import screen.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private int height;
    private int width;
    private int scaledHeight;
    private int scaledWidth;
    private Thread thread;
    private JFrame frame;
    private boolean isRunning;

    private Screen screen;

    private BufferedImage image;
    private int[] imagePixels;

    public Game(int height, int width, int scale) {
        isRunning = false;
        this.height = height;
        this.width = width;
        this.scaledHeight = height * scale;
        this.scaledWidth = width * scale;
        setSize();
        initFrame();
        initImage();
        screen = new Screen(height, width);
    }

    private void setSize() {
        Dimension size = new Dimension(scaledWidth, scaledHeight);
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
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    private void fillImagePixels(int[][] pixels) {
        int index = 0;
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                imagePixels[index] = pixels[i][j];
                index++;
            }
        }
    }

    public void run() {
        long lastTimeForPerSecond = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        long lastTime = System.nanoTime();
        double nanoInOneTick = 1000000000.0 / 64.0;
        double delta = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nanoInOneTick;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - lastTimeForPerSecond > 1000) {
                lastTimeForPerSecond = System.currentTimeMillis();
                frame.setTitle("fps=" + frames + " ups=" + updates);
                frames = 0;
                updates = 0;
            }
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

        screen.clear();
        screen.render();
        fillImagePixels(screen.getPixels());

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
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
        Game game = new Game(180, 320, 4);
        game.start();
    }
}
