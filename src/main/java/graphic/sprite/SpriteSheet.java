package graphic.sprite;

import lombok.Getter;
import util.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    public static final SpriteSheet WORLD_MAP_TILES =
            new SpriteSheet("/src/main/resources/spritesheet/world_map_tiles.png", 1, 1);

    private String path;
    @Getter
    private int pixelsBetween;
    @Getter
    private int pixelsBorder;
    @Getter
    private int[][] pixels;

    public SpriteSheet(String path, int pixelsBetween, int pixelsBorder) {
        this.path = path;
        this.pixelsBetween = pixelsBetween;
        this.pixelsBorder = pixelsBorder;
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            int h = image.getHeight();
            int w = image.getWidth();
            int[] temp = new int[h * w];
            image.getRGB(0, 0, w, h, temp, 0, w);
            pixels = new int[h][w];
            Util.convertFrom1dTo2d(temp, pixels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
