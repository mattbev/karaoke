package karaoke.server;


public class WebServer {

}
//
///* Copyright (c) 2017-2018 MIT 6.031 course staff, all rights reserved.
// * Redistribution of original or derived work requires permission of course staff.
// */
//package memory;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.InetSocketAddress;
//import java.util.Arrays;
//import java.util.concurrent.Executors;
//
//import com.sun.net.httpserver.HttpContext;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpServer;
//
//import memory.web.HeadersFilter;
//import memory.web.LogFilter;
//
///**
// * HTTP web game server.
// * 
// * <p>PS4 instructions: the specifications of {@link #WebServer(Board, int)},
// * {@link #port()}, {@link #start()}, and {@link #stop()} are required.
// */
//public class WebServer {
//    
//    private final Board board;        
//    private final HttpServer server;
//    
//    // Abstraction function:
//    //   AF(board, server) = a new WebServer instance representing a memory scramble playing board
//    // Representation invariant:
//    //   true;
//    // Safety from rep exposure:
//    //   all fields private final
//    //   copies are made before mutations
//    // Thread safety argument:
//    //   the shared game board's mutable portions (cards) are wrapped in synchronuzed blocks
//    //   will never have multiple web servers on the same thread
//    //   uses threadsafe types internally
//    
//    /**
//     * Make a new web game server using board that listens for connections on port.
//     * 
//     * @param newBoard shared game board
//     * @param port server port number
//     * @throws IOException if an error occurs starting the server
//     */
//    public WebServer(Board newBoard, int port) throws IOException {
//        this.server = HttpServer.create(new InetSocketAddress(port), 0);
//        this.board = newBoard;
//
//        // handle concurrent requests with multiple threads
//        server.setExecutor(Executors.newCachedThreadPool());
//        
//        LogFilter log = new LogFilter();
//        HeadersFilter headers = new HeadersFilter();
//        // allow requests from web pages hosted anywhere
//        headers.add("Access-Control-Allow-Origin", "*");
//        // all responses will be plain-text UTF-8
//        headers.add("Content-Type", "text/plain; charset=utf-8");     
//        
//        // handle requests for /look/player
//        HttpContext look = server.createContext("/look/", new HttpHandler() {
//            public void handle(HttpExchange exchange) throws IOException {
//                handleLook(exchange, board);
//            }
//        });
//        
//        look.getFilters().addAll(Arrays.asList(log, headers));
//        
//        // handle requests for /flip/player/row,column
//        HttpContext flip = server.createContext("/flip/", new HttpHandler() {
//            public void handle(HttpExchange exchange) throws IOException {
//                handleFlip(exchange, board);
//            }
//        });
//        
//        flip.getFilters().addAll(Arrays.asList(log, headers));
//        
//        // handle requests for /watch/player
////        HttpContext watch = server.createContext("/watch/", new HttpHandler() {
////            public void handle(HttpExchange exchange) throws IOException {
////                handleWatch(exchange, board);
////            }
////        });
//    }
//    
//    /**
//     * checks that the rep is not violated
//     */
//    private void checkRep() {
//        assert true;
//    }
//    
//    /*
//     * Handle a request for /look/<player> 
//     *  
//     * 
//     * @param exchange HTTP request/response, modified by this method to send a
//     *                 response to the client and close the exchange
//     */
//    private void handleLook(HttpExchange exchange, Board board) throws IOException {
//     // if you want to know the requested path:
//        final String path = exchange.getRequestURI().getPath();
//        
//        // it will always start with the base path from server.createContext():
//        final String base = exchange.getHttpContext().getPath();
//        assert path.startsWith(base);
//        
//        final String playerID = path.substring(base.length());
//        
//        final String response;
//        final Player player;
//        exchange.sendResponseHeaders(200, 0);
//        if (board.getPlayers().containsKey(playerID)) {
//            // if the request is valid, respond with HTTP code 200 to indicate success
//            player = board.getPlayerFromID(playerID);
//        } else {
//            player = new Player(playerID, board);
//        }
//        response = player.boardToStringWeb();
////        else {
////            // otherwise, respond with HTTP code 404 to indicate an error
////            exchange.sendResponseHeaders(404, 0);
////            response = "player " + playerID + " is an invalid player";
////        }
//        // write the response to the output stream using UTF-8 character encoding
//        OutputStream body = exchange.getResponseBody();
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
//        out.println(response);
//        exchange.close();
//    }
//    
//    /*
//     * Handle a request for /flip/<player>/<row>,<col> 
//     *  
//     * 
//     * @param exchange HTTP request/response, modified by this method to send a
//     *                 response to the client and close the exchange
//     */
//    private void handleFlip(HttpExchange exchange, Board board) throws IOException {
//        // if you want to know the requested path:
//           final String path = exchange.getRequestURI().getPath();
//           
//           // it will always start with the base path from server.createContext():
//           final String base = exchange.getHttpContext().getPath();
//           assert path.startsWith(base);
//           
//           final String[] parameters = path.substring(base.length()).split("/");
//           final String[] coords = parameters[1].split(",");
//           final String playerID = parameters[0];
//           final int row = Integer.parseInt(coords[0]);
//           final int column = Integer.parseInt(coords[1]);
//           
//           final String response;
//           final Player player;
//           exchange.sendResponseHeaders(200, 0);
//           if (board.getPlayers().containsKey(playerID)) {
//               // if the request is valid, respond with HTTP code 200 to indicate success
//               player = board.getPlayerFromID(playerID);
//           } else {
//               player = new Player(playerID, board);
//           }
//           player.flip(row-1, column-1);
//           response = player.boardToStringWeb();
////           else {
////               // otherwise, respond with HTTP code 404 to indicate an error
////               exchange.sendResponseHeaders(404, 0);
////               response = "player " + playerID + " is an invalid player";
////           }
//           // write the response to the output stream using UTF-8 character encoding
//           OutputStream body = exchange.getResponseBody();
//           PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
//           out.println(response);
//           exchange.close();
//       }
//    
//    /**
//     * @return the port on which this server is listening for connections
//     */
//    public int port() {
//        return server.getAddress().getPort();
//    }
//    
//    /**
//     * Start this server in a new background thread.
//     */
//    public void start() {
//        System.err.println("Server will listen on " + server.getAddress());
//        server.start();
//    }
//    
//    /**
//     * Stop this server. Once stopped, this server cannot be restarted.
//     */
//    public void stop() {
//        System.err.println("Server will stop");
//        server.stop(0);
//    }
//    
//    /*
//     * Handle a request for /hello/<what> by responding with "Hello, <what>!" if
//     *   <what> is a single word; error 404 otherwise.
//     * 
//     * @param exchange HTTP request/response, modified by this method to send a
//     *                 response to the client and close the exchange
//     */
//    private void handleHello(HttpExchange exchange) throws IOException {
//        // if you want to know the requested path:
//        final String path = exchange.getRequestURI().getPath();
//        
//        // it will always start with the base path from server.createContext():
//        final String base = exchange.getHttpContext().getPath();
//        assert path.startsWith(base);
//        
//        final String whatToGreet = path.substring(base.length());
//        
//        final String response;
//        if (whatToGreet.matches("\\w+")) {
//            // if the request is valid, respond with HTTP code 200 to indicate success
//            // - response length 0 means a response will be written
//            // - you must call this method before calling getResponseBody()
//            exchange.sendResponseHeaders(200, 0);
//            response = "Hello, " + whatToGreet + "!";
//        } else {
//            // otherwise, respond with HTTP code 404 to indicate an error
//            exchange.sendResponseHeaders(404, 0);
//            response = "Go away, " + whatToGreet + ".";
//        }
//        // write the response to the output stream using UTF-8 character encoding
//        OutputStream body = exchange.getResponseBody();
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
//        out.println(response);
//        
//        // if you do not close the exchange, the response will not be sent!
//        exchange.close();
//    }
//}