package sample.model.button.and.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.model.map.Map;

public class StopButton extends ImageView {
    private final Image PLAY_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/play.png")));
    private final Image PAUSE_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/pause.png")));
    private boolean isGameStop;

    public StopButton(int y, int x) {
        this.setLayoutY(y);
        this.setLayoutX(x);
        this.setImage(PAUSE_IMAGE);
        this.setFitWidth(30);
        this.setFitHeight(20);
        this.isGameStop = false;
    }

    public void pause() {
        this.isGameStop = true;
        this.setImage(PLAY_IMAGE);
    }

    public void play() {
        this.isGameStop = false;
        this.setImage(PAUSE_IMAGE);
    }

    public boolean isGameStop() {
        return isGameStop;
    }
}
