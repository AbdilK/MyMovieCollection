package MyMovieCollection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import MyMovieCollection.BE.Movies;

/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
public class MyMovieCollection extends Application
{
    
    @Override
    public void start(Stage pStage) throws Exception
    {
        pStage.setTitle("IMDB Movie Collection");
        pStage.centerOnScreen();
        pStage.getIcons().add(new Image("MyMovieCollection/icons/imdbRed.png"));
        Parent root = FXMLLoader.load(getClass().getResource("GUI/view/MainWindow.fxml")); //getClassLoader added to avoid NullPointerException
        Scene scene = new Scene(root);    
        pStage.setScene(scene);
        pStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
        
     
    }
    
}