package karaoke;

import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpServer;

import karaoke.server.WebServer;
import karaoke.sound.SequencePlayer;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Karaoke is an immutable ADT representing a piece of music of a karaoke machine
 *
 */
public class Karaoke {
    private final Body body;
    private final Header header;
    // Abstraction Function:
    //   AF(body, header) = a Karaoke object where header holds the details of the music file and body
    //                      holds the music and lyrics of one or more voices.
    //      
    //
    // Rep Invariant:
    //   true             
    //
    // Rep Safety Argument:
    //   all fields are private final
    //   all getter methods return Strings which are immutable 
    //
    // Thread Safety Argument:
    //   no rep fields are mutated outside of the constructor, which is a threadsafe method by default
    
    /**
     * creates an instance of a Karaoke object
     * @param body the body of the Karaoke
     * @param header the header of the Karaoke
     */
    private Karaoke(Header header, Body body) {
        this.body = body;
        this.header = header;
    }
    
    
    /**
     * creates an new karaoke
     * @param body the body of the Karaoke
     * @param header the header of the Karaoke
     * @return a new Karaoke object
     */
    public static Karaoke createKaraoke(Header header, Body body) {
        return new Karaoke(header,body);
    }
    
    /**
     * Play this karaoke
     * @param player player to play on
     * @param server the server the karaoke is being played on
     */
    public void play(SequencePlayer player, WebServer server) {
        body.play(player, server);
    }
    
    /**
     * Get the title of this karaoke song
     * 
     * @return the title of this piece
     */
    public String getTitle() {
        return this.header.getTitle();
    }

    /**
     * Get the composer of the song being played on karaoke
     * 
     * @return the composer of this song
     */
    public String getComposer() {
        return this.header.getComposer();
    }
    
    
    /**
     * Provides all the voice in the karaoke's body
     * @return list of voices in the body of the karaoke
     */
    public List<String> getVoices() {
        List<String> voices = new ArrayList<>();
        for (String v : this.body.getVoices()) {
            voices.add(v);
        } return voices;
    }
    
    
}
