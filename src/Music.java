import java.util.*;
import java.io.*;
import javax.sound.midi.*;
public class Music{
	public static void main(String[] args) {
		try {
			// From file
			Sequence sequence = MidiSystem.getSequence(new File("korobeiniki.mid"));
				
			// Create a sequencer for the sequence
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);

			sequencer.setTempoFactor(2);	//default is 1, fastest should be something like 5
											//good increments would be 0.5 or so

			sequencer.start();			
		} catch (IOException e) {
		} catch (MidiUnavailableException e) {
		} catch (InvalidMidiDataException e) {
		}
	}
}
