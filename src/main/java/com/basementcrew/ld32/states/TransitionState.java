/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.states;

import com.basementcrew.ld32.movie.Movie;
import java.awt.Graphics;

/**
 * This state that will play a video in between menus and battle sequences
 * @author Kevin
 */
public class TransitionState extends TimedGameState {

    private Movie movie;
    
    public TransitionState(Movie play) {
        this.movie = play;
    }
    
    @Override
    public void update(long dt) {
        movie.updateMovie(dt);
    }

    @Override
    public void render(Object o) {
        movie.renderMovie((Graphics)o);
    }

    @Override
    public void onEnter() {
        movie.reset();
    }

    @Override
    public void onExit() {
    }
    
}
