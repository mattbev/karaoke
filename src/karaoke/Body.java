package karaoke;

import java.util.HashMap;
import java.util.List;

import karaoke.sound.SequencePlayer;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Immutable data type representing a karaoke music body
 *
 */
public class Body implements Music {
    private HashMap<String,Music> voiceToMusic = new HashMap<String,Music>();
    private HashMap<String,LyricLine> voiceToLyrics = new HashMap<String,LyricLine>();
    
    // Abstraction Function:
    //   AF(voiceToMusic, voiceToLyrics) = one or more musics played at the same time by different voices
    //                                     with zero or more lyrics where the music for voice <voice> is
    //                                     at voiceToMusic.get(<voice>) and the lyrics are at voiceToLyrics.get(<voice>)
    //      
    //
    // Rep Invariant:
    //      voiceToMusic.size() > 0
    //      voiceToMusic.size() > 0
    //   
    //
    // Rep Safety Argument:
    //   all fields are private 
    //   all getter methods return Strings which are immutable 
    //
    // Thread Safety Argument:
    //   no rep fields are mutated outside of the constructor, which is a threadsafe method by default
    
    /**
     * Creates an isntance of a body object
     * @param musicMap map from voices Music
     * @param lyricMap map from voices to LyricLines
     */
    public Body(HashMap<String,Music> musicMap, HashMap<String,LyricLine> lyricMap) {
        this.voiceToMusic = musicMap;
        this.voiceToLyrics = lyricMap;
    }
    public double duration() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void play(SequencePlayer player, double startBeat) {
        // TODO Auto-generated method stub

    }

    public List<Measure> getMusic() {
        // TODO Auto-generated method stub
        return null;
    }

}
