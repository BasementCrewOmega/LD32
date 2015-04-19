/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class ImageMovieObject extends MovieObject {

    private BufferedImage imageMovieObject;

    public ImageMovieObject(BufferedImage imageMovieObject) {
        this.imageMovieObject = imageMovieObject;
    }
    
    
    
    @Override
    public void update(long dt) {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(imageMovieObject, getX(), getY(), null);
    }
    
}
