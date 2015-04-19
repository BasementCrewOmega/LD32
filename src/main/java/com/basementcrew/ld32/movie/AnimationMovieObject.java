/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

import bropals.lib.simplegame.animation.Animation;
import java.awt.Graphics;

/**
 *
 * @author Jonathon
 */
public class AnimationMovieObject extends MovieObject {
    
    private Animation animation;

    public AnimationMovieObject(Animation animation) {
        this.animation = animation;
        this.animation.setTrack(0);
    }
    
    @Override
    public void update(long dt) {
        animation.update((int)dt);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentImage(), getX(), getY(), null);
    }
    
}
