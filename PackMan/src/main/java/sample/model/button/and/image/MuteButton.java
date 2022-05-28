package sample.model.button.and.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.model.map.Map;

public class MuteButton extends ImageView {
    private final Image MUTE_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/mute.png")));
    private final Image UN_MUTE_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/unmute.png")));

    public MuteButton(int y, int x) {
        this.setLayoutY(y);
        this.setLayoutX(x);
        this.setImage(UN_MUTE_IMAGE);
        this.setFitWidth(30);
        this.setFitHeight(20);
    }

    public void mute() {
        this.setImage(MUTE_IMAGE);
    }

    public void unMute() {
        this.setImage(UN_MUTE_IMAGE);
    }
}
