package sample.model.ghost;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.model.PacMan;
import sample.model.map.Map;

public class Ghost extends ImageView {
    private final Image DEFAULT_IMAGE;
    private final Image SCAPE_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/scapeghost.gif")));
    private final Image RED_GHOST_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/redghost.gif")));
    private final Image BLUE_GHOST_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/blueghost.gif")));
    private final Image PINK_GHOST_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/pinkghost.gif")));
    private final Image YELLOW_GHOST_IMAGE = new Image(String.valueOf(Map.class.getResource("/sample/pic/yellowghost.gif")));
    private double xPosition;
    private double yPosition;

    public Ghost(GhostColor ghostColor) {
        if (ghostColor == GhostColor.RED) this.DEFAULT_IMAGE = RED_GHOST_IMAGE;
        else if (ghostColor == GhostColor.BLUE) this.DEFAULT_IMAGE = BLUE_GHOST_IMAGE;
        else if (ghostColor == GhostColor.YELLOW) this.DEFAULT_IMAGE = YELLOW_GHOST_IMAGE;
        else this.DEFAULT_IMAGE = PINK_GHOST_IMAGE;
        this.setImage(DEFAULT_IMAGE);
        this.setFitWidth(30);
        this.setFitHeight(30);
    }

    public double getXPosition() {
        return xPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
        this.setLayoutX(xPosition);
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
        this.setLayoutY(yPosition);
    }

    public boolean hasCollision(PacMan pacMan) {
        int y = (int) pacMan.getYPosition();
        int x = (int) pacMan.getXPosition();
        return isPositionInGhost(y + 1, x + 1) ||
                isPositionInGhost(y + 29, x + 1) ||
                isPositionInGhost(y + 1, x + 29) ||
                isPositionInGhost(y + 29, x + 29);
    }

    private boolean isPositionInGhost(int y, int x) {
        return (int) this.xPosition <= x &&
                (int) this.xPosition + 30 >= x &&
                (int) this.yPosition <= y &&
                (int) this.yPosition + 30 >= y;
    }

    public void changePictureToScapeGhost() {
        this.setImage(SCAPE_IMAGE);
    }

    public void changePictureToDefaultPicture() {
        this.setImage(DEFAULT_IMAGE);
    }
}
