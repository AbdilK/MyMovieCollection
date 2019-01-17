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

/**
 *
 * @author Hassuni
 */
public interface BLLLogicFacade
{

    /**
     *
     * @param p
     */
    public void createCategory(Category p);

    /**
     *
     * @param CategoryToDelete
     */
    public void deleteCategory(Category CategoryToDelete);

    /**
     *
     * @return
     */
    public List<Category> getAllCategorys();

    /**
     *
     * @param p
     */
    public void updateCategory(Category p);

    /**
     *
     * @param movieId
     * @param title
     * @param ratingImdb
     * @param ratingPersonal
     * @param moviepath
     */
    public void createMovie(int movieId, String title, double ratingImdb, double ratingPersonal, String moviepath);

    /**
     *
     * @param movie
     */
    public void deleteMovie(Movies movie);

    /**
     *
     * @return
     */
    public List<Movies> findDullMovies();
    
    /**
     *
     * @return
     */
    public List<Movies> getAllMovies();

    /**
     *
     * @param movie
     */
    public void updateMovie(Movies movie);

    /**
     *
     * @param query
     * @return
     */
    public List<Movies> searchMovie(String query);

    /**
     *
     * @return
     */
    public Integer nextAvailableMovieID();

    /**
     *
     * @return
     */
    public Integer nextAvailableCategoryID();

    /**
     *
     * @param c
     * @return
     */
    public List<Movies> getCategoryMovies(Category c);

    /**
     *
     * @param s
     * @param c
     */
    public void addMovieToCategory(Movies s, Category c);

    /**
     *
     * @param id
     */
    public void deleteMovieFromCategoryMovies(int id);

    /**
     *
     * @param id
     */
    public void deleteCategoryFromCategoryMovies(int id);

}
