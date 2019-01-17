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
import MyMovieCollection.BLL.BLLLogicFacade;
import MyMovieCollection.BLL.exception.BLLException;
import javafx.scene.image.ImageView;

/*
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */

/**
 *
 * @author Hassuni
 */
public class MovieModel {

    /**
     *
     */
    public final BLLLogicFacade BLLManager;

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
        addNewCategory();
        olMovies = FXCollections.observableArrayList();
        olCategorys = FXCollections.observableArrayList();
        BLLManager = new BLLManager();
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
       return BLLManager.findDullMovies();
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
        List<Movies> movie = BLLManager.searchMovie(query);
        return movie;
    }
    
    /**
     *
     * @param category
     */
    public void createCategory(Category category) {
        BLLManager.createCategory(category);
    }

    /**
     *
     * @param categoryToDelete
     */
    public void deleteCategory(Category categoryToDelete) {
        BLLManager.deleteCategory(categoryToDelete);
    }

    /**
     *
     * @return
     */
    public List<Category> getAllCategorys() {
        List<Category> p = BLLManager.getAllCategorys();
        return p;
    }

    /**
     *
     * @param category
     */
    public void updateCategory(Category category) {
        BLLManager.updateCategory(category);
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
        BLLManager.createMovie(movieId, title, ratingImdb, ratingPersonal, moviePath);
    }

    /**
     *
     * @param movie
     */
    public void deleteMovie(Movies movie) {
        BLLManager.deleteMovie(movie);
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
        List<Movies> movie = BLLManager.getAllMovies();
        return movie;
    }

    /**
     *
     * @param movie
     */
    public void updateMovie(Movies movie) {
        BLLManager.updateMovie(movie);
    }

    /**
     *
     * @return
     */
    public Integer nextAvailableMovieID() {
        return BLLManager.nextAvailableMovieID();
    }

    /**
     *
     * @return
     */
    public Integer nextAvailableCategoryID() {
        return BLLManager.nextAvailableCategoryID();
    }

    /**
     *
     * @param category
     * @return
     */
    public List<Movies> getCategoryMovies(Category category) {
        return BLLManager.getCategoryMovies(category);
    }

    /**
     *
     * @param movie
     * @param category
     */
    public void addMovieToCategory(Movies movie, Category category) {
        BLLManager.addMovieToCategory(movie, category);
    }

    /**
     *
     * @param id
     */
    public void deleteMovieFromCategoryMovies(int id) {
        BLLManager.deleteMovieFromCategoryMovies(id);
    }

    /**
     *
     * @param id
     */
    public void deleteCategoryFromCategoryMovies(int id) {
        BLLManager.deleteCategoryFromCategoryMovies(id);
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
    public void addNewCategory(String cat) {
        AllCategorys.add(cat);
    }

    private void addNewCategory() {
        AllCategorys.add("Action");
        AllCategorys.add("Drama");
        AllCategorys.add("Comedy");
        AllCategorys.add("Romance");
        AllCategorys.add("Crime");
        AllCategorys.add("Horror");
        AllCategorys.add("Thriller");
        AllCategorys.add("Historical");
        AllCategorys.add("Adventure");
        AllCategorys.add("Fantasy");
        AllCategorys.add("Mystery");
        AllCategorys.add("Satire");
        AllCategorys.add("Science Fiction");
        AllCategorys.add("Animation");
        AllCategorys.add("Live Action");
        AllCategorys.add("War");
        AllCategorys.add("Western");

    }

    
    
}

