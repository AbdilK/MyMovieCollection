/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyMovieCollection.BE;



/**
 *
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
public class Movies
{

    private int movieId;
    private String title;
    private int locationID;
    private String moviePath;
    private int CategoryUniqueID;
    private double ratingImdb;
    private double imdbR;
    private double ratingPersonal;
    private double imdbP;

    /**
     *
     * @param movieId
     * @param title
     * @param ratingImdb
     * @param ratingPersonal
     * @param moviePath
     */
    public Movies(int movieId, String title, double ratingImdb, double ratingPersonal, String moviePath)
    {
        this.movieId = movieId;
        this.title = title;
        this.ratingImdb = ratingImdb;
        this.ratingPersonal = ratingPersonal;
        this.moviePath = moviePath;
     
    }

    /**
     *
     */
    public Movies()
    {

    }

 

    /**
     *
     * @return
     */
    public int getCategoryUniqueID()
    {
        return CategoryUniqueID;
    }

    /**
     *
     * @param CategoryUniqueID
     */
    public void setCategoryUniqueID(int CategoryUniqueID)
    {
        this.CategoryUniqueID = CategoryUniqueID;
    }

    /**
     *
     * @return
     */
    public int getMovieId()
    {
        return movieId;
    }

    /**
     *
     * @param movieId
     */
    public void setMovieId(int movieId)
    {
        this.movieId = movieId;
    }

    /**
     *
     * @return
     */
    public String getTitle()
    {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     *
     * @return
     */
    public double getRatingImdb()
    {
        return ratingImdb;
    }
    
    /**
     *
     * @param ratingImdb
     */
    public void setRatingImdb(double ratingImdb)
    {
        this.imdbR = ratingImdb;
    }

    /**
     *
     * @return
     */
    public double getRatingPersonal()
    {
        return ratingPersonal;
    }
    
    /**
     *
     * @param ratingPersonal
     */
    public void setRatingPersonal(double ratingPersonal)
    {
        this.imdbP = ratingPersonal;
    }

    /**
     *
     * @return
     */
    public int getLocationID()
    {
        return locationID;
    }

    /**
     *
     * @param locationID
     */
    public void setLocation(int locationID)
    {
        this.locationID = locationID;
    }

    /**
     *
     * @return
     */
    public String getMoviePath()
    {
        return moviePath;
    }

    /**
     *
     * @param moviePath
     */
    public void setMoviePath(String moviePath)
    {
        this.moviePath = moviePath;
    }

    @Override
    public String toString()
    {
        String movies = this.title;
        return movies;
    }
}
