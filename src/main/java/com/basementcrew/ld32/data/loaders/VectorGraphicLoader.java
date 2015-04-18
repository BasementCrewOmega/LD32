package com.basementcrew.ld32.data.loaders;

import java.io.InputStream;

import com.basementcrew.ld32.data.VectorGraphic;

import bropals.lib.simplegame.io.AssetLoader;

/**
 * <p>
 * Loads a vector graphic like image format
 * saves space and makes cleaner images when possible.
 * </p>
 * 
 * <code>
 * <p>
 * init := width height <statement>* <br>
 * <br>
 * statement := ... <br>
 * fgColor #RRGGBBAA <br>
 * bgColor #RRGGBBAA <br>
 * line x y x2 y2 (stroke=1) <br>
 * rect x y width height (stroke=1) <br>
 * fillRect x y width height (stroke=0) <br>
 * </code>
 * 
 * @author Starbuck
 *
 */
public class VectorGraphicLoader extends AssetLoader<VectorGraphic> {

	@Override
	public void loadAsset(String key, InputStream inputStream) {
		
	}

}
