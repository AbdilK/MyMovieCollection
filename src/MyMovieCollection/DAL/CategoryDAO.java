
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

public class CategoryDAO
{
    private DBConnectionProvider db;
    private final CatMovieDAO DankCategory;


    public CategoryDAO() throws IOException
    {
        db = new DBConnectionProvider();
        DankCategory = new CatMovieDAO();
    }
// This method creats a playlist in and stores it in our database
    public void createCategory(Category cat) throws SQLException
    {
        try
        {
            Connection con = db.getConnection();
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
// This method deletes the selected playlist from our database
    public void deleteCategory(Category categoryToDelete) throws SQLException
    {
        try
        {
            Connection con = db.getConnection();
            String sql = "DELETE FROM Category WHERE CategoryID=?";
            PreparedStatement ppst = con.prepareCall(sql);
            ppst.setInt(1, categoryToDelete.getCategoryId());
            ppst.executeUpdate();
        } catch (SQLServerException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// This method gets all the playlists that have been stored from our databse, and loads them when you run the program
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
    public void updateCategory(Category cat) throws SQLException
    {
        try
        {
            Connection con = db.getConnection();
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
    public Integer nextAvailableCategoryID() throws SQLException
    {
        try
        {
            Connection con = db.getConnection();
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