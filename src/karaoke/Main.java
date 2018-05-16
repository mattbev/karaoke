package karaoke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import karaoke.server.WebServer;
import karaoke.sound.MusicPlayer;
import parser.KaraokeParser;

/**
 * Main entry point of the karaoke machine, run to make karaoke song requests and stream lyrics
 */
public class Main {

  
    
    /**
     * Start a karaoke machine using the given ABC music file.
     * 
     * <p> Command-line usage:
     * 
     * <pre> java -cp bin:lib/parserlib.jar some.package.Main FILENAME1 </pre>
     * where:
     * 
     * <p> FILENAME1 is the path to an ABC music file to be played
     * 
     * 
     * @param args arguments as described above
     * @throws IOException if an error occurs communicating with the server
     */
    public static void main(String[] args) throws IOException {
        
        final Queue<String> arguments = new LinkedList<>(Arrays.asList(args));
        final String songPath;
        final Karaoke karaoke;
        final int port = 8080;
        String iPAddress = InetAddress.getLocalHost().getHostAddress();
        try {
            songPath = arguments.remove();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("missing filename");
        }
        
        try {
            List<String> s = Files.readAllLines(Paths.get(songPath), StandardCharsets.UTF_8);
            String contents = String.join("\n",s);
            karaoke = KaraokeParser.parse(contents);
            List<String> voices = karaoke.getVoices();
            List<String> urls = new ArrayList<>();
            
            String mainUrl =  iPAddress + ":" + port + "/";
            for (String voice : voices) {
                urls.add(mainUrl + voice + "/");
            }
            WebServer server = new WebServer(karaoke, port);
            server.start();
            
            System.out.println("\nReady to play " + karaoke.getTitle() + " by " + karaoke.getComposer());
            System.out.println("\nTo get ready to view the lyrics, navigate in your browser to one of the following urls, where the extension"
                    + " indicates which voice's lyrics will be streaming at that url, where voice \"1\" is the default if your file specified no voice:");
            System.out.println("\n"+String.join("\n", urls));

            System.out.println("\nBegin playing the music and streaming the lyrics by typing \"play\" and hitting Enter");

            System.out.println("\nTo exit at any time, press Ctrl-C");
            System.out.println(karaoke.getLyrics("1"));
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
            if (b.readLine().equals("play")) {
                MusicPlayer.play(karaoke,server);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("error in opening and parsing file");
        }
       
        }
    }
    
    
