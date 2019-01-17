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
    private Slider sliderMovie;
    @FXML
    private Slider sliderVolume;
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

        //sliderVolume.setValue(100);

        //sliderVolume.getParent().getParent().toFront();

        setupMovieSlider();

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
        mp.setVolume(1);

        movieView.setMediaPlayer(mp);

        mp.setOnReady(() ->
        {
            duration = mp.getMedia().getDuration();

            mp.currentTimeProperty().addListener((Observable ov) ->
            {
                updateTimeSlider();
            });
            playMedia = true;
        });

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
    private void changeVolume(MouseEvent event)
    {
         sliderVolume.valueProperty().addListener((ObservableValue<? extends Number> value, Number oldValue, Number newValue) ->
        {
            mp.setVolume(sliderVolume.getValue() / 100);
            sliderVolume.setValue(mp.getVolume() * 100);
            currentVolume.setText(String.format("%.0f", newValue) + "%");
        });
    }
    
    private void setupMovieSlider()
    {
        sliderMovie.valueProperty().addListener((Observable observable) ->
        {
            if (sliderMovie.isValueChanging())
            {
                mp.seek(mp.getTotalDuration().multiply(sliderMovie.getValue() / 100));
            }
        });
    }

    private void updateTimeSlider()
    {
        if (timeElapsed != null && sliderMovie != null && sliderVolume != null)
        {
            Platform.runLater(() ->
            {
                Duration currentTime = mp.getCurrentTime();
                timeElapsed.setText(DurationCalculator(currentTime, duration));
                sliderMovie.setDisable(duration.isUnknown());
                if (!sliderMovie.isValueChanging() && !sliderMovie.isDisabled() && duration.greaterThan(Duration.ZERO))
                {
                    sliderMovie.setValue(currentTime.divide(duration).toMillis() * 100.0);
                }
                if (!sliderVolume.isValueChanging())
                {
                    sliderVolume.setValue((int) Math.round(mp.getVolume() * 100));
                }
            });
        }
    }
    
    private void movieFullSCreen()
    {
        Stage stage = (Stage) anchorPane.getScene().getWindow();

        if (stage.isFullScreen())
        {
            stage.setFullScreen(false);
        }
        else if (!stage.isFullScreen())
        {
            stage.setFullScreen(true);
        }
    }
    
    @FXML
    private void resizeToFullScreen(ActionEvent event)
    {
        movieFullSCreen();
    }

    @FXML
    private void muteSound(MouseEvent event)
    {
        if (!muteMedia)
        {
            mp.setMute(true);
            btnMute.setGraphic(new ImageView(speakerMute));
            sliderVolume.setOpacity(0.5);
            muteMedia = !muteMedia;
        }
        else if (muteMedia)
        {
            mp.setMute(false);
            btnMute.setGraphic(new ImageView(speakerActive));
            sliderVolume.setOpacity(1);

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
        timeElapsed.setText(DurationCalculator(mp.getStartTime(), duration));
        sliderMovie.setValue(0.0);
        btnPlayPause.setText("Play Movie");
    }
       
    private static String DurationCalculator(Duration timeElapsed, Duration totalDuration)
    {
        int elapsed = (int) Math.floor(timeElapsed.toSeconds());
        int eHour = elapsed / (60 * 60);
        if (eHour > 0)
        {
            elapsed -= eHour * 60 * 60;
        }
        int eMinutes = elapsed / 60;
        int eSeconds = elapsed - eHour * 60 * 60 - eMinutes * 60;

        if (totalDuration.greaterThan(Duration.ZERO))
        {
            int duration = (int) Math.floor(totalDuration.toSeconds());
            int dHour = duration / (60 * 60);
            if (dHour > 0)
            {
                duration -= dHour * 60 * 60;
            }
            int dMinutes = duration / 60;
            int dSeconds = duration - dHour * 60 * 60
                                  - dMinutes * 60;
            if (dHour > 0)
            {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                                     eHour, eMinutes, eSeconds,
                                     dHour, dMinutes, dSeconds);
            }
            else
            {
                return String.format("%02d:%02d/%02d:%02d",
                                     eMinutes, eSeconds, dMinutes,
                                     dSeconds);
            }
        }
        else
        {
            if (eHour > 0)
            {
                return String.format("%d:%02d:%02d", eHour,
                                     eMinutes, eSeconds);
            }
            else
            {
                return String.format("%02d:%02d", eMinutes,
                                     eSeconds);
            }
        }
    }
}
