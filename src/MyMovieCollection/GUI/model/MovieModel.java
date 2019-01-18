/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyMovieCollection.GUI.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import MyMovieCollection.BE.Category;
import MyMovieCollection.BE.Movies;
import MyMovieCollection.BLL.BLLManager;
import MyMovieCollection.BLL.exception.BLLException;
import javafx.scene.image.ImageView;

/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */

public class MovieModel {

    /**
     *
     */
   // public final BLLLogicFacade BLLF;

    /**
     *
     */
    public BLLManager BLM;

    /**
     *
     */
    public ObservableList olMovies;

    /**
     *
     */
    public ObservableList olCategorys;
    private Category category;
    private Movies movie;
    private List<String> AllCategorys;

    /**
     *
     */
    public static MovieModel instance;
    
    /**
     *
     * @throws IOException
     */
    public MovieModel() throws IOException {
        AllCategorys = new ArrayList();
        
        olMovies = FXCollections.observableArrayList();
        olCategorys = FXCollections.observableArrayList();
        BLM = new BLLManager();
    }

    /**
     *
     * @return
     */
    public static MovieModel getInstance() // IF there is an existing Model we are returning instance of it, otherwise we are creating and returning new one.
    {
        if (instance == null) {
            try {
                instance = new MovieModel();
            } catch (IOException ex) {
                Logger.getLogger(MovieModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public ObservableList getMoviesAsObservable() // We are getting all movies and returning it as ObservableList
    {
        olMovies.clear();
        olMovies.addAll(getAllMovies());
        return olMovies;
    }
    
    /**
     *
     * @return
     */
    public List<Movies> findDullMovies()
    {
       return BLM.findDullMovies();
    }

    /**
     *
     * @return
     */
    public ObservableList getCategorysAsObservable() // We are getting all categorys and returning it as ObservableList
    {
        olCategorys.clear();
        olCategorys.addAll(getAllCategorys());
        return olCategorys;
    }
    
    /**
     *
     * @param query
     * @return
     */
    public List<Movies> searchMovie(String query) {
        List<Movies> movie = BLM.searchMovie(query);
        return movie;
    }
    
    /**
     *
     * @param category
     */
    public void createCategory(Category category) {
        BLM.createCategory(category);
    }

    /**
     *
     * @param categoryToDelete
     */
    public void deleteCategory(Category categoryToDelete) {
        BLM.deleteCategory(categoryToDelete);
    }

    /**
     *
     * @return
     */
    public List<Category> getAllCategorys() {
        List<Category> p = BLM.getAllCategorys();
        return p;
    }

    /**
     *
     * @param category
     */
    public void updateCategory(Category category) {
        BLM.updateCategory(category);
    }

    /**
     *
     * @param movieId
     * @param title
     * @param ratingImdb
     * @param ratingPersonal
     * @param moviePath
     */
    public void createMovie(int movieId, String title, double ratingImdb, double ratingPersonal, String moviePath) {
        BLM.createMovie(movieId, title, ratingImdb, ratingPersonal, moviePath);
    }

    /**
     *
     * @param movie
     */
    public void deleteMovie(Movies movie) {
        BLM.deleteMovie(movie);
    }

    /**
     *
     * @param view
     * @return
     */
    public Movies getMovieData(ImageView view)
    {
        Movies data = null;
        try
        {
            data = BLM.getMovieData(view);
        }
        catch (BLLException ex)
        {
            System.out.println(ex);
        }
        return data;
    }

    /**
     *
     * @return
     */
    public List<Movies> getAllMovies() {
        List<Movies> movie = BLM.getAllMovies();
        return movie;
    }

    /**
     *
     * @param movie
     */
    public void updateMovie(Movies movie) {
        BLM.updateMovie(movie);
    }

    /**
     *
     * @return
     */
    public Integer nextAvailableMovieID() {
        return BLM.nextAvailableMovieID();
    }

    /**
     *
     * @return
     */
    public Integer nextAvailableCategoryID() {
        return BLM.nextAvailableCategoryID();
    }

    /**
     *
     * @param category
     * @return
     */
    public List<Movies> getCategoryMovies(Category category) {
        return BLM.getCategoryMovies(category);
    }

    /**
     *
     * @param movie
     * @param category
     */
    public void addMovieToCategory(Movies movie, Category category) {
        BLM.addMovieToCategory(movie, category);
    }

    /**
     *
     * @param id
     */
    public void deleteMovieFromCategoryMovies(int id) {
        BLM.deleteMovieFromCategoryMovies(id);
    }

    /**
     *
     * @param id
     */
    public void deleteCategoryFromCategoryMovies(int id) {
        BLM.deleteCategoryFromCategoryMovies(id);
    }

    /**
     *
     * @param chosen
     * @param toSwapWith
     */
    
    /**
     *
     * @return
     */
    public Category getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     *
     * @return
     */
    public Movies getMovie() {
        return movie;
    }

    /**
     *
     * @param movie
     */
    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    /**
     *
     * @return
     */
    public List<String> getCategorys() {
        return AllCategorys;
    }

    /**
     *
     * @param cat
     */
 

    
    
}

