/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyMovieCollection.GUI.controller;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import MyMovieCollection.GUI.model.MovieModel;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Abdil-K
 */
public class MediaPlayerWindowController implements Initializable
{
    @FXML
    private Button btnPlayPause;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnMute;
    @FXML
    private Button btnClose;
    private Image playPause;
    private Image playPlay;
    private Image playStop;
    private Image speakerActive;
    private Image speakerMute;
    private Image MediaClose;
    private MediaPlayer mp;
    private MediaView movieView;
    private ImageView imageView;
    private boolean isPlaying;
    private boolean muteMedia;
    private Media movieMedia;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
   @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setIcons();
    }    
    
    /**
     *
     * @param cm
     * @param moviePath
     */
    public void MediaSetup(MovieModel cm, String moviePath)
    {
        isPlaying = false;

        DoubleProperty width = movieView.fitWidthProperty();
        DoubleProperty height = movieView.fitHeightProperty();
        width.bind(Bindings.selectDouble(movieView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(movieView.sceneProperty(), "height"));
        movieView.setPreserveRatio(false); 

        String path = moviePath;

        movieMedia = new Media(new File(path).toURI().toString());

        mp = new MediaPlayer(movieMedia);
        mp.setAutoPlay(true);

        movieView.setMediaPlayer(mp);
        mp.setOnEndOfMedia(() ->
        {
            //resetPlayButton();
            btnPlayPause.setGraphic(new ImageView(playPlay));
            mp.pause();
        });
    }
    
    /**
     *
     * @param movieView
     */
    public void setImageView(ImageView movieView)
    {
        this.imageView = movieView;
    }
    
    private void setIcons()
    {
        playPlay = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/play-play.png"));
        playPause = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/play-pause.png"));
        playStop = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/play-stop.png"));
        speakerActive = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/speaker-active.png"));
        speakerMute = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/speaker-mute.png"));
        MediaClose = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/MediaClose.png"));

        btnPlayPause.setGraphic(new ImageView(playPause));
        btnPlayPause.setText("");

        btnStop.setGraphic(new ImageView(playStop));
        btnStop.setText("");

        btnMute.setGraphic(new ImageView(speakerActive));
        btnMute.setText("");
        
        btnClose.setGraphic(new ImageView(MediaClose));
        btnClose.setText("");
    }

    
    @FXML
    private void playPauseMovie(ActionEvent event)
    {
        if (!isPlaying)
        {
            mp.play();
            btnPlayPause.setGraphic(new ImageView(playPause));
            isPlaying = !isPlaying;
        }
        else if (isPlaying)
        {
            mp.pause();
            btnPlayPause.setGraphic(new ImageView(playPlay));
            isPlaying = !isPlaying;
        }
    }
    

    @FXML
    private void stopMovie(ActionEvent event)
    {
        mp.pause();
        mp.seek(mp.getStartTime());
        btnPlayPause.setGraphic(new ImageView(playPlay));
    }

    @FXML
    private void muteSound(MouseEvent event)
    {
          if (!muteMedia)
        {
            mp.setMute(true);
            btnMute.setGraphic(new ImageView(speakerMute));
            
            muteMedia = !muteMedia;
        }
        else if (muteMedia)
        {
            mp.setMute(false);
            btnMute.setGraphic(new ImageView(speakerActive));
            

            muteMedia = !muteMedia;
        }
    }

    @FXML
    private void MediaClose(ActionEvent event)
    {
        btnClose.setGraphic(new ImageView(MediaClose));
        mp.stop();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
  
   
    
    
}
