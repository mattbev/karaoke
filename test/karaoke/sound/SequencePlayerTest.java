package karaoke.sound;

import java.util.function.Consumer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

public class SequencePlayerTest {
    public static final int OCTAVE = 12;


    /**
     * Tetsing Strategy:
     * 
     * @category no_didit
     * Listening test on
     *  -piece1.abc
     *  -piece2.abc
     *  -piece3.abc
     * 
     */
    
  
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //Covers:
    //      piece1.abc
    @Test
    public void testPiece1() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;
        // create a new player
        final int beatsPerMinute = 140; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 64; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        double startBeat = 0;
        int numBeats = 1;  //default is quarter note
        player.addNote(piano, new Pitch('C'), startBeat++, numBeats);
        System.out.println("start beat: "+startBeat);
        player.addNote(piano, new Pitch('C'), startBeat++, numBeats);
        System.out.println("start beat: "+startBeat);
        player.addNote(piano, new Pitch('C'), startBeat, .75);      //     3/4 beats
        System.out.println("start beat: "+startBeat);
        startBeat+=.75;
        player.addNote(piano, new Pitch('D'), startBeat, .25);      //     1/4 beats
        System.out.println("start beat: "+startBeat);
        startBeat+=.25;
        player.addNote(piano, new Pitch('E'), startBeat++, numBeats);
        System.out.println("start beat: "+startBeat);
        
        //measure
        
        player.addNote(piano, new Pitch('E'), startBeat, .75);      //     3/4 beats
        startBeat += .75;
        player.addNote(piano, new Pitch('D'), startBeat, .25);      //     1/4 beats
        startBeat+=.25;
        player.addNote(piano, new Pitch('E'), startBeat, .75);      //     3/4 beats
        startBeat += .75;
        player.addNote(piano, new Pitch('F'), startBeat, .25);      //     1/4 beats
        startBeat += .25;
        player.addNote(piano, new Pitch('G'), startBeat, 2);        //       2 beats
        startBeat += 2;
        
        //measure
        
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, 1.0/3);      //     1/2 beats  
        startBeat += 1.0/3;
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, 1.0/3);        //     1/2 beats
        startBeat += 1.0/3;
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, 1.0/3);        //     1/2 beats
        startBeat += 1.0/3;
        
        player.addNote(piano, new Pitch('G'), startBeat, 1.0/3);      //     1/2 beats  startBeat++?
        startBeat += 1.0/3;
        player.addNote(piano, new Pitch('G'), startBeat, 1.0/3);        //     1/2 beats
        startBeat += 1.0/3;
        player.addNote(piano, new Pitch('G'), startBeat, 1.0/3);        //     1/2 beats
        startBeat += 1.0/3;
        
        player.addNote(piano, new Pitch('E'), startBeat, 1.0/3);      //     1/2 beats  startBeat++?
        startBeat += 1.0/3;
        player.addNote(piano, new Pitch('E'), startBeat, 1.0/3);        //     1/2 beats
        startBeat += 1.0/3;
        player.addNote(piano, new Pitch('E'), startBeat, 1.0/3);        //     1/2 beats
        startBeat += 1.0/3;
        
        player.addNote(piano, new Pitch('C'), startBeat, 1.0/3);      //     1/2 beats  startBeat++?
        startBeat += 1.0/3;
        player.addNote(piano, new Pitch('C'), startBeat, 1.0/3);        //     1/2 beats
        startBeat += 1.0/3;
        player.addNote(piano, new Pitch('C'), startBeat, 1.0/3);        //     1/2 beats
        startBeat += 1.0/3;
       
        //measure
        
        player.addNote(piano, new Pitch('G'), startBeat, .75);      //     3/4 beats
        startBeat += .75;
        player.addNote(piano, new Pitch('F'), startBeat, .25);      //     1/4 beats
        startBeat += .25;
        player.addNote(piano, new Pitch('E'), startBeat, .75);      //     3/4 beats
        startBeat += .75;
        player.addNote(piano, new Pitch('D'), startBeat, .25);      //     1/4 beats
        startBeat += .25;
        player.addNote(piano, new Pitch('C'), startBeat, 2);        //       2 beats
        startBeat += 2;
        
        // add a listener at the end of the piece to tell main thread when it's done
        Object lock = new Object();
        player.addEvent(startBeat, (Double beat) -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        
        // print the configured player
        System.out.println(player);

        // play!
        player.play();
        
        // wait until player is done
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("done playing");
       
    }
    
    //Covers:
    //      piece2.abc
    @Test
    
    public void testPiece2() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;

        // create a new player
        final int beatsPerMinute = 200; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 64; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        double startBeat = 0;
        int numBeats = 1;
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, .5);
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, .5);
        startBeat += .5;
        
        
        
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, .5);
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, .5);
        
        startBeat += .5;
        
        startBeat += .5;   // .5 beat rest
        
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, .5);
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, .5);
        startBeat += .5;
        
        startBeat += .5;   // .5 beat rest
        
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, .5);
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, .5);
        startBeat += .5;
        
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, 1);
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, 1);
        startBeat += 1;
        
        //end measure
        
        player.addNote(piano, new Pitch('G').transpose(Pitch.OCTAVE), startBeat, 1);
        player.addNote(piano, new Pitch('B'), startBeat, 1);
        player.addNote(piano, new Pitch('G'), startBeat, 1);
        startBeat += 1;
        
        startBeat += 1;   // 1 beat rest
        
        player.addNote(piano, new Pitch('G'), startBeat, 1);
        startBeat += 1;
        
        startBeat += 1;   // 1 beat rest
        
        //end measure
        
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, 3.0/2);
        startBeat += 3.0/2;
        player.addNote(piano, new Pitch('G'), startBeat, .5);
        startBeat += .5;
        
        startBeat += 1;   // 1 beat rest
        
        player.addNote(piano, new Pitch('E'), startBeat, numBeats);
        startBeat += 1;
        
        //end measure
        
        player.addNote(piano, new Pitch('E'), startBeat, .5);
        startBeat += .5;
        player.addNote(piano, new Pitch('A'), startBeat, numBeats);
        startBeat += 1;
        player.addNote(piano, new Pitch('B'), startBeat, numBeats);
        startBeat += 1;
        player.addNote(piano, new Pitch('B').transpose(-1), startBeat, .5);
        startBeat += .5;
        player.addNote(piano, new Pitch('A'), startBeat, numBeats);
        startBeat += 1;
        
        //end measure
        
        player.addNote(piano, new Pitch('G'), startBeat, 2.0/3);       
        startBeat += 2.0/3;
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, 2.0/3);        
        startBeat += 2.0/3;
        player.addNote(piano, new Pitch('G').transpose(Pitch.OCTAVE), startBeat, 2.0/3);        
        startBeat += 2.0/3;
        //end triple
        
        player.addNote(piano, new Pitch('A').transpose(Pitch.OCTAVE), startBeat, numBeats);
        startBeat += 1;
        player.addNote(piano, new Pitch('F').transpose(Pitch.OCTAVE), startBeat, .5);
        startBeat += .5;
        player.addNote(piano, new Pitch('G').transpose(Pitch.OCTAVE), startBeat, .5);
        startBeat += .5;
        
        //end measure
        
        startBeat += .5;   // .5 beat rest
        
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, numBeats);
        startBeat += 1;
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, .5);
        startBeat += .5;
        player.addNote(piano, new Pitch('D').transpose(Pitch.OCTAVE), startBeat, .5);
        startBeat += .5;
        player.addNote(piano, new Pitch('B'), startBeat, 3.0/4);
        startBeat += 3.0/4;
        
        startBeat += 3.0/4;   // 3/4 beat rest
        
        
        
        // add a listener at the end of the piece to tell main thread when it's done
        Object lock = new Object();
        player.addEvent(startBeat, (Double beat) -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        
        // print the configured player
        System.out.println(player);

        // play!
        player.play();
        
        // wait until player is done
        // (not strictly needed here, but useful for JUnit tests)
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("done playing");
        
    }
    public Consumer<Double> consumerMaker(Double startBeat){
        Consumer<Double> c1 = i -> System.out.println("not initialized");
        
        if(startBeat == 2.0) {
            c1= i-> System.out.print("A-");

        }
        if(startBeat == 3.0) {
            c1= i-> System.out.print("ma-");
        }
        if(startBeat == 5.0) {
            c1= i-> System.out.print("zing");
            
        }
        if(startBeat == 6.5) {
            c1= i-> System.out.print(" grace!");
        }
        if(startBeat == 8.5) {
            c1= i-> System.out.print(" How");
        }
        if(startBeat == 9.5) {
            c1= i-> System.out.print(" sweet");
        }
        if(startBeat == 11.5) {
            c1= i-> System.out.print(" the");
        }
        if(startBeat == 12.5) {
            c1= i-> System.out.print(" sound");
        }
        if(startBeat == 14.5) {
            c1= i-> System.out.print(" That");
        }
        if(startBeat == 15.5) {
            c1= i-> System.out.print(" saved");
        }
        if(startBeat == 17.5) {
            c1= i-> System.out.print(" a");
        }
        if(startBeat == 19.0) {
            c1= i-> System.out.print(" wretch");
        }
        if(startBeat == 21.0) {
            c1= i-> System.out.print(" like");
        }
        if(startBeat == 22.0) {
            c1= i-> System.out.print(" me.");
        }
        return c1;
            
        }
    //Covers:
    //      piece3.abc
    @Test
    public void testPiece3() throws MidiUnavailableException, InvalidMidiDataException{
        Instrument piano = Instrument.PIANO;
        
//        }
//        Consumer<Double> c1 = x -> System.out.println(x);
        // create a new player
        final int beatsPerMinute = 100; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 64; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        double startBeat = 0;
        double numBeats = 1.0/2;
        
        startBeat += 4 * numBeats;   // 2 beat rest
        
        player.addNote(piano, new Pitch('D'), startBeat, 1);
        player.addEvent(startBeat, consumerMaker(startBeat));
        startBeat += 1;

        // end measure
        
        player.addNote(piano, new Pitch('G'), startBeat, 2);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 2;
        
        player.addNote(piano, new Pitch('B'), startBeat, 3.0/4);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 3.0/4;
        player.addNote(piano, new Pitch('G'), startBeat, 3.0/4);
        startBeat += 3.0/4;
        //end measure and tuplet
        
        player.addNote(piano, new Pitch('B'), startBeat, 2);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 2;
        player.addNote(piano, new Pitch('A'), startBeat, 1);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 1;
        //end measure
        
        player.addNote(piano, new Pitch('G'), startBeat, 2);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 2;
        player.addNote(piano, new Pitch('E'), startBeat, 1);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 1;
        //end measure
        
        player.addNote(piano, new Pitch('D'), startBeat, 2);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 2;
        player.addNote(piano, new Pitch('D'), startBeat, 1);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 1;
        //end measure
        
        player.addNote(piano, new Pitch('G'), startBeat, 2);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 2;
        
        player.addNote(piano, new Pitch('B'), startBeat, 3.0/4);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 3.0/4;
        player.addNote(piano, new Pitch('G'), startBeat, 3.0/4);

        startBeat += 3.0/4;
        //end measure and tuplet
        
        player.addNote(piano, new Pitch('B'), startBeat, 2);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 2;
        player.addNote(piano, new Pitch('A'), startBeat, 1);
        player.addEvent(startBeat, consumerMaker(startBeat));

   
        startBeat += 1;
        //end measure
        
        player.addNote(piano, new Pitch('D').transpose(Pitch.OCTAVE), startBeat, 3);
        player.addEvent(startBeat, consumerMaker(startBeat));

        startBeat += 3;
        //end measure
        
        
        
        
        
        
        // add a listener at the end of the piece to tell main thread when it's done
        Object lock = new Object();
        player.addEvent(startBeat, (Double beat) -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        
        // print the configured player
        System.out.println(player);

        // play!
        player.play();
        
        // wait until player is done
        // (not strictly needed here, but useful for JUnit tests)
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("done playing");
    }
    
    
}
