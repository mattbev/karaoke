package karaoke.server;


import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.junit.Test;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.Karaoke;
import parser.KaraokeParser;

/**
 * Tests for WebServer
 *
 */
public class WebServerTest {
    
    // testing strategy
    //
    // testing html2(HttpExchange exchange, Karaoke karaoke)
    // partition the inputs as follows:
    //   exchange path has a valid command       *
    //   karaoke has lyrics to print to web page *
    //   karaoke does not contain lyrics         *
    //   karaoke has notes/chords/tuplets to play aloud *
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
    //   a webserver to call the function on  *
    // partition the outputs as follows:
    //   a valid port number (that the server is listening on)*
    //   an invalid port number (that the server is not listening on)*
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
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    

    
    //Covers:
    //
    // Testing html2()
    //Partion Input:
    //-exchange path has a valid command
    //-karaoke does not contain lyrics
    //-karaoke has notes, chords, and tuplets
    //   to play aloud
    //Partition Output:
    //-lyrics successfully printed to web page in real time
    //
    // Testing port()
    //Partition Input:
    //-a webserver to call the function on
    //Partion Output:
    //-a valid port number (that the server is listening on)
    //
    //Testing start()
    //Partition Input:
    //-a webserver to call the function on
    //Partition Output:
    //-a running server
    //
    //Testing stop()
    //Partition Input:
    //-a webserver to call the function on
    //Partition Output:
    //-a running server 
    @Test
    public void testhtml2ExchangePathValidCommandNoLyrics() throws IOException, URISyntaxException, UnableToParseException {
        Karaoke karaoke;
        try {
            File f = new File("samples/piece2.abc");
            List<String> s = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
            String contents = String.join("\n", s) +"\n";
            karaoke = KaraokeParser.parse(contents);
        
        } catch (IOException e) {
            throw new UnableToParseException("Unable to parse file");
        } 
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        for(String voice: karaoke.getVoices()) {
            HttpContext url = server.createContext("/"+voice, new HttpHandler(){
                public void handle(HttpExchange exchange) throws IOException{
                    
                    //We enter this body if we have a valid exchange path command
                    
                    //html2(exchange);
                    
                }
            });
        }
    }
    
    
    
    //Covers:
    //
    // Testing html2()
    //Partion Input:
    //-karaoke does contain lyrics
    //
    //Testing port()
    //Partition Input:
    //-a webserver to call the function on
    //Partition Output:
    //-an invalid port number (that the server is not listening on)
    //
    //Testing start()
    //Partition Input:
    //-a webserver to call the function on
    //Partition Output:
    //-a stopped server (failed case)
    //
    //Testing stop()
    //Partition Input:
    //-a webserver to call the function on
    //Partition Output:
    //-a stopped server
    
    @Test
    public void testhtml2ExchangePathValidCommandLyrics() throws IOException, URISyntaxException, UnableToParseException {
        Karaoke karaoke;
        try {
            File f = new File("samples/piece3.abc");
            List<String> s = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
            String contents = String.join("\n", s) +"\n";
            karaoke = KaraokeParser.parse(contents);
        
        } catch (IOException e) {
            throw new UnableToParseException("Unable to parse file");
        } 
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        
        for(String voice: karaoke.getVoices()) {
            HttpContext url = server.createContext("/"+voice, new HttpHandler(){
                public void handle(HttpExchange exchange) throws IOException{
                    
                    //We enter this body if we have a valid exchange path command
                    
                    //html2(exchange);
                    
                }
            });
        }
    }
}
