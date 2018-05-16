package karaoke;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.HttpServer;

import karaoke.server.WebServer;
import karaoke.sound.SequencePlayer;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Immutable data type representing a karaoke music body
 *
 */
public class Body {
    private Map<String,Music> voiceToMusic = new HashMap<String,Music>();
    
    // Abstraction Function:
    //   AF(voiceToMusic, voiceToLyrics) = one or more musics played at the same time by different voices
    //                                     with zero or more lyrics where the music for voice <voice> is
    //                                     at voiceToMusic.get(<voice>) and the lyrics are at voiceToLyrics.get(<voice>)
    //      
    //
    // Rep Invariant:
    //      voiceToMusic.size() > 0
    //      voiceToMusic.size() > 0
    //   
    //
    // Rep Safety Argument:
    //   all fields are private 
    //   all getter methods return Strings which are immutable 
    //
    // Thread Safety Argument:
    //   no rep fields are mutated outside of the constructor, which is a threadsafe method by default
    
    /**
     * Creates an isntance of a body object
     * @param musicMap map from voices Music
     */
    public Body(Map<String,Music> musicMap) {
        this.voiceToMusic = musicMap;
    }
    
    /**
     * gets the voices and their corresponding musics
     * @return the map mapping voices to their musics
     */
    public Map<String, Music> getVoicesToMusics() {
        return new HashMap<String, Music>(this.voiceToMusic);
    }
    
    /**
     * gets the voices that are present in the body
     * @return the set of all voices 
     */
    public Set<String> getVoices() {
        return this.voiceToMusic.keySet();
    }

    /**
     * plays the body's music
     * @param player the player to play the music
     * @param server the server on which the body's
     *        music is played
     */
    public void play(SequencePlayer player, WebServer server) {
        for (String voice : this.voiceToMusic.keySet()) {
            final Music voiceMusic = this.voiceToMusic.get(voice);
            voiceMusic.play(player, 0, server);
        }
    }

}
