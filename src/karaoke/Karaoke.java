package karaoke;

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
     * Provides a copy of the lyric lines of a certain voice
     * @param voice voice whose lyric lines are returned
     * @return the lyric lines of voice <voice>
     */
    public List<String> getVoiceLyricLinesList(String voice){
        List<String> lyricLines = this.body.getVoiceLyricLines(voice);
        return lyricLines;

    }

}
