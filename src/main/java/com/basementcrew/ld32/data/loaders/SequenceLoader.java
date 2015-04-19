package com.basementcrew.ld32.data.loaders;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import bropals.lib.simplegame.io.AssetLoader;
import bropals.lib.simplegame.logger.ErrorLogger;

public class SequenceLoader extends AssetLoader<Sequence> {

	@Override
	public void loadAsset(String key, InputStream is) {
		try {
			is = new BufferedInputStream(is);
			Sequence seq = MidiSystem.getSequence(is);
			add(key, seq);
		} catch (InvalidMidiDataException | IOException e) {
			e.printStackTrace(ErrorLogger.getErr());
		}
	}

}
