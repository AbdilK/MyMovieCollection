package MyMovieCollection.GUI.controller;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JFrame;
import MyMovieCollection.BE.Movies;
import MyMovieCollection.GUI.model.MovieModel;

/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */



public class EditMovieController implements Initializable {

    private MovieModel tm;
    @FXML
    private TextField TitleBox;
    @FXML
    private TextField ImdbBox;
    @FXML
    private TextField PersonalBox;
    @FXML
    private TextField MoviePathBox;
    private MainWindowController MainWController;
    private boolean isEditing = false;
    private int MovieNewID;
    private List<String> AllGenres;
    private Movies movie;
    @FXML
    private Button btn_Choose;
    @FXML
    private Button btn_Save;
    @FXML
    private Button btn_Cancel;

    /** 
     * denne metode Initialiserer controller klassen
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tm = MovieModel.getInstance();
        movie = tm.getMovie();
        if (movie != null) {
            TitleBox.setText(movie.getTitle());
            ImdbBox.setText(movie.getRatingImdb()+"");
            PersonalBox.setText(movie.getRatingPersonal()+"");
            MoviePathBox.setText(movie.getMoviePath());
            
        }
        
    }
/** 
 * denne metode tillader os at vælge stien filmen tilhører, mens vi redigere eller tilføjer/laver en ny film
 */ @FXML
    private void clickChooseMovie(ActionEvent event) throws IOException 
    {
        FileDialog fileD = new FileDialog(new JFrame());
        fileD.setVisible(true);
        File[] Path = fileD.getFiles();
        if (Path.length > 0) {
            String filePath = "src\\MyMovieCollection\\movies" + fileD.getFiles()[0].getName();
            MoviePathBox.setText(filePath);
        }
    }
/** 
 * denne metode lukker EditMovie vinduet. når vi er i EditMovie og vi gerne vil annullere. 
 */
    @FXML
    private void clickCancelEditMovie(ActionEvent event) 
    {
        isEditing = false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
/** 
 * denne metode gemmer de data du putter ind i EditMovie vinduet. 
 * f.eks. din egen personlige rating af filmen, rating fundet på imdb's hjemmeside, titlen, id... osv.
 * @param event
 * @throws IOException 
 */
    @FXML
    private void clickSaveEditsMovie(ActionEvent event) throws IOException
    {
        if (!isEditing) {
            if (!"".equals(ImdbBox.getText()) && !"".equals(PersonalBox.getText()) && !"".equals(MoviePathBox.getText())) {
                int movieId = tm.nextAvailableMovieID();
                String title = TitleBox.getText();
                double ratingImdb = Double.parseDouble(ImdbBox.getText());
                double ratingPersonal = Double.parseDouble(PersonalBox.getText());                
                String moviePath = MoviePathBox.getText();
                tm.createMovie(movieId, title, ratingImdb, ratingPersonal, moviePath);
                MainWController.refreshTableMovies();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        } else {
            if (!"".equals(ImdbBox.getText()) && !"".equals(PersonalBox.getText()) && !"".equals(MoviePathBox.getText())) {
                int movieId = MovieNewID;
                String title = TitleBox.getText();
                double ratingImdb = Double.parseDouble(ImdbBox.getText());
                double ratingPersonal = Double.parseDouble(PersonalBox.getText());                
                String moviePath = MoviePathBox.getText();
                Movies editMovie = new Movies(movieId, title, ratingImdb, ratingPersonal, moviePath);
                tm.updateMovie(editMovie);
                MainWController.refreshTableMovies();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                isEditing = false;
            }
        }
    }
    
    /**
     * denne metode tillader os at få forbindelse med vores MainWindowController og den vil checke om vi laver en film eller redigere
     * @param controller
     * @param isEditing
     * @param movieID
     */
    public void setController(MainWindowController controller, boolean isEditing, int movieID)
    {
        this.MainWController = controller;
        this.isEditing = isEditing;
        this.MovieNewID = movieID;
    }

    @FXML
    private void ClickCancelEditMovie(ActionEvent event)
    {
    }
}
