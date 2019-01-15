
package MyMovieCollection.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import MyMovieCollection.BE.*;

/**
 *
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
public class CatMovieDAO
{
   DBConnectionProvider db;

    public CatMovieDAO() throws IOException
    {
        db = new DBConnectionProvider();
    }
    // returns all the movies of the category that have been selected.
    public List<Movies> getCategoryMovies(Category CatMovie) throws SQLException 
    {
        List<Movies> movies = new ArrayList<>();
        try
        {
            Connection con = db.getConnection();
            String sql = "SELECT Movie.movieId, title, ratingImdb, moviePath FROM Movie " +
                "JOIN CatMovie ON CatMovie.MovieID = Movie.movieId " +
                "JOIN Category ON CatMovie.CategoryId = Category.CategoryID WHERE Category.CategoryID = ?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, CatMovie.getCategoryId());
            ResultSet rs = ppst.executeQuery();
            while (rs.next())
            {
                int movieId = rs.getInt("movieId");
                String title = rs.getString("title");
                int ratingImdb = rs.getInt("ratingImdb");
                String moviePath = rs.getString("moviePath");
                int categoryUniqueID = rs.getInt("movieId");
                Movies movie = new Movies(movieId, title, ratingImdb, moviePath);
                movie.setCategoryUniqueID(categoryUniqueID);
                movies.add(movie);
               
            }
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return movies;
        
    }
    // when we select a movie and want to insert it into our categorymovies
    public void addMovieToCategory(Movies movie, Category category) throws SQLException 
    {
        try
        {
            Connection con = db.getConnection();
            String sql = "INSERT INTO CatMovie (MovieID, CategoryId) VALUES (?,?)";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, movie.getMovieId());
            ppst.setInt(2, category.getCategoryId());
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // when we delete movie, we also want to delete every trace of it from categoryMovies, which is done by deleting movie id.
    public void deleteMovieFromCategoryMovies(int id) throws SQLException  
    {
        try
        {
            Connection con = db.getConnection();
            String sql = "DELETE FROM CatMovie WHERE MovieID=?";                  
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, id);
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // when we delete a category, we want to delete every record from categoryMovies, which is having the deleted category id.
    public void deleteCategoryFromCategoryMovies(int id) throws SQLException 
    {
        try
        {
            Connection con = db.getConnection();
            String sql = "DELETE FROM CatMovie WHERE CategoryId=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, id);
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // This method switches the positions of two movies in the category
    public void reCreateCategoryMovies(Movies selected, Movies replace) throws SQLException 
    {
        try
        {
            Connection con = db.getConnection();
            int selectedID = selected.getMovieId();   
            int replaceID = replace.getMovieId();   
            String sql = "UPDATE CatMovie SET MovieID = ? WHERE ID = ?";    
            String sqll = "UPDATE CatMovie SET MovieID = ? WHERE ID = ?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, replaceID);                       
            ppst.setInt(2, selected.getCategoryUniqueID());     
            PreparedStatement ppst2 = con.prepareCall(sqll);    
            ppst2.setInt(1, selectedID);
            ppst2.setInt(2, replace.getCategoryUniqueID());
            ppst.execute();
            ppst2.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 

