
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

    /**
     * Opretter forbindelse til databasen
     * @throws IOException
     */
    public CatMovieDAO() throws IOException
    {
        db = new DBConnectionProvider();
    }
   
    /**
     * Returnere alle film, som er tilføjet til en kategori
     * @param CatMovie Film kategori, bruges til at sætte en ID for selve kategorien
     * @return returnere filmene
     * @throws SQLException kaster en SQL exception, når en fejl opstår
     */
    public List<Movies> getCategoryMovies(Category CatMovie) throws SQLException 
    {
        List<Movies> movies = new ArrayList<>();
        try (Connection con = db.getConnection())
        {
            
            String sql = "SELECT Movie.movieId, title, ratingImdb, ratingPersonal, moviePath FROM Movie " +
                "JOIN CatMovie ON CatMovie.MovieID = Movie.movieId " +
                "JOIN Category ON CatMovie.CategoryId = Category.CategoryID WHERE Category.CategoryID = ?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, CatMovie.getCategoryId());
            ResultSet rs = ppst.executeQuery();
            while (rs.next())
            {
                int movieId = rs.getInt("movieId");
                String title = rs.getString("title");
                double ratingImdb = rs.getInt("ratingImdb");
                double ratingPersonal = rs.getInt("ratingPersonal");
                String moviePath = rs.getString("moviePath");
                int categoryUniqueID = rs.getInt("movieId");
                if(title.contains("."))
                {
                    title = title.split("\\.")[0];

                }
                Movies movie = new Movies(movieId, title, ratingImdb, ratingPersonal, moviePath);
                movie.setCategoryUniqueID(categoryUniqueID);
                movies.add(movie);
               
            }
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return movies;
        
    }
    

    /**
     * Tilføjer en kategori til en valgt film, og lagres i databasen
     * @param movie den valgte film, som sættes til en kategori
     * @param category kategorien, som en film sættes ind i
     * @throws SQLException kaster en SQL Exception.
     */
    public void addMovieToCategory(Movies movie, Category category) throws SQLException 
    {
        try(Connection con = db.getConnection())
        {
            
            String sql = "INSERT INTO CatMovie (MovieID, CategoryId) VALUES (?,?)";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, movie.getMovieId());
            ppst.setInt(2, category.getCategoryId());
            ppst.executeUpdate();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Når der slettes en film, så slettes filmen også filmens valgte kategorier,
     * således, at en fejl ikke opstår. Filmen slettes fra CatMovie, ved at finde filmens  ID.
     * @param id ID bruges til at fjerne den ønskede film.
     * @throws SQLException Kaster en SQL Exception, hvis en fejl opstår.
     */
    public void deleteMovieFromCategoryMovies(int id) throws SQLException  
    {
        try (Connection con = db.getConnection())
        {
            
            String sql = "DELETE FROM CatMovie WHERE MovieID=?";                  
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, id);
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Hvis vi sletter en kategori, så forsvinder alle film også i kategorien, ved at finde kategoriens ID.
     * @param id bruges til at finde kategoriens ID.
     * @throws SQLException kaster en SQL exception, hvis en fejl opstår.
     */
    public void deleteCategoryFromCategoryMovies(int id) throws SQLException 
    {
        try (Connection con = db.getConnection()) 
        {
            
            String sql = "DELETE FROM CatMovie WHERE CategoryId=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, id);
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
} 

