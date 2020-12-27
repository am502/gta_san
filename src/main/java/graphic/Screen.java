package graphic;

import lombok.Getter;
import util.Const;

import java.util.concurrent.ThreadLocalRandom;

// from i = 0, j = 0 to i = 1080, j = 1920
public class Screen {
    private int height;
    private int width;
    @Getter
    private int[][] pixels;
    private int[][] tiles;

    public Screen(int height, int width) {
        this.height = height;
        this.width = width;
        pixels = new int[height][width];

        tiles = new int[Const.MAP_SIZE_IN_TILES][Const.MAP_SIZE_IN_TILES];
        fillTilesWithRandom();
    }

    private void fillTilesWithRandom() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = ThreadLocalRandom.current().nextInt(0xffffff);
            }
        }
    }

    public void render(int iOffset, int jOffset) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = tiles
                        [((i + iOffset) / Const.TILE_SIZE_IN_PIXELS) & Const.MAP_SIZE_IN_TILES_MASK]
                        [((j + jOffset) / Const.TILE_SIZE_IN_PIXELS) & Const.MAP_SIZE_IN_TILES_MASK];
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
}
