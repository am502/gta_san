package graphic.sprite;

public class Sprite {
    private int sizeInPixels;
    private int row;
    private int column;
    private SpriteSheet sheet;
    private int[][] pixels;

    public Sprite(int sizeInPixels, int row, int column, SpriteSheet sheet) {
        this.sizeInPixels = sizeInPixels;
        pixels = new int[sizeInPixels][sizeInPixels];
        this.row = sheet.getPixelsBorder() + row * sizeInPixels + row * sheet.getPixelsBetween();
        this.column = sheet.getPixelsBorder() + column * sizeInPixels + column * sheet.getPixelsBetween();
        this.sheet = sheet;
        load();
    }

    private void load() {
        for (int i = 0; i < sizeInPixels; i++) {
            for (int j = 0; j < sizeInPixels; j++) {
                pixels[i][j] = sheet.getPixels()[i + row][j + column];
            }
        }
    }
}
