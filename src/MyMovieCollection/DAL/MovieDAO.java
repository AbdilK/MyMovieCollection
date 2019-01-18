
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

    /**
     * Forbinder til vores database
     * @throws IOException
     */
    public MovieDAO() throws IOException
    {
        db = new DBConnectionProvider();
    }
   

    /**
     * Tilføjer en ny film til databasen med forskellige informationer i nedenstående parametre.
     * Under try getConnection splitter vi titlen således, at vi ikke får .mp4 med i filmens titel.
     * @param movieId ID til filmene
     * @param title filmens titel
     * @param ratingImdb Rating på filmene, hentet fra imdb
     * @param ratingPersonal egen personlige rating på filmen
     * @param moviePath stien til valgte filmen
     * @return returnere filmens informationer
     * @throws SQLException kaster en sql exception, hvis en fejl opstår
     */
    public Movies createMovie(int movieId, String title, double ratingImdb, double ratingPersonal, String moviePath) throws SQLException
    {
        {
            try (Connection con = db.getConnection())
            {
                if(title.contains("."))
                {
                    title = title.split("\\.")[0];

                }
                           
                String sql = "INSERT INTO Movie(movieId, title, ratingImdb, ratingPersonal, moviePath) VALUES(?,?,?,?,?)";
                PreparedStatement ppst = con.prepareStatement(sql);
                ppst.setInt(1, movieId);
                ppst.setString(2, title);
                ppst.setDouble(3, ratingImdb);
                ppst.setDouble(4, ratingPersonal);
                ppst.setString(5, moviePath);
                ppst.execute();
                Movies movie = new Movies(movieId, title, ratingImdb, ratingPersonal, moviePath);
                return movie;
            }
        }
    }
    
    /**
     * Sletter en valgt film
     * @param movie henter filmen, som skal slettes
     * @throws SQLException kaster en sql exception, hvis en fejl opstår
     */
    public void deleteMovie(Movies movie) throws SQLException
    {
        try (Connection con = db.getConnection())
        {
            
           
            String sqll = "DELETE FROM CatMovie WHERE CatMovie.MovieID = ?";
            PreparedStatement ppst = con.prepareStatement(sqll);
            ppst.setInt(1, movie.getMovieId());
            ppst.execute();
            String sql = "DELETE FROM Movie WHERE movieId = ?";
            PreparedStatement ppstt = con.prepareStatement(sql);
            ppstt.setInt(1, movie.getMovieId());
            
           
            ppstt.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * Listen returnere alle film fra databasen ind i vores main vindue
     * @return returnere filmene
     * @throws SQLException kaster en SQL exception hvis en fejl opstår
     */
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
                double ratingImdb = rs.getDouble("ratingImdb");
                double ratingPersonal = rs.getDouble("ratingPersonal");
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
   

    /**
     * Opdaterer filmens information, såsom titel, imdb rating, personal rating og moviepath,
     * hvis vi har ændret noget inde fra main vinduet
     * @param movie henter filmen som skal opdateres med ny information
     * @throws SQLException
     */
    public void updateMovie(Movies movie) throws SQLException
    {
        
        try (Connection con = db.getConnection())
        {
            
            String sql = "UPDATE Movie SET title=?, ratingImdb=?, ratingPersonal=?, moviePath=? WHERE movieId=?";
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setString(1, movie.getTitle());
            ppst.setDouble(2, movie.getRatingImdb());
            ppst.setDouble(3, movie.getRatingPersonal());
            ppst.setString(4, movie.getMoviePath());
            ppst.setInt(5, movie.getMovieId());
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * Tilføjer nedenstående SQL forespørgsel, og henter data fra databasen,
     * når man bruger søgefeltet i vores main fxml.
     * @param query Sender en forespørgsel ind til databasen med et indtastet keyword,
     * som returnere en specific film. 
     * 
     * @return returnerer den film som passer til forespørgslen.
     * @throws SQLException kaster en SQLException hvis en fejl opstår
     */
    public List<Movies> searchMovie(String query) throws SQLException
    {
        List<Movies> movies = new ArrayList<>();
        try (Connection con = db.getConnection())
        {
           
            
            String sql = "SELECT * FROM Movie WHERE title LIKE ?";
            PreparedStatement ppst = con.prepareStatement(sql);
          
            ppst.setString(1, "%" + query + "%");
            ResultSet rs = ppst.executeQuery();
            while (rs.next())
            {
                int movieId = rs.getInt("movieId");
                String title = rs.getString("title");
                double ratingImdb = rs.getDouble("ratingImdb");
                double ratingPersonal = rs.getDouble("ratingPersonal");
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
     

    /**Hvis en film er slettet, og en ny er tilføjet, så får den nye film samme ID,
     * som den gamle der lige er blevet slettet.
     *
     * @return returnerer ID til den nye film.
     * @throws SQLException kaster en SQL exception hvis en fejl opstår
     */
    public Integer nextAvailableMovieID() throws SQLException 
    {
        try (Connection con = db.getConnection())
        {
            
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
    
    /** Opdaterer filmens personal rating inde i databasen, når man enten opretter,
     * eller redigerer filmens rating.
     *
     * @param id filmens id
     * @param ratingPersonal personal rating til den valgte film, som er en double,
     * for at få decimal tal med.
     * @throws DALException kaster en DALException, hvis en fejl opstår.
     */
    public void setRatingPersonal(double id, double ratingPersonal) throws DALException
    {
        try (Connection con = db.getConnection())
        {
            String sqlCmd = "UPDATE Movie SET Movie.ratingPersonal = ? WHERE id = ?";
            PreparedStatement ppst = con.prepareStatement(sqlCmd);
            ppst.setDouble(1, ratingPersonal);
            ppst.setDouble(2, id);
            ppst.executeUpdate();
        } catch (SQLException ex)
        {
            throw new DALException();
        }
    }
    
    /** Opdaterer filmens imdb rating inde i databasen, når men enten opretter,
     * eller redigerer filmens rating
     *
     * @param id filmens id
     * @param ratingImdb imdb rating til den valgte film, som er en double
     * for at få decimal tal med.
     * @throws DALException kaster en DALException, hvis der opstår en fejl.
     */
    public void setRatingImdb(double id, double ratingImdb) throws DALException
    {
        try (Connection con = db.getConnection())
        {
            String sqlCmd = "UPDATE Movie SET Movie.ratingImdb = ? WHERE id = ?";
            PreparedStatement ppst = con.prepareStatement(sqlCmd);
            ppst.setDouble(1, ratingImdb);
            ppst.setDouble(2, id);
            ppst.executeUpdate();
        } catch (SQLException ex)
        {
            throw new DALException();
        }
    }
}