package sample.model.button.and.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.model.map.Map;

public class GameOverImage extends ImageView {
    private final Image IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/gameOver.jpg")));

    public GameOverImage(int y, int x) {
        this.setLayoutY(y);
        this.setLayoutX(x);
        this.setImage(IMAGE);
        this.setFitWidth(640);
        this.setFitHeight(610);
    }
}
