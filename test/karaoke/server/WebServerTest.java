package karaoke.server;



/**
 * Tests for WebServer
 *
 */
public class WebServerTest {
    
    // testing strategy
    //
    // testing handleWatch(HttpExchange exchange, Karaoke karaoke)
    // partition the inputs as follows:
    //   exchange path is empty 
    //   exchange path has a invalid command
    //   exchange path has a valid command
    //   karaoke has lyrics to print to web page
    //   karaoke does not contain lyrics
    //   karaoke has notes/chords/tuplets to play aloud
    //   karaoke does not have anything audible to play (just rests)
    //   single clients on server
    //   multiple clients on server
    // partition the outputs as follows:
    //   lyrics successfully printed to web page in real time
    //   no lyrics printed to web page
    //   music played on server's local machine
    //
    //
    // testing port()
    // partition the inputs as follows:
    //   a webserver to call the function on
    // partition the outputs as follows:
    //   a valid port number (that the server is listening on)
    //   an invalid port number (that the server is not listening on)
    //
    //
    // testing start()
    // partition the inputs as follows:
    //   a webserver to call the function on
    // partition the outputs as follows:
    //   a running server
    //   a stopped server (failed case)
    //
    //
    // testing stop()
    // partition the inputs as follows:
    //   a webserver to call the function on
    // partition the outputs as follows:
    //   a stopped server
    //   a running server (failed case)
    
    
}
