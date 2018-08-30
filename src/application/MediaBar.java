/**
 * Created by MK on 02.09.2017
 */

package application;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;

public class MediaBar extends HBox{
    private MediaPlayer mediaPlayer;
    private Slider timeSlider = new Slider();
    private Slider volumeSlider = new Slider();
    private Button playButton = new Button("||");
    private Label volumeLabel = new Label("Volume: ");

    MediaBar(MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10,10,10,10));
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMinWidth(30);

        volumeSlider.setValue(100);
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        playButton.setPrefWidth(30);

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MediaPlayer.Status status = mediaPlayer.getStatus();
                if(status == MediaPlayer.Status.PLAYING) {
                    if(mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())) {
                        mediaPlayer.seek(mediaPlayer.getStartTime());
                        mediaPlayer.play();
                    }
                    else {
                        mediaPlayer.pause();
                        playButton.setText(">");
                    }

                }
                if(status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.HALTED || status == MediaPlayer.Status.STOPPED) {
                    mediaPlayer.play();
                    playButton.setText("||");
                }
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateValuses();
            }
        });

        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(timeSlider.isPressed())
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(timeSlider.getValue()/100));
            }
        });

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(volumeSlider.isPressed())
                    mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });

        getChildren().add(playButton);
        getChildren().add(timeSlider);
        getChildren().add(volumeLabel);
        getChildren().add(volumeSlider);

    }

    private void updateValuses() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timeSlider.setValue(mediaPlayer.getCurrentTime().toMillis()/mediaPlayer.getTotalDuration().toMillis()*100);
            }
        });
    }

}