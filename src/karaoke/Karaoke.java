package karaoke;


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

}
