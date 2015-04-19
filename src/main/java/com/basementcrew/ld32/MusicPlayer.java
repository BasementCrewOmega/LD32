package com.basementcrew.ld32;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import bropals.lib.simplegame.logger.ErrorLogger;

public class MusicPlayer {
	private static MusicPlayer singleton;
	private Sequencer sequencer = null;
	
	private MusicPlayer() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace(ErrorLogger.getErr());
		}
	}
	
	public void play(Sequence seq, boolean loop) {
		play(seq, loop, 0, 0, -1);
	}
	
	public void play(Sequence seq, boolean loop, long beginOffset, long repeatStart, long repeatEnd) {
		try {
			sequencer.setSequence(seq);

			// Fails unless after setSequence (duh)
			sequencer.setTickPosition(beginOffset);
			sequencer.setLoopCount(loop ? Sequencer.LOOP_CONTINUOUSLY : 0);
			sequencer.setLoopEndPoint(-1); // Reset end point to solve start end potential problem
			sequencer.setLoopStartPoint(repeatStart);
			sequencer.setLoopEndPoint(repeatEnd);
			
			sequencer.start();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace(ErrorLogger.getErr());
		}
	}
	
	public void stop() {
		sequencer.stop();
	}

	public void shutdown() {
		sequencer.close();
		singleton = null;
	}
	
	// Get a new copy every time you use this class, or be careful since cached entries will become invalid after shutdown
	public static MusicPlayer getInstance() {
		if (singleton == null) {
			singleton = new MusicPlayer();
		}
		
		return singleton;
	}
}
