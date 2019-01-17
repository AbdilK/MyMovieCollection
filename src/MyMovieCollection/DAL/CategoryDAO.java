
package MyMovieCollection.DAL;

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
import MyMovieCollection.BE.Category;

/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */

/**
 *
 * @author Hassuni
 */


public class CategoryDAO
{
    private DBConnectionProvider db;
    private final CatMovieDAO DankCategory;

    /**
     *
     * @throws IOException
     */
    public CategoryDAO() throws IOException
    {
        db = new DBConnectionProvider();
        DankCategory = new CatMovieDAO();
    }


    /**
     * Denne metode gør at vi kan lave en kategori og gemme det i vores database
     * @param cat filmens kategori
     * @throws SQLException
     */
    public void createCategory(Category cat) throws SQLException
    {
        try (Connection con = db.getConnection())
        {
            
            String sql = "INSERT INTO Category VALUES (?,?)";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, cat.getCategoryId());
            ppst.setString(2, cat.getCategoryName());
            ppst.executeUpdate();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Denne metode gør at vi kan slette den valgte kategori fra vores database
     * @param categoryToDelete kategorien der skal slettes
     * @throws SQLException
     */
    public void deleteCategory(Category categoryToDelete) throws SQLException
    {
        try (Connection con = db.getConnection())
        {
            
            String sql = "DELETE FROM Category WHERE CategoryID=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, categoryToDelete.getCategoryId());
            ppst.executeUpdate();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Denne metode får alle kategorierne der er i vores database, og loader dem når du kører programmet
     * @return returnerer category listen
     * @throws SQLException
     */
    public List<Category> getAllCategorys() throws SQLException {
        List<Category> p = new ArrayList<>();
        try (Connection con = db.getConnection()){
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Category");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Category cat = new Category(id, name);
                p.add(cat);
            }

        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
// This method allows us to update our playlist and stores the new and updated information in the database

    /**
     * Denne metode gør at vi kan opdatere vores playlist og gemme de nye informationer i vores database
     * @param cat kategorien der er opdateret
     * @throws SQLException
     */
    public void updateCategory(Category cat) throws SQLException
    {
        try (Connection con = db.getConnection())
        {
            
            String sql = "UPDATE Category SET CategoryName=? WHERE CategoryID=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setString(1, cat.getCategoryName());
            ppst.setInt(2, cat.getCategoryId());
            ppst.execute();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// This method finds the next available ID and gives it to the playlist

    /**
     * Denne metode finder den næste ledige ID og giver den til playlisten
     * @return returnerer id + 1 således, at den får en større værdi end den forrige
     * @throws SQLException
     */
    public Integer nextAvailableCategoryID() throws SQLException
    {
        try (Connection con = db.getConnection())
        {
            
            String sql = "SELECT MAX(CategoryID) FROM Category";
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
    
}