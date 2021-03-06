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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JFrame;
import MyMovieCollection.BE.Movies;
import MyMovieCollection.GUI.model.MovieModel;

/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
public class NewMovieController implements Initializable {

    private MovieModel mm;
    @FXML
    private TextField TitleBox;
    @FXML
    private TextField ImdbBox;
    @FXML
    private TextField PersonalBox;
    @FXML
    private TextField MoviePathBox;
    @FXML
    private ComboBox<String> comboCategory;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mm = MovieModel.getInstance();
        movie = mm.getMovie();  
        if (movie != null) {
            TitleBox.setText(movie.getTitle());
            ImdbBox.setText(movie.getRatingImdb()+"");
            PersonalBox.setText(movie.getRatingPersonal()+"");
            MoviePathBox.setText(movie.getMoviePath());
           
        }
    }
/** 
 * denne her metode kan man vækge hvilken film man skal vælge.
 */
    @FXML
    private void clickChooseMovie(ActionEvent event) throws IOException 
    {
        FileDialog fileD = new FileDialog(new JFrame());
        fileD.setVisible(true);
        File[] Path = fileD.getFiles();
        Path[0].getName();
        if (Path.length > 0) 
        {
            
            TitleBox.setText(Path[0].getName());
            String filePath = "./src/MyMovieCollection/movies/" + Path[0].getName();
            MoviePathBox.setText(filePath);
        }
    }
/** 
 * denne her metode lukker selve vinduet ned  
 */ 
    @FXML
    private void clickCancelNewMovie(ActionEvent event) 
    {
        isEditing = false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
/** 
 * denne her methode gemmer dataen man har puttet ind i editmovie vindue 
 */
    
  
    @FXML
    private void clickSaveMovie(ActionEvent event) throws IOException 
    {
        if (!isEditing) {
            if (!"".equals(ImdbBox.getText())
                    && !"".equals(PersonalBox.getText()) && !"".equals(MoviePathBox.getText())) {
                int movieId = mm.nextAvailableMovieID();
                String title = TitleBox.getText();
                double ratingImdb = Double.parseDouble(ImdbBox.getText());
                double ratingPersonal = Double.parseDouble(PersonalBox.getText());                
                String moviePath = MoviePathBox.getText();
                mm.createMovie(movieId, title, ratingImdb, ratingPersonal, moviePath);;
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
                Movies editMovie = new Movies(movieId, title, ratingImdb, ratingPersonal, moviePath);;
                mm.updateMovie(editMovie);
                MainWController.refreshTableMovies();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                isEditing = false;
            }
        }
    }

    /**
     * denne methode giver os muligheden at få forbindelse til MainWindowController og vil tjekke om vi creater eller ændre noget.
     * @param controller 
     * @param isEditing: at den ændre på noget. 
     * @param movieID: selve filmens id. 
     */
    public void setController(MainWindowController controller, boolean isEditing, int movieID) 
    {
        this.MainWController = controller;
        this.isEditing = isEditing;
        this.MovieNewID = movieID;
    }
}
