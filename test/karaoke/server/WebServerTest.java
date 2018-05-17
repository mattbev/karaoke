package karaoke.server;


import org.junit.Test;

/**
 * Tests for WebServer
 *
 */
public class WebServerTest {
    
    // Testing Strategy:
    //
    // All Testing is Manual-
    //  @category no_didit
    //
    //
    // testing lyric streaming as follows
    //
    // partition the inputs as follows:
    //      exchange path has a valid command, invalid command    
    //      karaoke has lyrics to print to web page
    //      karaoke does not contain lyrics        
    //      karaoke has none (so default), one, and > 1 voices
    //      karaoke has notes/chords/tuplets to play aloud 
    //      karaoke does not have anything audible to play (just rests)
    //      single client on server
    //      multiple clients on server
    // partition the outputs as follows:
    //      lyrics successfully printed to web page in real time
    //      no lyrics printed to web page
    //      music played on server's local machine
    //
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // covers:  karaoke has lyrics
    //          lyrics print to web page
    //          one client connection
    //          exchange path has a valid command
    
    @Test
    public void testStreamDefaultVoiceOneConnection() {
        /*
         * Manual testing: streaming lyrics on one connection, from a file with no specified voice (so a default voice "1")
         * 
         * 1. from the karaoke package run from command line: java -cp bin:lib/parserlib.jar karaoke.Main samples/piece3.abc
         *      => assert that the following print to the console:
         *                     a) title and composer (Piece 3 by Unknown), 
         *                     b) instructions about how to view lyrics streams with a web browser, assert only one url listed with /1/ as the extension
         *                     c) instructions about how to start music playback (typing "play" and enter)
         * 2. navigate to that url (it will not finish loading), then go back to the console and type "play".
         *      => assert that the lyrics are printing by line, bolded when they're
         *         supposed to be sung in Amazing Grace, reloading automatically
         */
    }
    
    // covers:  karaoke has lyrics
    //          lyrics print to web page
    //          multiple clients connected
    //          exchange path has a valid command
    
    @Test
    public void testStreamDefaultVoiceTwoConnections() {
        /*
         * Manual testing: streaming lyrics on multiple connections simultaneously, from a file with no specified voice (so a default voice "1")
         * 
         * 1. from the karaoke package run from command line: java -cp bin:lib/parserlib.jar karaoke.Main samples/piece3.abc
         *      => assert that the following print to the console:
         *                     a) title and composer (Piece 3 by Unknown), 
         *                     b) instructions about how to view lyrics streams with a web browser, assert only one url listed with /1/ as the extension
         *                     c) instructions about how to start music playback (typing "play" and enter)
         * 2. navigate to that url in two separate browser windows separately
         * 3. then go back to the console and type "play".
         *      => for each browser window:
         *          assert that the lyrics are printing by line, bolded when they're
         *          supposed to be sung in Amazing Grace, reloading automatically
         */
    }
    
    // covers:  karaoke has lyrics
    //          lyrics print to web page
    //          one client connected
    //          exchange path has a valid command
    
    @Test
    public void testStreamOneVoiceOneConnection() {
        /*
         * Manual testing: streaming lyrics on one connection, from a file with a specified voice "v"
         * 
         * 1. from the karaoke package run from command line: java -cp bin:lib/parserlib.jar karaoke.Main samples/piece3.abc
         *      => assert that the following print to the console:
         *                     a) title and composer (Piece 3 by Unknown), 
         *                     b) instructions about how to view lyrics streams with a web browser, assert only one url listed with /v/ as the extension
         *                     c) instructions about how to start music playback (typing "play" and enter)
         * 2. navigate to that url (it will not finish loading), then go back to the console and type "play".
         *      => assert that the lyrics are printing by line, bolded when they're
         *         supposed to be sung in Amazing Grace, reloading automatically
         */
    
    }
    
    // covers:  karaoke has lyrics, one voice
    //          lyrics print to web page
    //          multiple clients connected
    //          exchange path has a valid command
    
    @Test
    public void testStreamOneVoiceTwoConnections() {
        /*
         * Manual testing: streaming lyrics on mutiple connections, from a file with a specified voice "v"
         * 
         * 1. from the karaoke package run from command line: java -cp bin:lib/parserlib.jar karaoke.Main samples/piece3.abc
         *      => assert that the following print to the console:
         *                     a) title and composer (Piece 3 by Unknown), 
         *                     b) instructions about how to view lyrics streams with a web browser, assert only one url listed with /v/ as the extension
         *                     c) instructions about how to start music playback (typing "play" and enter)
         * 2. navigate to that url in two separate browser windows separately
         * 3. then go back to the console and type "play".
         *      => for each browser window:
         *          assert that the lyrics are printing by line, bolded when they're
         *          supposed to be sung in Amazing Grace, reloading automatically
         */
         
    
    }
    
    
    // covers:  karaoke has lyrics, multiple voices
    //          lyrics print to web page
    //          multiple clients connected
    //          exchange path has a valid command
    
    @Test
    public void testStreamMultipleVoicesMultipleConnections() {
        /*
         * Manual testing: streaming lyrics on mutiple connections, from a file with a specified voices, going to one voice twice
         * 
         * 1. from the karaoke package run from command line: java -cp bin:lib/parserlib.jar karaoke.Main samples/despacito.abc
         *      => assert that the following print to the console:
         *                     a) title and composer (T:Despacito (simplified excerpt) by Luis Fonsi), 
         *                     b) instructions about how to view lyrics streams with a web browser, assert three urls listed
         *                        with /Guitar1/, /Guitar2/, and /Voice/ as the extensions
         *                     c) instructions about how to start music playback (typing "play" and enter)
         * 2. navigate to each url in a separate browser windows separately, 
         * 3. navigate to the /Voice/ url in another separete windows
         * 3. assert that in both /Voice/ tabs, the lyrics are printing by line, bolded when they're
         *    supposed to be sung in Despacito, and that the guitar urls have no lyrics streaming
         */
    }
    
    
    // covers:  karaoke has no lyrics
    //          no lyrics print to web page
    //          one client connection
    //          exchange path has a valid command
    
    @Test
    public void testStreamRestsEmptyLineOfLyrics() {
        /*
         * Manual testing: streaming lyrics on one connection, from a file with no specified voice (so a default voice "1"), all rests, and empty lyric line
         * 
         * 1. from the karaoke package run from command line: java -cp bin:lib/parserlib.jar karaoke.Main samples/rests_empty_lyrics.abc
         *      => assert that the following print to the console:
         *                     a) title and composer (R by Unknown), 
         *                     b) instructions about how to view lyrics streams with a web browser, assert only one url listed with /1/ as the extension
         *                     c) instructions about how to start music playback (typing "play" and enter)
         * 2. navigate to that url (it will not finish loading), then go back to the console and type "play".
         *      => assert that no lyrics print to the web browser, as nothing should be sung. 
         * 
         */
    }
    
    // covers:  karaoke has no lyrics
    //          no lyrics print to web page
    //          one client connection
    //          exchange path has an invalid command
    
    @Test
    public void testStreamInvalidCommand() {
        /*
         * Manual testing: streaming lyrics on one connection, from a file with no specified voice (so a default voice "1"), all rests, and empty lyric line
         * 
         * 1. from the karaoke package run from command line: java -cp bin:lib/parserlib.jar karaoke.Main samples/rests_empty_lyrics.abc
         *      => assert that the following print to the console:
         *                     a) title and composer (R by Unknown), 
         *                     b) instructions about how to view lyrics streams with a web browser, assert only one url listed with /1/ as the extension
         *                     c) instructions about how to start music playback (typing "play" and enter)
         * 2. attempt to navigate to the given url, but with /2/ as the extension, not /1/, then go back to the console and type "play".
         *      => assert that no lyrics print to the web browser, as nothing should be streamed to this invalid voices url
         *          ** whether it closes, never loads, etc is unspecified, just make sure no lyrics print to it. 
         */
    }
    
}
