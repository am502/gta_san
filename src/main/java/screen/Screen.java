package screen;

public class Screen {
    private int height;
    private int width;
    private int[][] pixels;

    public Screen(int height, int width) {
        this.height = height;
        this.width = width;
        pixels = new int[height][width];
    }

    public void render() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = 0xff00ff;
            }
        }
    }

    public int[][] getPixels() {
        return pixels;
    }
}
