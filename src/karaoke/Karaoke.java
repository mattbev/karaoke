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
     * @param b the body of the Karaoke
     * @param h the header of the Karaoke
     */
    public Karaoke(Body b, Header h) {
        this.body = b;
        this.header = h;
    }

}
