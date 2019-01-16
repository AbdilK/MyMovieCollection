/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyMovieCollection.BE;

import java.io.IOException;

/**
 *
 * @author Hassuni
 */
public class VLCPlayer
{
    
public void runVLC(String moviePath){
        String qq = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
        String test = "\"" + qq + "\"";
        String l = "\\\\Github\\MyMovieCollection\\src\\MyMovieCollection\\movies\\Halloween.mp4";
               try {
        Process p = Runtime.getRuntime().exec(test + moviePath);
                   System.out.println(moviePath);
    } catch (IOException e){
        e.printStackTrace();
    }
    
}}
