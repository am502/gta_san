package level;

import graphic.Screen;

public class Level {
    protected int height;
    protected int width;
    protected int[][] tiles;

    public Level(int height, int width) {
        this.height = height;
        this.width = width;
        tiles = new int[height][width];
        generateLevel();
    }

    protected void generateLevel() {
    }

    public void update() {
    }

    public void render(int iScroll, int jScroll, Screen screen) {
    }
}
