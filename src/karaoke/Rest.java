/**
 * 
 */
package karaoke;

import java.util.function.Consumer;

import karaoke.server.WebServer;
import karaoke.sound.Instrument;
import karaoke.sound.SequencePlayer;

/**
 * @author chessa, mattbev, sophias
 * 
 * Rest represents a pause in a piece of music, a variant of playable.
 *
 */



public class Rest implements Playable {
    
    private static final double ONE_HUNDRED = 100;
    private final double duration;
    private final LyricLine lyricLine;
    
    // AF(duration, lyricLine): a pause in the music of length duration during 
    //                          which a non-bolded line of lyrics is displayed
    //
    // RI: 
    //      duration > 0
    //
    // Safety from rep exposure:
    //      all fields private final, and never mutated after contructor
    //      all return types are immutable, rep is never exposed
    // Thread safety argument:
    //    This class is threadsafe because it's immutable:
    //    - duration is private final and an immutable type
    //    
    
    /**
     * Make a component to represent a pause in a piece of music
     * 
     * @param duration the duration of the pause
     * @param lyricLine a line of lyrics tied to the rest
     */
    public Rest(double duration, LyricLine lyricLine) { 
        this.duration = duration;
        this.lyricLine = lyricLine;
    }
    
    /**
     * Make a component to represent a pause in a piece of music
     * 
     * @param duration the duration of the pause
     * @param lyricLine a line of lyrics tied to the rest
     * @param header the header of the music that the rest is parsed from 
     */
    public Rest(String duration, LyricLine lyricLine, Header header) {
        this.lyricLine = lyricLine;
        
        String numerator;
        String denominator;

        if (duration.length() == 1) {
            numerator = duration;
            denominator = "1";
        } else {
            final String[] lengthParams = duration.split("/");
            numerator = lengthParams[0];
            denominator = lengthParams[1];
        }
        int numeratorInt = 1;
        int denominatorInt = 1;
        if (numerator.length() > 0) {
            numeratorInt = Integer.parseInt(numerator);
        }
        if (denominator.length() > 0) {
            denominatorInt = Integer.parseInt(denominator);
        }
        final double beatMultiplier = 4.;
        this.duration = ((double) numeratorInt) / ((double) denominatorInt) * header.getDefaultLengthDouble() * beatMultiplier;
    }

    @Override
    public double duration() {
        return this.duration;
    }

    
    /**
     * Provide a rest in the karaoke
     */
    @Override
    public void play(SequencePlayer player, double startBeat, WebServer server, Instrument inst) {
        Consumer<Double> c1 = i -> {
            try {
                server.putInBlockingQueue(lyricLine);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        player.addEvent(startBeat , c1 );
        return;
    }
    
    @Override
    public boolean equals(Object that) {
        if (that instanceof Rest) {
            if (this.duration() == ((Rest) that).duration()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Playable copyWithNewLyric(LyricLine l) {
        
        double d = this.duration();
        return new Rest(d, l);
    }
    
    @Override 
    public int hashCode() {
        return (int) (this.duration*ONE_HUNDRED);
    }
    
    @Override
    public String toString() {
        return "z" + this.duration;
    }

    @Override
    public LyricLine getLyricLine() {
        return this.lyricLine;
    }

}
