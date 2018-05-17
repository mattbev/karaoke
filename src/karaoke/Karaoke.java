package karaoke;

import java.util.ArrayList;
import java.util.List;


import karaoke.server.WebServer;
import karaoke.sound.Instrument;
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
     * check the stated and implied rep invariant
     */
    private void checkRep() {
        assert body != null;
        assert header != null;
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
     * @param i the instrument to play the karaoke on
     */
    public void play(SequencePlayer player, WebServer server, Instrument i) {
        checkRep();
        body.play(player, server, i);
    }
    
    /**
     * Get the title of this karaoke song
     * 
     * @return the title of this piece
     */
    public String getTitle() {
        checkRep();
        return this.header.getTitle();
    }

    /**
     * Get the composer of the song being played on karaoke
     * 
     * @return the composer of this song
     */
    public String getComposer() {
        checkRep();
        return this.header.getComposer();
    }
    
    
    /**
     * Provides all the voice in the karaoke's body
     * @return list of voices in the body of the karaoke
     */
    public List<String> getVoices() {
        checkRep();
        List<String> voices = new ArrayList<>();
        for (String v : this.body.getVoices()) {
            voices.add(v);
        } return voices;
    }
    
    /**
     * get the body of the karaoke
     * @return the karaoke body
     */
    public Body getBody() {
        checkRep();
        return this.body;
    }
    
    /**
     * get the header of the karaoke
     * @return the karaoke header
     */
    public Header getHeader() {
        checkRep();
        return this.header;
    }
    
    /**
     * 
     * @param voice the voice you want the lyrics for 
     * @return the list of lyric lines for this karaoke for this voice
     */
    public List<String> getLinesOfLyrics(String voice) {
        checkRep();
        List<String> allLyrics = new ArrayList<>();
        for (Playable p : this.body.getVoicesToMusics().get(voice).getComponents()) {
            allLyrics.add(p.getLyricLine().toString());
        } return allLyrics;
    }
}
