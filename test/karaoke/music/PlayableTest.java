package karaoke.music;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * test suite for the playable interface
 * @author mattj
 */
public class PlayableTest {
    // testing strategy
    //
    // testing: createChord(List<Note> notes, List<Lyric> lyrics)
    // partition the inputs as follows:
    //   notes: size 1, size >1
    //   lyrics: size 1, size >1
    // partition the outputs as follows:
    //   number of notes in chord: 1, >1
    //   number of lyrics for chord: 1, >1
    //
    // testing: createRest(double duration)
    // partition the inputs as follows:
    //   duration = 0;
    //   0 < duration < number of beats per measure
    //   duration = number of beats per measure
    // partition the outputs as follows:
    //   rest of 0 beats
    //   rest of 0 < beats < number of beats per measure
    //   rest of number of beats per measure beats
    //
    // testing: getDuration()
    // partition the outputs as follows:
    //   0 seconds 
    //   max seconds allowed by time signature and tempo
    //   some amount of seconds in between the above 2 partitions
    //
    // testing play(SequencePlayer player, double startBeat)
    // partition the inputs as follows:
    //   TODO
    // partition the outputs as follows:
    //   TODO
    
    
    
}
