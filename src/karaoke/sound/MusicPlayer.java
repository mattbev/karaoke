package karaoke.sound;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import karaoke.Header;
import karaoke.Karaoke;
import karaoke.Note;
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
        final Header header = karaoke.getHeader();
        final String tempo = header.getTempo();
        final String[] components = tempo.split("=");
        double noteLength = Note.parseLength(components[0]);
        int beatsPerMinute = Integer.parseInt(components[1]);
        final double lowerThreshold = header.getDefaultLengthDouble()-.01;
        final double upperThreshold = header.getDefaultLengthDouble()+.01;
        if (noteLength < lowerThreshold) {
            while (noteLength < lowerThreshold) {
                noteLength *= 2;
                beatsPerMinute *= 2;
            }
        }
        else if (noteLength > upperThreshold) {
            while (noteLength > upperThreshold) {
                noteLength /= 2;
                beatsPerMinute /= 2;
            }
        }
        System.out.println(noteLength);
        System.out.println(beatsPerMinute);
        final int ticksPerBeat = 64;
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        karaoke.play(player, server);
        
        //recurses all the way down to note class which is added to player in addNote(), then play the player
        player.play();
        
    }
    
    
    
    
    
    

}
