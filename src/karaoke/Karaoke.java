package karaoke;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Karaoke is an immutable karaoke object that contains all information about the abc file    ????????
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
    //   true             <<<<<<????????????
    //
    // Rep Safety Argument:
    //   all fields are private final
    //   all getter methods return Strings which are immutable 
    //
    // Thread Safety Argument:
    //   no rep fields are mutated outside of the constructor, which is a threadsafe method by default
    
    /**
     * creates an instance of a Karaoke object
     * @param b the body of the Karaoke
     * @param h the header of the Karaoke
     */
    private Karaoke(Header h, Body b) {
        this.body = b;
        this.header = h;
    }
    
    
    /**
     * creates an new karaoke
     * @param b the body of the Karaoke
     * @param h the header of the Karaoke
     * @return a new Karaoke object
     */
    public static Karaoke createKaraoke(Header h, Body b) {
        return new Karaoke(h,b);
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
    
    public List<String> getVoices() {
        List<String> voices = new ArrayList<>();
        for (String v : this.body.getVoices()) {
            voices.add(v);
        } return voices;
    }
}
