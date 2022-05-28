package sample.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPack {
    private boolean isMute = false;
    private Media backGroundMedia = new Media(getClass().getResource("/sample/music/background.mp3").toExternalForm());
    private MediaPlayer backGround = new MediaPlayer(backGroundMedia);
    private Media eatCoinMedia = new Media(getClass().getResource("/sample/music/eatCoin.mp3").toExternalForm());
    private MediaPlayer eatCoin = new MediaPlayer(eatCoinMedia);
    private Media eatEnergyBombMedia = new Media(getClass().getResource("/sample/music/eatEnergyBomb.mp3").toExternalForm());
    private MediaPlayer eatEnergyBomb = new MediaPlayer(eatEnergyBombMedia);
    private Media pacManDeathMedia = new Media(getClass().getResource("/sample/music/pacmanDeath.wav").toExternalForm());
    private MediaPlayer pacManDeath = new MediaPlayer(pacManDeathMedia);
    private Media gameOverMedia = new Media(getClass().getResource("/sample/music/gameOver.mp3").toExternalForm());
    private MediaPlayer gameOver = new MediaPlayer(gameOverMedia);
    private Media eatGhostMedia = new Media(getClass().getResource("/sample/music/pacman_eatghost.wav").toExternalForm());
    private MediaPlayer eatGhost = new MediaPlayer(eatGhostMedia);

    {
        backGround.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                backGround.stop();
                backGround.play();
            }
        });
        eatCoin.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                eatCoin.stop();
            }
        });
        eatEnergyBomb.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                eatEnergyBomb.stop();
            }
        });
        pacManDeath.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                pacManDeath.stop();
            }
        });
        gameOver.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                gameOver.stop();
            }
        });
        eatGhost.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                eatGhost.stop();
            }
        });
    }

    public void setMute(boolean mute) {
        isMute = mute;
    }

    public MediaPlayer getBackGround() {
        return backGround;
    }

    public boolean isMute() {
        return isMute;
    }

    public MediaPlayer getEatCoin() {
        eatCoin.stop();
        eatCoin = new MediaPlayer(eatCoinMedia);
        return eatCoin;
    }

    public MediaPlayer getPacManDeath() {
        pacManDeath.stop();
        pacManDeath = new MediaPlayer(pacManDeathMedia);
        return pacManDeath;
    }

    public MediaPlayer getEatEnergyBomb() {
        eatEnergyBomb.stop();
        eatEnergyBomb = new MediaPlayer(eatEnergyBombMedia);
        return eatEnergyBomb;
    }

    public MediaPlayer getEatGhost() {
        eatGhost.stop();
        eatGhost = new MediaPlayer(eatGhostMedia);
        return eatGhost;
    }


    public MediaPlayer getGameOver() {
        return gameOver;
    }
}
