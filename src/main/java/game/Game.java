package game;

import graphic.Screen;
import input.Keyboard;
import util.Util;

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
    private Keyboard keyboard;
    private int iCurrent;
    private int jCurrent;

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
        keyboard = new Keyboard();
        addKeyListener(keyboard);
        this.iCurrent = 0;
        this.jCurrent = 0;
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

    public void run() {
        long lastTimeForPerSecond = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        // 1 000 000 000 / 64
        long nanoSecondInOneTick = 15625000;
        long lastTime = System.nanoTime();
        long delta = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += now - lastTime;
            lastTime = now;
            while (delta >= nanoSecondInOneTick) {
                update();
                updates++;
                delta -= nanoSecondInOneTick;
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
        keyboard.update();
        if (keyboard.isUp()) iCurrent--;
        if (keyboard.isDown()) iCurrent++;
        if (keyboard.isLeft()) jCurrent--;
        if (keyboard.isRight()) jCurrent++;
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        screen.render(iCurrent, jCurrent);
        Util.convertFrom2dTo1d(screen.getPixels(), imagePixels);

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
        Game game = new Game(180, 320, 5);
        game.start();
    }
}
