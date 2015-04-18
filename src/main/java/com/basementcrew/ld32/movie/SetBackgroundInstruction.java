/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathon
 */
public class SetBackgroundInstruction extends MovieInstruction {

    private BufferedImage image;

    public SetBackgroundInstruction(BufferedImage image) {
        this.image = image;
    }
    
    
    
    @Override
    public void startInstruction(Movie movie) {
        movie.setBackground(image);
    }

    @Override
    public void endInstruction(Movie movie) {
    }

    @Override
    public void updateInstruction(Movie movie, long dt) {
    }
    
}
