package level;

import java.util.concurrent.ThreadLocalRandom;

public class RandomLevel extends Level {
    public RandomLevel(int height, int width) {
        super(height, width);
    }

    protected void generateLevel() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = ThreadLocalRandom.current().nextInt(8);
            }
        }
    }
}
