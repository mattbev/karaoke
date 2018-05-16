package karaoke.server;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import karaoke.Karaoke;
import karaoke.LyricLine;


/**
 * 
 * @author chessa, mattbev, sophias
 * An HTTP karaoke server
 */
public class WebServer {
    public static final int AUTOSCROLL_LETTERS = 10;
    
    private final HttpServer server;
    private final Karaoke karaoke;
    private final BlockingQueue<LyricLine> bq = new LinkedBlockingQueue();
//    private final List<BlockingQueue<LyricLine>> bq1 = new ArrayList<>();
    
    // Abstraction function:
    //   AF(server, karaoke) = a new WebServer instance, where the lyrics for karaoke are streamed on server
    // Rep invariant:
    //   true
    // Safety from rep exposure:
    //   all fields private and final
    //   defensive copies are made of inputs 
    // 
    // Thread safety argument:
    //   the shared karaoke is immutable, with immutable lyrics as well
    //   safe b/c will never have multiple users on the same thread (multiple players are on multiple threads)
    //   multiple users have their own blocking queues
    //   uses thread safe types internally


    /**
     * Make a new web server for karaoke that listens for connections on port.
     * 
     * @param karaoke shared karaoke stream
     * @param port server port number
     * @throws IOException if an error occurs starting the server
     */
    public WebServer(Karaoke karaoke, int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.karaoke = karaoke;

//        LogFilter log = new LogFilter();
//        HeadersFilter headers = new HeadersFilter();
        // all responses will be plain-text UTF-8
//        headers.add("Content-Type", "text/plain; charset=utf-8");
        for(String voice: karaoke.getVoices()) {
            HttpContext url = this.server.createContext("/"+voice, new HttpHandler(){
                public void handle(HttpExchange exchange) throws IOException{
                    html2(karaoke, exchange);
                }
            });
        
            // -or- use a lambda expression with "->"
            //HttpContext hello = server.createContext("/hello/", exchange -> handleHello(exchange));
            // -or- use a method reference with "::"
            //HttpContext hello = server.createContext("/hello/", this::handleHello);
            
            // add logging to the /hello/ handler and set required HTTP headers
            //   (do this on all your handlers)
//            url.getFilters().addAll(Arrays.asList(log, headers));
        }
        
        
        checkRep();
    }
    
    /**
     * assert RI
     */
    private void checkRep() {
        assert true;
    }
    
    
    /**
     * This handler sends a stream of HTML to the web browser,
     * pausing briefly between each line of output.
     * Returns after the entire stream has been sent.
     * 
     * @param exchange request/reply object
     * @param karaoke karaoke object to be streamed
     * @throws IOException if network problem
     */
    private static void htmlStream(HttpExchange exchange, Karaoke karaoke) throws IOException {
        
        //JUST NEEDS TO BE CALLED FOR EACH VOICE'S URL WHEN MUSIC IS STARTED
        
        final String path = exchange.getRequestURI().getPath();
        final String base = exchange.getHttpContext().getPath();     
        assert path.startsWith(base);
        
        String voice = path.substring(base.length());     //create substring of voice
        
        List<Double> voiceDurationList = karaoke.getDurationList(voice);

        
        //get list of lyricLines that corresponds to voice
        List<String> voiceLyrics = karaoke.getVoiceLyricLinesList(voice);
        System.err.println("received request " + path);
    
        final boolean autoscroll = true;     //path.endsWith("/autoscroll");
        
        // html response
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        
        // must call sendResponseHeaders() before calling getResponseBody()
        final int successCode = 200;
        final int lengthNotKnownYet = 0;
        exchange.sendResponseHeaders(successCode, lengthNotKnownYet);

        // get output stream to write to web browser
        final boolean autoflushOnPrintln = true;
        PrintWriter out = new PrintWriter(
                              new OutputStreamWriter(
                                  exchange.getResponseBody(), 
                                  StandardCharsets.UTF_8), 
                              autoflushOnPrintln);
        
        try {

            // IMPORTANT: some web browsers don't start displaying a page until at least 2K bytes
            // have been received.  So we'll send a line containing 2K spaces first.
            final int enoughBytesToStartStreaming = 2048;
            for (int i = 0; i < enoughBytesToStartStreaming; ++i) {
                out.print(' ');
            }
            out.println(); // also flushes
            
            final int numberOfLinesToSend = voiceLyrics.size();
            //final int millisecondsBetweenLines = 200;
            
            for (int i = 0; i < numberOfLinesToSend; ++i) {
                
                // print a line of text
                
                out.println(voiceLyrics.get(i) + "<br>"); // also flushes  //print that line of the lyrics list
                
                if (autoscroll) {
                    // send some Javascript to browser that makes it scroll down to the bottom of the page,
                    // so that the last line sent is always in view
                    out.println("<script>document.body.scrollIntoView(false)</script>");
                }
                
                long currentNoteDuration = voiceDurationList.get(i).longValue();    //get the duration at the corresponding index that matches the lyric
                
                // wait a bit
                try {
                    Thread.sleep(currentNoteDuration);
                } catch (InterruptedException e) {
                    return;
                }
            }
            
        } finally {
            exchange.close();
        }
        System.err.println("done streaming request");
    }
    
    /**
     * 
     * @param exchange the Http exchange where the lyrics are being watched
     * @param karaoke the karaoke to be watched 
     */
//    private void handleWatch(HttpExchange exchange, Karaoke karaoke) throws IOException {
//     // if you want to know the requested path:
//        final String path = exchange.getRequestURI().getPath();
//        
//        // it will always start with the base path from server.createContext():
//        final String base = exchange.getHttpContext().getPath();     
//        assert path.startsWith(base);
//        
//        final String voiceID = path.substring(base.length());
//        
//        final String response;
//        final Player player;
//        exchange.sendResponseHeaders(200, 0);
//        if (board.getPlayers().containsKey(playerID)) {
//            // if the request is valid, respond with HTTP code 200 to indicate success
//            player = board.getPlayerFromID(playerID);
////        } else {
////            player = new Player(playerID, board);
////        }
//        response = player.boardToStringWeb();
////        else {
////            // otherwise, respond with HTTP code 404 to indicate an error
////            exchange.sendResponseHeaders(404, 0);
////            response = "player " + playerID + " is an invalid player";
////        }
//        // write the response to the output stream using UTF-8 character encoding
//        OutputStream body = exchange.getResponseBody();
//        PrintWriter out = new PrintWriter(body, true);
//        out.println(response);
//        exchange.close();
//    }
//    }
    
    //uncomment the above ^^
    
    
    // this was handleLook from ps4, as a guide for when we implement if needed
    
    
    /*
     * Handle a request for /look/<player> 
     *  
     * 
     * @param exchange HTTP request/response, modified by this method to send a
     *                 response to the client and close the exchange
     
    private void handleLook(HttpExchange exchange, Board board) throws IOException {
     // if you want to know the requested path:
        final String path = exchange.getRequestURI().getPath();
        
        // it will always start with the base path from server.createContext():
        final String base = exchange.getHttpContext().getPath();
        assert path.startsWith(base);
        
        final String playerID = path.substring(base.length());
        
        final String response;
        final Player player;
        exchange.sendResponseHeaders(200, 0);
        if (board.getPlayers().containsKey(playerID)) {
            // if the request is valid, respond with HTTP code 200 to indicate success
            player = board.getPlayerFromID(playerID);
        } else {
            player = new Player(playerID, board);
        }
        response = player.boardToStringWeb();
        else {
            // otherwise, respond with HTTP code 404 to indicate an error
            exchange.sendResponseHeaders(404, 0);
            response = "player " + playerID + " is an invalid player";
        }
        // write the response to the output stream using UTF-8 character encoding
        OutputStream body = exchange.getResponseBody();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
        out.println(response);
        exchange.close();
    }
    
    /*
     * Handle a request for /flip/<player>/<row>,<col> 
     *  
     * 
     * @param exchange HTTP request/response, modified by this method to send a
     *                 response to the client and close the exchange
     *
    private void handleFlip(HttpExchange exchange, Board board) throws IOException {
        // if you want to know the requested path:
           final String path = exchange.getRequestURI().getPath();
           
           // it will always start with the base path from server.createContext():
           final String base = exchange.getHttpContext().getPath();
           assert path.startsWith(base);
           
           final String[] parameters = path.substring(base.length()).split("/");
           final String[] coords = parameters[1].split(",");
           final String playerID = parameters[0];
           final int row = Integer.parseInt(coords[0]);
           final int column = Integer.parseInt(coords[1]);
           
           final String response;
           final Player player;
           exchange.sendResponseHeaders(200, 0);
           if (board.getPlayers().containsKey(playerID)) {
               // if the request is valid, respond with HTTP code 200 to indicate success
               player = board.getPlayerFromID(playerID);
           } else {
               player = new Player(playerID, board);
           }
           player.flip(row-1, column-1);
           response = player.boardToStringWeb();
           else {
               // otherwise, respond with HTTP code 404 to indicate an error
               exchange.sendResponseHeaders(404, 0);
               response = "player " + playerID + " is an invalid player";
           }
           // write the response to the output stream using UTF-8 character encoding
           OutputStream body = exchange.getResponseBody();
           PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
           out.println(response);
           exchange.close();
       }
    */
    
    
    /**
     * @return the port on which this server is listening for connections
     */
    public int port() {
        return server.getAddress().getPort();
    }
    
    /**
     * Start this server in a new background thread.
     */
    public void start() {
        System.err.println("Server will listen on " + server.getAddress());
        server.start();
        //htmlString should be called here?
    }
    
    /**
     * Stop this server. Once stopped, this server cannot be restarted.
     */
    public void stop() {
        System.err.println("Server will stop");
        server.stop(0);
    }
    
    /**
     * Put s a line of lyrics into a blocking queue
     * 
     * @param l the line of lyrics, with one bolded syllable, to be put into the queue
     * @throws InterruptedException if the thread is interrupted
     */
    public void putInBlockingQueue(LyricLine l) throws InterruptedException {
        bq.put(l);
        
    }
    
    private void html2(Karaoke karaoke, HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();
        
        System.err.println("received request " + path);

        // html response
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        
        // must call sendResponseHeaders() before calling getResponseBody()
        final int successCode = 200;
        final int lengthNotKnownYet = 0;
        exchange.sendResponseHeaders(successCode, lengthNotKnownYet);

        // get output stream to write to web browser
        final boolean autoflushOnPrintln = true;
        PrintWriter out = new PrintWriter(
                              new OutputStreamWriter(
                                  exchange.getResponseBody(), 
                                  StandardCharsets.UTF_8), 
                              autoflushOnPrintln);
        
        try {

            // Wait until an event occurs in the server.
            // In this example, the event is just a brief fixed-length delay, but it
            // could synchronize with another thread instead.
            try {
                LyricLine next = bq.take();
                out.println(next.getText());
            } catch (InterruptedException e) {
                return;
            }
            
            // Send a full HTML page to the web browser
            //out.println(System.currentTimeMillis() + "<br>");
            
            // End the page with Javascript that causes the browser to immediately start 
            // reloading this URL, so that this handler runs again and waits for the next event
            out.println("<script>location.reload()</script>");
            
        } finally {
            exchange.close();
        }
        System.err.println("done streaming request");
    }
    
}