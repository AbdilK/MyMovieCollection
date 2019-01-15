
package MyMovieCollection.DAL;

/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import MyMovieCollection.BE.Movies;
import MyMovieCollection.DAL.exception.DALException;

public class MovieDAO
{
    private final DBConnectionProvider db;

    public MovieDAO() throws IOException
    {
        db = new DBConnectionProvider();
    }
    ////this code makes it, so you can create a Movie in the mainwindow
    public Movies createMovie(int movieId, String title, int ratingImdb, int ratingPersonal, String moviePath) throws SQLException
    {
        {
            try (Connection con = db.getConnection())
            {
                String sql = "INSERT INTO Movie(movieId, title, ratingImdb, ratingPersonal, moviePath) VALUES(?,?,?,?,?)";
                PreparedStatement ppst = con.prepareStatement(sql);
                ppst.setInt(1, movieId);
                ppst.setString(2, title);
                ppst.setInt(3, ratingImdb);
                ppst.setInt(4, ratingPersonal);
                ppst.setString(5, moviePath);
                ppst.execute();
                Movies movie = new Movies(movieId, title, ratingImdb, ratingPersonal, moviePath);
                return movie;
            }
        }
    }
    //this code makes, do you can delete a movie from your mp3 player.
    public void deleteMovie(Movies movie) throws SQLException
    {
        try
        {
            Connection con = db.getConnection();
            //String sql = "DELETE Movies , PlaylistSongs FROM Movies INNER JOIN PlaylistSongs WHERE Movies.movieId= PlaylistSongs.SongID AND Movies.movieId = ?";
            String sql = "DELETE FROM Movie WHERE movieId = ?";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setInt(1, movie.getMovieId());
            ppst.execute();
            //ppst.setInt(2, song.getsongId());
            String sqll = "DELETE FROM CatMovie WHERE CatMovie.MovieID = ?"; 
            PreparedStatement ppstt = con.prepareStatement(sqll);
            ppstt.setInt(1, movie.getMovieId());
            //ppstt.setInt(2, movie.getMovieId());
           
            ppstt.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //this code makes, you will get all movies in one list 
    public List<Movies> getAllMovies() throws SQLException
    {
        List<Movies> movies = new ArrayList<>();
        try (Connection con = db.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Movie;");
            while (rs.next())
            {
                int movieId = rs.getInt("movieId");
                String title = rs.getString("title");
                int ratingImdb = rs.getInt("ratingImdb");
                int ratingPersonal = rs.getInt("ratingPersonal");
                String moviePath = rs.getString("moviePath");
                
                
                Movies movie = new Movies(movieId, title, ratingImdb, ratingPersonal, moviePath);
                movies.add(movie);
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return movies;
    }
    //this code makes changes to the mp3 files title, artist EtX.
    public void updateMovie(Movies movie) throws SQLException
    {
        
        try
        {
            Connection con = db.getConnection();
            String sql = "UPDATE Movie SET title=?, ratingImdb=?, ratingPersonal=?, moviePath=? WHERE movieId=?";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setString(1, movie.getTitle());
            ppst.setInt(2, movie.getRatingImdb());
            ppst.setInt(3, movie.getRatingPersonal());
            ppst.setString(4, movie.getMoviePath());
            ppst.setInt(5, movie.getMovieId());
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //this code allows, so you can search for your songs in the filter 
    public List<Movies> searchMovie(String query) throws SQLException
    {
        List<Movies> movies = new ArrayList<>();
        try
        {
           
            Connection con = db.getConnection();
            String sql = "SELECT * FROM Movie WHERE title like?";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setString(1, "%" + query + "%");
            ppst.setString(2, "%" + query + "%");
            ResultSet rs = ppst.executeQuery();
            while (rs.next())
            {
                int movieId = rs.getInt("movieId");
                String title = rs.getString("title");
                int ratingImdb = rs.getInt("ratingImdb");
                int ratingPersonal = rs.getInt("ratingPersonal");
                String moviePath = rs.getString("moviePath");
                Movies movie = new Movies(movieId, title, ratingImdb, ratingPersonal, moviePath);
                movies.add(movie);
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return movies;
    }
    //this code allows you to switch to the next movie, who is available. 
    public Integer nextAvailableMovieID() throws SQLException 
    {
        try
        {
            Connection con = db.getConnection();
            String sql = "SELECT MAX(movieId) FROM Movie";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int id = 0;
            if (rs.next())
            {
                id = rs.getInt(1);
            }
            return id + 1;
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setRatingPersonal(int id, int ratingPersonal) throws DALException
    {
        try (Connection con = db.getConnection())
        {
            String sqlCmd = "UPDATE Movie SET Movie.ratingPersonal = ? WHERE id = ?";
            PreparedStatement ppst = con.prepareStatement(sqlCmd);
            ppst.setInt(1, ratingPersonal);
            ppst.setInt(2, id);
            ppst.executeUpdate();
        } catch (SQLException ex)
        {
            throw new DALException();
        }
    }
    
}