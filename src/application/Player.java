/**
 * Created by MK on 02.09.2017
 */

package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class Player extends BorderPane{
    private Media media;
    private MediaPlayer player;
    private MediaView view;
    private Pane mpane;
    private MediaBar mediaBar;

    public MediaPlayer getPlayer() {
        return player;
    }

    Player(String filePath){
        media = new Media(filePath);
        player = new MediaPlayer(media);
        view  = new MediaView(player);        
        mpane = new Pane();
        mpane.getChildren().add(view);
        setCenter(mpane);
        mediaBar = new MediaBar(player);
        setBottom(mediaBar);
        setStyle("-fx-background-color: #000");
        player.play();
    }
}