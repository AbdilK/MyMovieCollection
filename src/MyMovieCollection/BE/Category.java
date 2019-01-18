/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyMovieCollection.BE;

import java.util.ArrayList;

/**
 *
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
public class Category {
    
    private int CategoryId;
    private String CategoryName;
    private int movieAmountPL;
    
    private ArrayList<Movies> Categorymovies;

    /**
     *
     * @param catId
     * @param catName
     */
    public Category(int catId, String catName) {
        
        this.CategoryId = catId;
        this.CategoryName = catName;
        Categorymovies = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public int getCategoryId() 
    {
        return CategoryId;
    }

    /**
     *
     * @param catId
     */
    public void setCategoryId(int catId)
    {
        this.CategoryId = catId;
    }
    
    /**
     *
     * @return
     */
    public String getCategoryName() {
        return CategoryName;
    }

    /**
     *
     * @param catName
     */
    public void setCategoryName(String catName) {
        this.CategoryName = catName;
    }
  
    /**
     *
     * @return
     */
    public int getMovieAmountPL()
    {
        return movieAmountPL;
    }

    /**
     *
     * @param movieAmountPL
     */
    public void setMovieAmountPL(int movieAmountPL)
    {
        this.movieAmountPL = movieAmountPL;
    }

    /**
     *
     * @return
     */
    public ArrayList<Movies> getCategory() {
        return Categorymovies;
    }

    /**
     *
     * @param movies
     */
    public void addMovieToCategory(Movies movies) {
        Categorymovies.add(movies);
    }

    /**
     *
     * @param movies
     */
    public void removeMovieFromCategory(Movies movies) {
        for (Movies movie1 : Categorymovies) {
            if (movie1.equals(movies)) {
                Categorymovies.remove(movies);
            }
        }
    }

    @Override
    public String toString(){
        return CategoryName;
    }

  
}
