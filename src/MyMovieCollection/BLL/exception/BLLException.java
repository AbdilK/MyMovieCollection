/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyMovieCollection.BLL.exception;

/**
 *
  * @author Abdil-K, Bjarne666, Hassuni8, KerimTopci
 */
public class BLLException extends Exception
{
    public BLLException(String message)
    {
        super(message);
    }

    public BLLException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
