package karaoke.server;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
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
 * 
 * A web server to act as a karaoke lyric streaming machine for a specified song
 */
public class WebServer {
    
    private final HttpServer server;
    private final Karaoke karaoke;
    private final Map<String,BlockingQueue<LyricLine>> bq = new HashMap<>();
    private final Map<String, Integer> countPerVoice = new HashMap<>();
    
    // Abstraction function:
    //   AF(server, karaoke, bq) = a new WebServer instance, where the lyrics for karaoke are streamed on server, where
    //                  the lyrics for each voice v of the karaoke are being controlled in the queue bq.get(v)
    // Rep invariant:
    //   true
    // Safety from rep exposure:
    //   all fields private and final
    //   defensive copies are made of inputs 
    // 
    // Thread safety argument:
    //   the shared karaoke is immutable, with immutable lyrics as well
    //   thread safe bc will never have multiple users on the same thread (multiple players are on multiple threads)
    //   multiple users have their own blocking queues
    //   uses thread safe types internally 
    //   mutable rep only changed in constructor that is a threadsafe java method


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


        server.setExecutor(Executors.newCachedThreadPool());
        LogFilter log = new LogFilter();
        HeadersFilter headers = new HeadersFilter();
        // all responses will be plain-text UTF-8
        //headers.add("Content-Type", "text/html; charset=utf-8");
        for(String voice: this.karaoke.getVoices()) {
            HttpContext url = this.server.createContext("/"+voice, new HttpHandler(){
                public void handle(HttpExchange exchange) throws IOException{
                    countPerVoice.put(voice, countPerVoice.get(voice) + 1);
                    
                    html2(exchange);
                    
                }
            });
            bq.put(voice, new LinkedBlockingQueue<>());
            countPerVoice.put(voice, 0);
        
            // add logging to the /hello/ handler and set required HTTP headers
            //   (do this on all your handlers)
            //url.getFilters().addAll(Arrays.asList(log, headers));
        }
        
        checkRep();
    }
    
    /**
     * assert RI
     */
    private void checkRep() {
        assert server != null;
        assert karaoke != null;
        assert bq != null; //TODO
    }
    
   
    
    /**
     * @return the port on which this server is listening for connections
     */
    public int port() {
        checkRep();
        return server.getAddress().getPort();
    }
    
    /**
     * Start this server in a new background thread.
     */
    public void start() {
        server.start();
        checkRep();
    }
    
    /**
     * Stop this server. Once stopped, this server cannot be restarted.
     */
    public void stop() {
        server.stop(0);
        checkRep();
    }
    
    /**
     * Put s a line of lyrics into a blocking queue
     * 
     * @param l the line of lyrics, with one bolded syllable, to be put into the queue
     * @throws InterruptedException if the thread is interrupted
     */
    public void putInBlockingQueue(LyricLine l) throws InterruptedException {
        for (int i = 0; i < countPerVoice.get(l.getVoice()); i ++) {
            bq.get(l.getVoice()).put(l);
        }
        checkRep();
        
    }
    
    private void html2(HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();
        

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
                LyricLine next = bq.get(path.substring(1, path.length() - 1)).take();
                out.println(next.toString());
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
            countPerVoice.put(path.substring(1, path.length() - 1), countPerVoice.get(path.substring(1, path.length() - 1)) - 1); 
        }
    }
    
}