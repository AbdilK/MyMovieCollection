package MyMovieCollection.BLL;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import MyMovieCollection.BLL.exception.BLLException;
import MyMovieCollection.DAL.MovieDAO;
import MyMovieCollection.DAL.exception.DALException;
import java.io.IOException;

/**
 *
 * @author Abdil-K
 */
public class UserRatings {

    private MovieDAO movDAO;
    List<ImageView> starRatingWhite;
    List<ImageView> starRatingYellow;
    private int ratingStars;
    private Label ratingLabel;
    

    public UserRatings(int movieId, double userRating, GridPane ratingPane, Label ratingLabel) throws BLLException, FileNotFoundException, IOException {
        {
            movDAO = new MovieDAO();

        starRatingYellow = new ArrayList();
        starRatingWhite = new ArrayList();
        this.ratingLabel = ratingLabel;
        ratingStars = (int) userRating;
        
        for(int i = 0; i < 10; i++)
        {
            ImageView isRated = new ImageView("/MyMovieCollection/icons/starRatingYellow.png");
            saveNewUserRating(isRated, ratingPane, movieId);
            setRatingClick(isRated, ratingPane);
            starRatingYellow.add(isRated);

            ImageView isNotRated = new ImageView("/MyMovieCollection/icons/starRatingWhite.png");
            saveNewUserRating(isNotRated, ratingPane, movieId);
            setRatingClick(isNotRated, ratingPane);
            starRatingWhite.add(isNotRated);
        }
        moveMouseOutOfPane(ratingPane);
        setRatingStars(ratingPane);
        }
    }
    
    private void mouseOnRating(GridPane ratingPane, int hoverStar)
    {
        ratingLabel.setText(Integer.toString(hoverStar +1));
        ratingPane.getChildren().clear();
        for (int i = 0; i < 10; i++)
        {
            if (i <= hoverStar)
            {
                ratingPane.setColumnIndex(starRatingYellow.get(i), i);
                ratingPane.getChildren().add(starRatingYellow.get(i));
            }
            else
            {
                ratingPane.setColumnIndex(starRatingWhite.get(i), i);
                ratingPane.getChildren().add(starRatingWhite.get(i));
            }
        }
    }
    
    private void setRatingStars(GridPane ratingPane)
    {
        for (int i = 1; i < 11; i++)
        {
            if (i <= ratingStars)
            {
                ratingPane.setColumnIndex(starRatingYellow.get(i - 1), i - 1);
                ratingPane.getChildren().add(starRatingYellow.get(i - 1));
            }
            else
            {
                ratingPane.setColumnIndex(starRatingWhite.get(i - 1), i - 1);
                ratingPane.getChildren().add(starRatingWhite.get(i - 1));
            }
        }
        ratingLabel.setText(Integer.toString(ratingStars));

    }
    
    private void setRatingClick(ImageView view, GridPane ratingPane)
    {
        view.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent MouseEvent)
            {
                Node n = (Node) MouseEvent.getSource();
                mouseOnRating(ratingPane, ratingPane.getColumnIndex(n));
            }
        }
        );
    }
    
    private void moveMouseOutOfPane(GridPane ratingPane)
    {
        ratingPane.setOnMouseExited(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent MouseEvent)
            {
                ratingPane.getChildren().clear();
                setRatingStars(ratingPane);
            }
        }
        );
    }  

    private void saveNewUserRating(ImageView view, GridPane ratingPane, int movieId) throws BLLException 
    {
        view.setOnMouseClicked(new EventHandler <MouseEvent>() {
            @Override
            public void handle(MouseEvent MouseEvent) {
                Node n = (Node) MouseEvent.getSource();

                try
                {
                    movDAO.setRatingPersonal(movieId, ratingPane.getColumnIndex(n) + 1);
                }
                catch (DALException ex)
                {
                }
                ratingStars = ratingPane.getColumnIndex(n) + 1;
                moveMouseOutOfPane(ratingPane);
            }
        }
        );
    }

}