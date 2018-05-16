package karaoke.sound;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import karaoke.Karaoke;
import karaoke.server.WebServer;



/**
 * MusicPlayer can play a Karaoke expression on MIDI synthesizer
 * @author chessa, mattbev, sophias 
 *
 */
public class MusicPlayer {
    
    
    
    /**
     * play a Karaoke 
     * @param karaoke Karaoke expression to play
     * @param server server on which the MusicPlayer is played
     * @throws MidiUnavailableException thrown if the Midi synthesizer is unavailable
     * @throws InvalidMidiDataException thrown if Midi cannot play the Karaoke file
     */
    public static void play(Karaoke karaoke, WebServer server) throws MidiUnavailableException, InvalidMidiDataException{
        SequencePlayer player = new MidiSequencePlayer();
        karaoke.play(player, server);
        
        //recurses all the way down to note class which is added to player in addNote(), then play the player
        player.play();
        
    }
    
    
    
    
    
    

}
