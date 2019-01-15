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
    private String genre;
    private String artist;
    private String duration;
    private int locationID;
    private String moviePath;
    private String readDuration;
    private int CategoryUniqueID;
    private int ratingImdb;
    private int imdbR;
    private int ratingPersonal;
    private int imdbP;

    public Movies(int movieId, String title, int ratingImdb, int ratingPersonal, String moviePath)
    {
        this.movieId = movieId;
        this.title = title;
        this.ratingImdb = ratingImdb;
        this.ratingPersonal = ratingPersonal;
        this.moviePath = moviePath;
     
    }

    public Movies()
    {

    }

    public String getReadDuration()
    {
        return readDuration;
    }

    public int getCategoryUniqueID()
    {
        return CategoryUniqueID;
    }

    public void setCategoryUniqueID(int CategoryUniqueID)
    {
        this.CategoryUniqueID = CategoryUniqueID;
    }

    public int getMovieId()
    {
        return movieId;
    }

    public void setMovieId(int movieId)
    {
        this.movieId = movieId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public int getRatingImdb()
    {
        return ratingImdb;
    }
    
    public void setRatingImdb(int ratingImdb)
    {
        this.imdbR = ratingImdb;
    }

     public int getRatingPersonal()
    {
        return ratingImdb;
    }
    
    public void setRatingPersonal(int ratingPersonal)
    {
        this.imdbP = ratingPersonal;
    }

    
    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public int getLocationID()
    {
        return locationID;
    }

    public void setLocation(int locationID)
    {
        this.locationID = locationID;
    }

    public String getMoviePath()
    {
        return moviePath;
    }

    public void setMoviePath(String moviePath)
    {
        this.moviePath = moviePath;
    }

    @Override
    public String toString()
    {
        String movies = this.title
                + " | " + this.artist;

        return movies;
    }
}
