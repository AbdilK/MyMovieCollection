/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyMovieCollection.BLL;

/**
 *
  * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
import java.util.List;
import MyMovieCollection.BE.Category;
import MyMovieCollection.BE.Movies;

public interface BLLLogicFacade
{
    public void createCategory(Category p);

    public void deleteCategory(Category CategoryToDelete);

    public List<Category> getAllCategorys();

    public void updateCategory(Category p);

    public void createMovie(int movieId, String title, double ratingImdb, double ratingPersonal, String moviepath, String lastViewDate);

    public void deleteMovie(Movies movie);

    
    
    public List<Movies> getAllMovies();

    public void updateMovie(Movies movie);

    public List<Movies> searchMovie(String query);

    public Integer nextAvailableMovieID();

    public Integer nextAvailableCategoryID();

    public List<Movies> getCategoryMovies(Category c);

    public void addMovieToCategory(Movies s, Category c);

    public void deleteMovieFromCategoryMovies(int id);

    public void deleteCategoryFromCategoryMovies(int id);

    public void reCreateCategoryMovies(Movies chosen, Movies toSwapWith);
    
    
}
