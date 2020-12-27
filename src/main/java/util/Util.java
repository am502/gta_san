package util;

public class Util {
    public static final int TILE_SIZE_IN_PIXELS = 16;
    public static final int MAP_SIZE_IN_TILES = 32;
    public static final int MAP_SIZE_IN_TILES_MASK = MAP_SIZE_IN_TILES - 1;

    public static void convertFrom2dTo1d(int[][] from, int[] to) {
        int height = from.length;
        int width = from[0].length;
        int index = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                to[index] = from[i][j];
                index++;
            }
        }
    }

    public static void convertFrom1dTo2d(int[] from, int[][] to) {
        int height = to.length;
        int width = to[0].length;
        int index = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                to[i][j] = from[index];
                index++;
            }
        }
    }
}
