package MyMovieCollection.GUI.controller;


import java.net.URL;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javax.sound.sampled.UnsupportedAudioFileException;
import MyMovieCollection.BE.Category;
import MyMovieCollection.BE.Movies;
import MyMovieCollection.GUI.model.CollectionModel;

/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */

public class MainWindowController implements Initializable
{

    @FXML
    private TextField textFieldFilterSearch;
    @FXML
    private Label lblMovieTitle;
    @FXML
    private TableView<Category> tblViewCategorys;
    @FXML
    private TableView<Movies> tblViewLibrary;
  
    
    
    private Button exitBtn;
    private CollectionModel tm;
    private String moviePath;
    private Media mp;
    private int Length;
    private Movies movie = null;
    private Movies movie1 = null;
    private Duration movieDuration;
    private ObservableList moviesAsObservable;
    private ObservableList<Category> categorysAsObservable;

    private ObservableList searchedMoviesAsObservable;
    @FXML
    private TableColumn<Movies, String> tblViewLibraryColumnTitle;
    @FXML
    private TableColumn<Movies, String> tblViewLibraryColumnRatingImdb;
    @FXML
    private TableColumn<Movies, String> tblViewLibraryColumnRatingPersonal;
    @FXML
    private TableColumn<Movies, String> tblViewLibraryColumnMoviePath;
    private ProgressBar progressBar;
    private TableColumn<Category, String> categoryNameCol;
    private TableColumn<Category, Integer> categoryMoviesCol;
    private TableColumn<Category, String> categoryDurationCol;
    private ProgressBar movieProgress;
    private Label movieTimeLabel;
    @FXML
    private Label currentTimeLabel;
    private Slider progressSlider;
    @FXML
    private AnchorPane grey;

    @FXML
    private Button btnDeleteCategorys;
    @FXML
    private Label labelCurrentlyPlaying;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnReplay;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnPreviousMovie;
    @FXML
    private Button btnNext;
    @FXML
    private ListView<Movies> ViewMoviesOnCategory;
    // This initializes our observables, progressbar, volumenSlider and such.
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        categorysAsObservable = FXCollections.observableArrayList();
        searchedMoviesAsObservable = FXCollections.observableArrayList();
      
        tm = CollectionModel.getInstance();
        setMoviesTable();
        setCategoryTable();
    }
    // The method underneath gets all movies from our database and loads it into our movie library table, with the given string.
    public void setMoviesTable() 
    {

        
        moviesAsObservable = FXCollections.observableArrayList(tm.getMoviesAsObservable());
        tblViewLibraryColumnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        tblViewLibraryColumnRatingImdb.setCellValueFactory(new PropertyValueFactory<>("RatingImdb"));
        tblViewLibraryColumnRatingPersonal.setCellValueFactory(new PropertyValueFactory<>("RatingPersonal"));
        tblViewLibraryColumnMoviePath.setCellValueFactory(new PropertyValueFactory<>("MoviePath"));
        tblViewLibrary.getColumns().clear();
        tblViewLibrary.setItems(moviesAsObservable);
        tblViewLibrary.getColumns().addAll(tblViewLibraryColumnTitle, tblViewLibraryColumnRatingImdb, tblViewLibraryColumnRatingPersonal, tblViewLibraryColumnMoviePath);

    }
   // The method underneath gets all categorys from our database and loads it into our category library table, with the given string.
    private void setCategoryTable() 
    {
        categoryMoviesCol = new TableColumn<>("ID");    
        categoryNameCol = new TableColumn<>("Name");
        
        //categoryDurationCol = new TableColumn<>();
        categoryMoviesCol.setCellValueFactory(new PropertyValueFactory<>("CategoryId"));
        categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        tblViewCategorys.setItems(tm.getCategorysAsObservable());
        tblViewCategorys.getColumns().addAll(categoryNameCol, categoryMoviesCol);

    }
    // This removes a movie from a chosen category, but does not delete the movie from our database.
    @FXML
    private void clickRemoveMovieCategory(ActionEvent event)
    {
        if (ViewMoviesOnCategory.getSelectionModel().getSelectedItem() != null)
        {
            Movies movie = ViewMoviesOnCategory.getSelectionModel().getSelectedItem();
            tm.deleteMovieFromCategoryMovies(movie.getCategoryUniqueID());
            ViewMoviesOnCategory.getItems().clear();
            Category category = tblViewCategorys.getSelectionModel().getSelectedItem();
            int index = tblViewCategorys.getSelectionModel().getSelectedIndex();
            List<Movies> List = tm.getCategoryMovies(category);
            ViewMoviesOnCategory.getItems().addAll(List);
            refreshTableCategory();
            tblViewCategorys.refresh();
            tblViewCategorys.getSelectionModel().select(index);
        }
    }
    // This method deletes a specific movie from our MusicLibrary and/or from our database.
    // An alert box has been implemented to ask the user, if he/she wishes to delete the specifi movie from
    // the database, or the MusicLibrary folder only.
    @FXML
    private void clickDeleteMovie(ActionEvent event)
    {
        Movies ToDeleteMovie = tblViewLibrary.getSelectionModel().getSelectedItem();
        if (ToDeleteMovie != null)
        {
            String name = ToDeleteMovie.getTitle() + " " + ToDeleteMovie.getArtist();
            Alert alert = new Alert(AlertType.CONFIRMATION, "Click YES to delete the chosen movie " + name + " from the database.\nClick NO to cancel your current action", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK)
            {
                if (ToDeleteMovie != null)
                {
                    tm.deleteMovie(ToDeleteMovie);
                    tm.deleteMovieFromCategoryMovies(ToDeleteMovie.getMovieId());
                    refreshTableMovies();
                }
            } else if (alert.getResult() == ButtonType.YES)
            {
                tm.deleteMovie(ToDeleteMovie);
                tm.deleteMovieFromCategoryMovies(ToDeleteMovie.getMovieId());
                refreshTableMovies();
                try
                {
                    File file = new File(ToDeleteMovie.getMoviePath());
                    Files.delete(file.toPath());
                } catch (IOException ex)
                {
                    Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
//This method deletes a specific Category from our tblViewCategory table to the left.
    @FXML
    private void clickDeleteCategory(ActionEvent event)
    {
        if (tblViewCategorys.getSelectionModel().getSelectedItem() != null)
        {
            String name = tblViewCategorys.getSelectionModel().getSelectedItem().getCategoryName();
            Alert alert = new Alert(AlertType.CONFIRMATION, "DELETE " + name + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES)
            {
                Category categoryToDelete = tblViewCategorys.getSelectionModel().getSelectedItem();
                if (categoryToDelete.getMovieAmountPL() > 0)
                {
                    tm.deleteCategoryFromCategoryMovies(categoryToDelete.getCategoryId());
                }
                tm.deleteCategory(categoryToDelete);
                refreshTableCategory();
            }
        }
    }

    
// This is playing our musicplayer, which gets the movie information from a movies given title and artist.
   
    

    private void search()
    {
        String text = textFieldFilterSearch.getText();
        List<Movies> Search = tm.searchMovie(text);
        searchedMoviesAsObservable.clear();
        searchedMoviesAsObservable.addAll(Search);
        if (Search.size() > 0 && text.length() > 0)
        {
            tblViewLibrary.setItems(searchedMoviesAsObservable);
        } else if (Search.isEmpty() && text.length() > 0)
        {
            tblViewLibrary.getItems().clear();
        } else if (Search.size() > 0 && text.length() == 0)
        {
            refreshTableMovies();
        }
    }

    private void getMoviesFromCategory(MouseEvent event)
    {
        Category category = tblViewCategorys.getSelectionModel().getSelectedItem();
        ObservableList obsList = FXCollections.observableArrayList(tm.getCategoryMovies(category));
        ViewMoviesOnCategory.setItems(obsList);
    }

    @FXML
    private void clickPushMovieToCategory(ActionEvent event)
    {
        if (tblViewLibrary.getSelectionModel().getSelectedItem() != null)
        {
            Movies movie = tblViewLibrary.getSelectionModel().getSelectedItem();

            moviesAsObservable.add(movie);
            ViewMoviesOnCategory.getItems().clear();
            ViewMoviesOnCategory.getItems().addAll(moviesAsObservable);

            Category category = tblViewCategorys.getSelectionModel().getSelectedItem();
            int index = tblViewCategorys.getSelectionModel().getSelectedIndex();
            tm.addMovieToCategory(movie, category);
            ViewMoviesOnCategory.getItems().clear();
            ViewMoviesOnCategory.getItems().addAll(tm.getCategoryMovies(category));
            refreshTableCategory();
            tblViewCategorys.refresh();
            tblViewCategorys.getSelectionModel().select(index);
        }
    }

    @FXML
    private void HitEnterSearch(KeyEvent event)
    {
        if (event.getCode() == KeyCode.ENTER && textFieldFilterSearch.isFocused())
        {
            search();
        }
    }
    
    public void refreshTableMovies()
    {
        tblViewLibrary.getItems().clear();
        tblViewLibrary.setItems(tm.getMoviesAsObservable());
    }

    public void refreshTableCategory()
    {
        tblViewCategorys.getItems().clear();
        tblViewCategorys.setItems(tm.getCategorysAsObservable());
    }
    
    // This will bind a chosen button to open up our NewMovie window
    public void openMovieWindow(String fxmlPath, int id, boolean isEditing)
    {
        try
        {
            Parent roots;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            roots = (Parent) fxmlLoader.load();
            fxmlLoader.<NewMovieController>getController().setController(this, isEditing, id);
            Stage stage = new Stage();
            stage.setScene(new Scene(roots));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // This will bind a chosen button to open up our Category window

    public void openCategoryWindow(String fxmlPath, int id, boolean isEditing)
    {
        try
        {
            Parent roots;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            roots = (Parent) fxmlLoader.load();
            fxmlLoader.<CategoryWindowController>getController().setController(this, isEditing, id);
            Stage stage = new Stage();
            stage.setScene(new Scene(roots));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // This will bind a chosen button to open up our EditMovie window

    public void openEditWindow(String fxmlPath, int id, boolean isEditing)
    {
        try
        {
            Parent roots;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            roots = (Parent) fxmlLoader.load();
            fxmlLoader.<EditMovieController>getController().setController(this, isEditing, id);
            Stage stage = new Stage();
            stage.setScene(new Scene(roots));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void clickNewCategory(ActionEvent event) throws IOException
    {
        int id = 0;
        String fxmlPath = "/MyMovieCollection/GUI/view/CategoryWindow.fxml";
        Category category = null;
        tm.setCategory(category);
        boolean isEditing = false;
        openCategoryWindow(fxmlPath, id, isEditing);
    }

    @FXML
    private void clickNewMovie(ActionEvent event)
    {
        int id = 0;
        String fxmlPath = "/MyMovieCollection/GUI/view/NewMovie.fxml";
        Movies Movie = null;
        tm.setMovie(Movie);
        boolean isEditing = false;
        openMovieWindow(fxmlPath, id, isEditing);
    }

    @FXML
    private void clickEditMovies(ActionEvent event)
    {
        Movies MovieToEdit = tblViewLibrary.getSelectionModel().getSelectedItem();
        tm.setMovie(MovieToEdit);
        if (tblViewLibrary.getSelectionModel().getSelectedItem() != null)
        {
            int id = tblViewLibrary.getSelectionModel().getSelectedItem().getMovieId();
            String fxmlPath = "/MyMovieCollection/GUI/view/EditMovie.fxml";
            boolean isEditing = true;
            openEditWindow(fxmlPath, id, isEditing);
        }
    }
    
    @FXML
    private void clickToEditCategory(ActionEvent event)
    {
        Category category = tblViewCategorys.getSelectionModel().getSelectedItem();
        tm.setCategory(category);
        if (tblViewCategorys.getSelectionModel().getSelectedItem() != null)
        {
            int id = tblViewCategorys.getSelectionModel().getSelectedItem().getCategoryId();
            String fxmlPath = "/MyMovieCollection/GUI/view/CategoryWindow.fxml";
            boolean isEditing = true;
            openCategoryWindow(fxmlPath, id, isEditing);
        }
    }

/*
    @FXML
    private void dblClickPlay(MouseEvent event) throws IOException
    {
        if (event.getClickCount() == 2)
        {
            try
            {
                if (!isPlaying)
                {
                    isPlaying = true;
                    playSelectedMovie();
                    mediaPlayer.setMute(muted);

                } else
                {
                    isPlaying = true;
                    playSelectedMovie();
                    mediaPlayer.setMute(muted);

                }
            } catch (UnsupportedAudioFileException ex)
            {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
*/
    @FXML
    private void ExitCollection(MouseEvent event)
    {
        System.exit(1);
    }
    
    @FXML
    private void clickCategory(MouseEvent event)
    {
        if (tblViewCategorys.getSelectionModel().getSelectedItem() != null)
        {
            ViewMoviesOnCategory.getItems().clear();
            Category category = tblViewCategorys.getSelectionModel().getSelectedItem();
            List<Movies> list = tm.getCategoryMovies(category);
            ViewMoviesOnCategory.getItems().addAll(list);
        }
    }

  
    private String currentDurationCalc(int timeSec)
    {
        int minutes = timeSec / 60;
        int seconds = timeSec % 60;
        if (seconds < 10)
        {
            return minutes + ":0" + seconds;
        } else
        {
            return minutes + ":" + seconds;
        }
    }
}
