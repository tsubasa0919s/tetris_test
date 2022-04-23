package gametetris;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class GameEffect {
	Synthesizer synth;
	MidiChannel channel;
	private MidiDevice synthesizer;
	public GameEffect() {
		try {
			synth= MidiSystem.getSynthesizer();

			synth.open();
			Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
			synth.loadInstrument(instruments[0]);

			channel = synth.getChannels()[10];
		} catch(Exception e){
			e.printStackTrace();
			if(channel != null)  channel.allNotesOff();
		}
	}
	
	public void close(){
		synth.close();
	}
	
	public void stop(){
		channel.allNotesOff();
	}
	
	public void hardDrop(){
		channel.noteOn(100 , 100);
	}
	
	public void moveDown(){
		channel.noteOn(60 , 100);
	}
	
	public void moveRight(){
		channel.noteOn(60 , 100);
	}
	
	public void moveLeft(){
		channel.noteOn(60 , 100);
	}
	
	public void turnRight(){
		channel.noteOn(60 , 100);
	}
	
	public void turnLeft(){
		channel.noteOn(60 , 100);
	}
	
	public void delete(int deleteNum, int ren){
		channel.noteOn(127 , 100);
	}
	
	public void hind(int num){
		if(num == 0){
			channel.noteOn(30 , 0);
		}else if(num < 3){
			channel.noteOn(30 , 100);
		}else if(num < 10){
			channel.noteOn(25 , 100);
		}else{
			channel.noteOn(20 , 100);
		}
	}
	
	public void win(){
		channel.noteOn(30 , 100);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stop();
		channel.noteOn(60 , 100);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stop();
		
	}
	
	public void lose(){
		channel.noteOn(60 , 100);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stop();
		channel.noteOn(30 , 100);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stop();
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameEffect effect = new GameEffect();
	}

}
