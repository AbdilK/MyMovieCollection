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
import MyMovieCollection.GUI.model.CollectionModel;

/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */


public class NewMovieController implements Initializable {

    private CollectionModel tm;
    @FXML
    private TextField TitleBox;
    @FXML
    private TextField ArtistBox;
    @FXML
    private TextField DurationBox;
    @FXML
    private TextField FilePathBox;
    @FXML
    private ComboBox<String> comboGenre;
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
        tm = CollectionModel.getInstance();
        movie = tm.getMovie();
        if (movie != null) {
            TitleBox.setText(movie.getTitle());
            ArtistBox.setText(movie.getArtist());
            DurationBox.setText(movie.getDuration());
            FilePathBox.setText(movie.getMoviePath());
            String movies = movie.getGenre();
            comboGenre.getSelectionModel().select(movies);
        }
        AllGenres = tm.getCategorys();
        for (String genre : AllGenres) {
            comboGenre.getItems().add(genre);
        }
    }
// This method allows us to pick the path of the while whilst we are editing or creating a movie.
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
            String filePath = "./src/MyMovieCollection/movies/";
            FilePathBox.setText(filePath);
        }
    }
// This closes the EditMovie window
    @FXML
    private void clickCancelNewMovie(ActionEvent event) // 
    {
        isEditing = false;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
// This saves that data that you put in the EditMovie window
    
    
    /** @FXML
    private void clickSaveMovie(ActionEvent event) throws IOException 
    {
        if (!isEditing) {
            if (!"".equals(DurationBox.getText()) && !"".equals(ArtistBox.getText()) && comboGenre.getSelectionModel().getSelectedItem() != null
                    && !"".equals(DurationBox.getText()) && !"".equals(FilePathBox.getText())) {
                int movieId = tm.nextAvailableMovieID();
                String title = TitleBox.getText();
                String artist = ArtistBox.getText();
                String genre = comboGenre.getSelectionModel().getSelectedItem();
                String duration = DurationBox.getText();                           
                String moviePath = FilePathBox.getText();
                tm.createMovie(movieId, title, artist, genre, duration, moviePath);;
                MainWController.refreshTableMovies();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        } else {
            if (!"".equals(DurationBox.getText()) && !"".equals(ArtistBox.getText()) && comboGenre.getSelectionModel().getSelectedItem() != null
                    && !"".equals(DurationBox.getText()) && !"".equals(FilePathBox.getText())) {
                int movieId = MovieNewID;
                String title = TitleBox.getText();
                String artist = ArtistBox.getText();
                String genre = comboGenre.getSelectionModel().getSelectedItem();
                String duration = DurationBox.getText();
                String moviePath = FilePathBox.getText();
                Movies editMovie = new Movies(movieId, artist, title, genre, duration, moviePath);
                tm.updateMovie(editMovie);
                MainWController.refreshTableMovies();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                isEditing = false;
            }
        }
    }
*/
  

    public void setController(MainWindowController controller, boolean isEditing, int movieID) // This method allows us to get connection with our MainWindowController and will check whether we are creating or editing
    {
        this.MainWController = controller;
        this.isEditing = isEditing;
        this.MovieNewID = movieID;
    }
}
