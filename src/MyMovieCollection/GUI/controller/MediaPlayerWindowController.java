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
    private AnchorPane anchorPane;
    
    @FXML
    private Label timeElapsed;
    @FXML
    private Label currentVolume;
    @FXML
    private MediaView movieView;
    private ImageView imageView;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Button btnPlayPause;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnMute;
    private Duration duration;
    private Image playPause;
    private Image playPlay;
    private Image playStop;
    private Image speakerActive;
    private Image speakerMute;
    private MediaPlayer mp;
    private boolean boolPlaying;
    private boolean playMedia;
    private boolean muteMedia;
    private Media movieMedia;
    

    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setControls();

       

      

    }    
    
    public void MediaSetup(MovieModel cm, String moviePath)
    {
        boolPlaying = false;

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
            resetPlayButton();
            mp.pause();
        });
    }
    
    private void setControls()
    {
        playPlay = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/play-play.png"));
        playPause = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/play-pause.png"));
        playStop = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/play-stop.png"));
        speakerActive = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/speaker-active.png"));
        speakerMute = new Image(getClass().getResourceAsStream("/MyMovieCollection/icons/speaker-mute.png"));

        btnPlayPause.setGraphic(new ImageView(playPause));
        btnPlayPause.setText("");

        btnStop.setGraphic(new ImageView(playStop));
        btnStop.setText("");

        btnMute.setGraphic(new ImageView(speakerActive));
        btnMute.setText("");
    }

    public void setImageView(ImageView movieView)
    {
        this.imageView = movieView;
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
    private void playPauseMovie(MouseEvent event)
    {
        if (!boolPlaying && playMedia)
        {
            mp.play();
            btnPlayPause.setGraphic(new ImageView(playPause));
            boolPlaying = !boolPlaying;
        }
        else if (boolPlaying)
        {
            mp.pause();
            btnPlayPause.setGraphic(new ImageView(playPlay));
            boolPlaying = !boolPlaying;
        }
    }

    @FXML
    private void stopMovie(MouseEvent event)
    {
        mp.pause();
        mp.seek(mp.getStartTime());
        resetPlayButton();
    }
    public void stopMovieEnterily()
    {
        mp.stop();
    }
    
    private void resetPlayButton()
    {
     
       
        btnPlayPause.setText("Play Movie");
    }
       
    
}
