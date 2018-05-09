package karaoke.server;
import karaoke.music.*;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * 
 * @author chessa, mattbev, sophias
 * An HTTP karaoke server
 */
public class WebServer {

    private final HttpServer server;
    private final Karaoke karaoke;

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
    //   will never have multiple web servers on the same thread (multiple players are on multiple threads)
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
        checkRep();
    }
    
    /**
     * assert RI
     */
    private void checkRep() {
        assert true;
    }
    
    /**
     * 
     * @param exchange the Http exchange where the lyrics are being watched
     * @param karaoke the karaoke to be watched 
     */
    private void handleWatch(HttpExchange exchange, Karaoke karaoke) throws IOException { 
        ;
    }
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
    }
    
    /**
     * Stop this server. Once stopped, this server cannot be restarted.
     */
    public void stop() {
        System.err.println("Server will stop");
        server.stop(0);
    }
}