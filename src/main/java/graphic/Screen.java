package graphic;

import util.Const;

import java.util.concurrent.ThreadLocalRandom;

public class Screen {
    private int height;
    private int width;
    private int[][] pixels;
    private int[][] tiles;

    public Screen(int height, int width) {
        this.height = height;
        this.width = width;
        pixels = new int[height][width];

        tiles = new int[Const.MAP_HEIGHT][Const.MAP_WIDTH];
        fillTilesWithRandom();
    }

    private void fillTilesWithRandom() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = ThreadLocalRandom.current().nextInt(0xffffff);
            }
        }
    }

    public void render() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = tiles[i / Const.TILE_SIZE][j / Const.TILE_SIZE];
            }
        }
    }

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = 0;
            }
        }
    }

    public int[][] getPixels() {
        return pixels;
    }
}
