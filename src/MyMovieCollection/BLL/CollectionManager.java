/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
package MyMovieCollection.BLL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import MyMovieCollection.BE.Category;
import MyMovieCollection.BE.Movies;
import MyMovieCollection.BLL.exception.BLLException;
import MyMovieCollection.DAL.CategoryDAO;
import MyMovieCollection.DAL.MovieDAO;
import MyMovieCollection.DAL.CatMovieDAO;
import javafx.scene.image.ImageView;

public class CollectionManager implements BLLLogicFacade
{

    private static CollectionManager instance;
    private final MovieDAO MovieDAO;
    private final CategoryDAO CategoryDAO;
    private final CatMovieDAO CategoryMoviesDAO;
    private CollectionManager cm;

    public CollectionManager() throws IOException
    {
        MovieDAO = new MovieDAO();
        CategoryDAO = new CategoryDAO();
        CategoryMoviesDAO = new CatMovieDAO();
    }

    @Override
    public void createCategory(Category category)
    {
        try
        {
            CategoryDAO.createCategory(category);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteCategory(Category categoryToDelete)
    {
        try
        {
            CategoryDAO.deleteCategory(categoryToDelete);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Category> getAllCategorys()
    {
        List<Category> categorys;
        try
        {
            System.out.println("All categorys has been loaded ");
            return categorys = CategoryDAO.getAllCategorys();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateCategory(Category category)
    {
        try
        {
            CategoryDAO.updateCategory(category);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void createMovie(int movieId, String title, int ratingImdb, int ratingPersonal, String moviePath)
    {
        try
        {
            MovieDAO.createMovie(movieId, title, ratingImdb, ratingPersonal, moviePath);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void deleteMovie(Movies movie)
    {
        try
        {
            MovieDAO.deleteMovie(movie);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Movies> getAllMovies()
    {
        List<Movies> allmovies;
        try
        {
            return allmovies = MovieDAO.getAllMovies();
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void updateMovie(Movies movie)
    {
        try
        {
            MovieDAO.updateMovie(movie);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Movies> searchMovie(String query)
    {
        List<Movies> searchResult;
        try
        {
            return searchResult = MovieDAO.searchMovie(query);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public Movies getMovieData(ImageView imageView) throws BLLException
    {
        Movies movieObject = null;

        for (Movies movie : getAllMovies())
        {
            
            if (Integer.parseInt(imageView.getId()) == movie.getMovieId())
            {
                movieObject = movie;
            }
        }

        return movieObject;
    }
    @Override
    public Integer nextAvailableMovieID()
    {
        try
        {
            return MovieDAO.nextAvailableMovieID();
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Integer nextAvailableCategoryID()
    {
        try
        {
            return CategoryDAO.nextAvailableCategoryID();
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Movies> getCategoryMovies(Category category)
    {
        try
        {
            return CategoryMoviesDAO.getCategoryMovies(category);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void addMovieToCategory(Movies movie, Category category)
    {
        try
        {
            CategoryMoviesDAO.addMovieToCategory(movie, category);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteMovieFromCategoryMovies(int id)
    {
        try
        {
            CategoryMoviesDAO.deleteMovieFromCategoryMovies(id);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteCategoryFromCategoryMovies(int id)
    {
        try
        {
            CategoryMoviesDAO.deleteCategoryFromCategoryMovies(id);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reCreateCategoryMovies(Movies selected, Movies replace)
    {
        try
        {
            CategoryMoviesDAO.reCreateCategoryMovies(selected, replace);
        } catch (SQLException ex)
        {
            Logger.getLogger(CollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
