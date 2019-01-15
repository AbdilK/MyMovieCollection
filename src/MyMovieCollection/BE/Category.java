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

    public Category(int catId, String catName) {
        
        this.CategoryId = catId;
        this.CategoryName = catName;
        Categorymovies = new ArrayList<>();
    }

      public int getCategoryId() 
    {
        return CategoryId;
    }

    public void setCategoryId(int catId)
    {
        this.CategoryId = catId;
    }
    
    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String catName) {
        this.CategoryName = catName;
    }
  
   
    public int getMovieAmountPL()
    {
        return movieAmountPL;
    }

    public void setMovieAmountPL(int movieAmountPL)
    {
        this.movieAmountPL = movieAmountPL;
    }


    public ArrayList<Movies> getCategory() {
        return Categorymovies;
    }

    public void addMovieToCategory(Movies movies) {
        Categorymovies.add(movies);
    }

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
