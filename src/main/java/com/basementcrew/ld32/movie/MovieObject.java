/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.basementcrew.ld32.movie;

import java.awt.Graphics;

/**
 *
 * @author Jonathon
 */
public abstract class MovieObject {

    private float x;
    private float y;
    private String name;

    public abstract void update(long dt);

    public abstract void render(Graphics g);

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
