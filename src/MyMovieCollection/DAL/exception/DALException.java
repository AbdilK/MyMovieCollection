/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyMovieCollection.DAL.exception;

/**
 *
 * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
public class DALException extends Exception
{

    /**
     *
     */
    public DALException()
    {
        // Constructor from the class we inherit from. (Exception)
        super();
    }

    /**
     *
     * @param message
     */
    public DALException(String message)
    {
        super(message);
    }

    /**
     *
     * @param message
     * @param ex
     */
    public DALException(String message, Exception ex) {
        super(message, ex);
    }
}
