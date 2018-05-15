package karaoke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.sun.net.httpserver.HttpServer;

import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.parser.KaraokeParser;
import karaoke.server.WebServer;
import karaoke.sound.MusicPlayer;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Main entry point of the karaoke machine, run to make karaoke song requests and stream lyrics
 */
public class Main {

  
    
    /**
     * Start a karaoke server using the given ABC music file.
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
            /*
            List<String> s = Files.readAllLines(Paths.get(songPath), StandardCharsets.UTF_8);
            String contents = String.join("\n", s);
            karaoke = KaraokeParser.parse(contents);
            List<String> voices = karaoke.getVoices();
            List<String> urls = new ArrayList<>();
            
            String mainUrl = "http://" + iPAddress + ":" + port + "/" + "/";
            for (String voice : voices) {
                urls.add(mainUrl + voice);
            }
            System.out.println("Playing " + karaoke.getTitle() + " by " + karaoke.getComposer());
            WebServer server = new WebServer(karaoke, port);
            
            */
            
            System.out.println("To view the lyrics, navigate in your browser to one of the following urls, where the extension"
                    + " indicates which voice's lyrics will be streaming at that url:");
//            System.out.println(urls);
            System.out.println("Begin playing the music by typing \"play\" and hitting Enter");
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
            if (b.readLine().equals("play")) {
                //server.start();
                //MusicPlayer.play(karaoke);
                System.out.println("would play karaoke here");
                
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("error in opening and parsing file");
        }
       
        }
    }
    
    
